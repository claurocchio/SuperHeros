var app = angular.module('marvelApp', ['ngRoute', 'ngResource']);


app.config(function($routeProvider) {

$routeProvider
  .when('/', {
    templateUrl : 'views/home.html',
    controller  : 'HomeController'
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


  .otherwise({redirectTo: '/'});
});


// ENDPOINTS

app.factory('FavoritosFactory', ['$http', function($http) {

    var urlBase = 'api/favoritos';
    var FavoritosFactory = {};

    FavoritosFactory.getFavoritos = function() {
        return $http.get(urlBase);
    };

    return FavoritosFactory;
}]);
