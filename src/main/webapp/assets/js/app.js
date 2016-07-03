var app = angular.module('marvelApp', ['ngRoute', 'ngResource']);


app.config(function($routeProvider) {

$routeProvider
  .when('/', {
    templateUrl : 'views/login.html',
    controller  : 'LoginController'
  })

  .when('/usuarios', {
    templateUrl : 'views/usuarios.html',
    controller  : 'UsuariosController'
  })

  .when('/catalogo', {
    templateUrl : 'views/catalogo.html',
    controller  : 'CatalogoController'
  })
  
    .when('/favoritos', {
    templateUrl : 'views/favoritos.html',
    controller  : 'FavoritosController'
  })
  
    .when('/grupos', {
    templateUrl : 'views/grupos.html',
    controller  : 'GruposController'
  })
  
    .when('/login', {
    templateUrl : 'views/login.html',
    controller  : 'LoginController'
  })
  
   .when('/ranking', {
    templateUrl : 'views/ranking.html',
    controller  : 'RankingController'
  })
  
    .when('/home', {
    templateUrl : 'views/home.html',
    controller  : 'HomeController'
  })


  .otherwise({redirectTo: '/'});
});



var urlPersonajes = 'api/personajes';
// ENDPOINTS
app.factory('FavoritosFactory', ['$http', function($http) {

    var urlBase = 'api/favoritos';
    var FavoritosFactory = {};

    FavoritosFactory.getFavoritos = function(userId) {
    	console.log(urlBase+'/'+userId);
        return $http.get(urlBase+'/'+userId);
    };
    
    FavoritosFactory.getPersonajesPorPag = function(pag) {
    	console.log(urlPersonajes+'?page='+pag+'&limit=7');
        return $http.get(urlPersonajes+'?page='+pag+'&limit=7');
    };

    FavoritosFactory.guardarFavoritos = function(listFavoritos,userId){
    	console.log();
    	return $http.put(urlBase+'/'+userId,listFavoritos);
    }
    return FavoritosFactory;
}]);

app.factory('GruposFactory', ['$http', function($http) {

    var urlBase = 'api/grupos/';
//    var urlPersonajes = 'api/personajes';
    var GruposFactory = {};

    GruposFactory.getGrupos = function(id) {
        return $http.get(urlBase+id);
    };
    
//    GruposFactory.addGrupo = function() {
//        return $http.put(urlBase);
//    };
    
    GruposFactory.nuevo = function(grupo) {
    	return $http.post(urlBase, grupo);
    };
    
    GruposFactory.getPersonajes = function() {
        return $http.get(urlPersonajes);
    };

    return GruposFactory;
}]);

app.factory('UsuariosFactory', ['$http', function($http) {

    var urlBase = 'api/usuarios';
    var urlPerfil = 'api/perfiles';
    var UsuariosFactory = {};

    UsuariosFactory.getUsuarios = function() {
        return $http.get(urlBase);
    };
    
    UsuariosFactory.getUsuarioById = function(id) {
        return $http.get(urlPerfil+'/'+id);
    };

    UsuariosFactory.guardarUsuario = function (usuario) {
        return $http.post(urlBase, usuario);
    };

    return UsuariosFactory;
}]);

app.factory('RankingFactory', ['$http', function($http) {
	var urlBase = 'api/ranking'
		
	var RankingFactory = [];
	
	RankingFactory.getRanking = function() {
	        return $http.get(urlBase);
	 };
	
    return RankingFactory;
}]);

