package com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.dao.exception.ManyResultsException;
import com.utn.tacs.tp2016c1g4.marvel_webapp.api.domain.Entity;

public abstract class MongoDBAbstractDao<T extends Entity, F extends SearchFilter<T>> implements Dao<T, F> {

	@SuppressWarnings("rawtypes")
	private MongoCollection collection;
	private Class<?> childClass;
	private long nextId;

	public MongoDBAbstractDao() {
		super();
		this.childClass = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost");
		MongoDatabase database = client.getDatabase("tacs-heroes");
		collection = database.getCollection(childClass.getSimpleName());
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
			return saved;
		obj.setId(this.nextId);
		try {
			collection.insertOne(Document.parse(mapper.writeValueAsString(obj)));
			saved = true;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if (saved) {
			this.nextId++;
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
		this.nextId = 1;
	}
}
