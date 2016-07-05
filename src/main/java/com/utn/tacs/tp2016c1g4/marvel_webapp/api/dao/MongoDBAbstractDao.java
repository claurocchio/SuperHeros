package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.recursos.FavoritoResource;

public abstract class MongoDBAbstractDao<T extends Entity, F extends SearchFilter<T>> implements Dao<T, F> {

	private static final Logger logger = LogManager.getLogger(MongoDBAbstractDao.class);

	@SuppressWarnings("rawtypes")
	private MongoCollection collection;
	private MongoCollection sequence;
	private MongoDatabase db;

	private Class<?> childClass;

	@SuppressWarnings("resource")
	public MongoDBAbstractDao() {
		super();
		this.childClass = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		MongoClient client = null;

		String uri = System.getenv("OPENSHIFT_MONGODB_DB_URL");
		if (uri != null && !uri.isEmpty()) {
			logger.info("iniciando dao usando db en uri: " + uri);
			MongoClientURI clientUri = new MongoClientURI(uri);
			client = new MongoClient(clientUri);
		} else {
			logger.info("iniciando dao usando db en localhost");
			client = new MongoClient("localhost");
		}

		db = client.getDatabase("tacs-heroes");
		collection = db.getCollection(childClass.getSimpleName());

		sequence = db.getCollection("idSequence");
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id", childClass.getSimpleName());

		if (sequence.count(doc) == 0) {
			Document obj = new Document().append("_id", childClass.getSimpleName()).append("seq", 0L);
			sequence.insertOne(obj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> getAll() {
		Set<T> result = new HashSet<T>();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		for (Document document : documents) {
			try {
				result.add((T) mapper.readValue(document.toJson(), childClass));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> find(Collection<F> filters) {
		Document bson = new Document();
		Map<String, ?> mapFilters = createFilters(filters);
		for (Entry<String, ?> filter : mapFilters.entrySet()) {
			bson.append(filter.getKey(), filter.getValue());
		}

		Set<T> result = new HashSet<T>();
		List<Document> documents = (List<Document>) collection.find(bson).into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		for (Document document : documents) {
			try {
				result.add((T) mapper.readValue(document.toJson(), childClass));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> find(Collection<F> filters, Page page) {

		Document bson = new Document();
		Map<String, ?> mapFilters = createFilters(filters);
		for (Entry<String, ?> filter : mapFilters.entrySet()) {
			bson.append(filter.getKey(), filter.getValue());
		}

		Set<T> result = new HashSet<T>();
		List<Document> documents = (List<Document>) collection.find(bson).skip(page.getPage() * page.getLimit())
				.limit(page.getLimit()).into(new ArrayList<Document>());

		ObjectMapper mapper = new ObjectMapper();
		for (Document document : documents) {
			try {
				result.add((T) mapper.readValue(document.toJson(), childClass));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	protected abstract <T> Map<String, ?> createFilters(Collection<F> filters);

	protected abstract T fullClone(T from);

	protected abstract void cloneForUpdate(T from, T into);

	@SuppressWarnings("unchecked")
	public T findForUpdate(Long id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", id);
		return (T) collection.find(query).first();
	}

	@Override
	public T findOne(Collection<F> filters) {
		Set<T> results = find(filters);

		if (results.size() > 1) {
			throw new ManyResultsException(results);
		}

		return results.size() == 1 ? results.iterator().next() : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean save(T obj) {
		boolean saved = false;

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		if (obj.getId() != null)
			return false;

		Long id = getNextSequence();
		obj.setId(id);

		try {
			collection.insertOne(Document.parse(mapper.writeValueAsString(obj)));
			saved = true;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return saved;
	}

	@Override
	public boolean delete(T obj) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", obj.getId());
		collection.deleteOne(query);
		return true;
	}

	@Override
	public boolean update(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		BasicDBObject query = new BasicDBObject();
		query.put("_id", obj.getId());

		try {
			collection.updateOne(query, new Document("$set", Document.parse(mapper.writeValueAsString(obj))));
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return true;
	}

	public void clear() {
		collection.drop();
		BasicDBObject query = new BasicDBObject();
		query.put("_id", childClass.getSimpleName());

		BasicDBObject update = new BasicDBObject();
		update.put("$set", new Document("seq", 1L));

		sequence.findOneAndUpdate(query, update);
	}

	protected Long getNextSequence() {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", childClass.getSimpleName());

		BasicDBObject update = new BasicDBObject();
		update.put("$inc", new Document("seq", 1));
		Document doc = (Document) sequence.findOneAndUpdate(query, update);

		return doc.getLong("seq");
	}

	public Long count() {
		return count(null);
	}

	public Long count(Collection<F> filters) {

		Long count = 0L;

		if (filters == null || filters.size() == 0) {
			Document query = new Document();
			Map<String, ?> mapFilters = createFilters(filters);
			for (Entry<String, ?> filter : mapFilters.entrySet()) {
				query.append(filter.getKey(), filter.getValue());
			}

			count = collection.count(query);
		} else {
			count = collection.count();
		}

		return count;
	}
}
