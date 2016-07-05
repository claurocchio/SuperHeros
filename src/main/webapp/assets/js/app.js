var app = angular.module('marvelApp', [ 'ngRoute', 'ngResource' ]);

app.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'views/login.html',
		controller : 'LoginController'
	})

	.when('/usuarios', {
		templateUrl : 'views/usuarios.html',
		controller : 'UsuariosController'
	})

	.when('/catalogo', {
		templateUrl : 'views/catalogo.html',
		controller : 'CatalogoController'
	})

	.when('/favoritos', {
		templateUrl : 'views/favoritos.html',
		controller : 'FavoritosController'
	})

	.when('/grupos', {
		templateUrl : 'views/grupos.html',
		controller : 'GruposController'
	})

	.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'LoginController'
	})

	.when('/ranking', {
		templateUrl : 'views/ranking.html',
		controller : 'RankingController'
	})

	.when('/home', {
		templateUrl : 'views/home.html',
		controller : 'HomeController'
	})

	.otherwise({
		redirectTo : '/'
	});
});

var urlPersonajes = 'api/personajes';
// ENDPOINTS
app.factory('FavoritosFactory', [ '$http', function($http) {

	var urlBase = 'api/favoritos';
	var FavoritosFactory = {};

	FavoritosFactory.getFavoritos = function(userId) {
		console.log("userId en favoritos");
		console.log(userId);
		console.log(urlBase + '/' + userId);
		return $http.get(urlBase + '/' + userId);
	};

	FavoritosFactory.getPersonajesPorPag = function(pag) {
		return $http.get(urlPersonajes + '?page=' + pag + '&limit=7');
	};

	FavoritosFactory.guardarFavoritos = function(listFavoritos, userId) {
		return $http.put(urlBase + '/' + userId, listFavoritos);
	}
	return FavoritosFactory;
} ]);

app.factory('GruposFactory', [ '$http', function($http) {
	var urlBase = 'api/grupos';
	// var urlPersonajes = 'api/personajes';
	var GruposFactory = {};

	GruposFactory.getGrupos = function(id) {
		return $http.get(urlBase + '?idUsuario=' + id);
	};

	// GruposFactory.addGrupo = function() {
	// return $http.put(urlBase);
	// };

	GruposFactory.nuevo = function(idUsuario, grupo) {
		return $http.post(urlBase, idUsuario, grupo);
	};

	GruposFactory.getPersonajesPorPag = function(pag) {
		console.log(urlPersonajes + '?page=' + pag + '&limit=7');
		return $http.get(urlPersonajes + '?page=' + pag + '&limit=7');
	};

	GruposFactory.guardarMiembros = function(listMiembros, userId) {
		console.log();
		return $http.put(urlBase + '/' + userId, listMiembros);
	}

	return GruposFactory;
} ]);

app.factory('UsuariosFactory', [ '$http', function($http) {

	var urlBase = 'api/usuarios';
	var urlPerfil = 'api/perfiles';
	var urlLogin = 'api/login';

	var UsuariosFactory = {};

	UsuariosFactory.getUsuarios = function() {
		return $http.get(urlBase);
	};

	UsuariosFactory.getUsuarioById = function(id) {
		return $http.get(urlPerfil + '/' + id);
	};

	UsuariosFactory.guardarUsuario = function(usuario) {
		return $http.post(urlBase, usuario);
	};

	UsuariosFactory.login = function(usuario) {
		return $http.post(urlLogin, usuario);
	};

	return UsuariosFactory;
} ]);

app.factory('RankingFactory', [ '$http', function($http) {
	var urlBase = 'api/ranking'

	var RankingFactory = [];

	RankingFactory.getRanking = function() {
		return $http.get(urlBase);
	};

	return RankingFactory;
} ]);

app.factory('CatalogoFactory', [ '$http', function($http) {

	var CatalogoFactory = [];

	CatalogoFactory.getPersonajesPorPag = function(pag) {
		return $http.get(urlPersonajes + '?page=' + pag + '&limit=6');
	};

	return CatalogoFactory;
} ]);

