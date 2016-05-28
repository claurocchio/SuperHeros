var app = angular.module('marvelApp', ['ngRoute']);


app.config(function($routeProvider) {
  $routeProvider

  .when('/', {
    templateUrl : 'views/home.html',
    controller  : 'HomeController'
  })

  .when('/admin', {
    templateUrl : 'views/admin.html',
    controller  : 'AdminController'
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
    controller  : 'myCtl'
  })
  
    .when('/login', {
    templateUrl : 'views/login.html',
    controller  : 'LoginController'
  })

  .otherwise({redirectTo: '/'});
});

app.controller('HomeController', function($scope) {
	  $scope.message = 'Hello from HomeController';
	});

app.controller('AdminController', function($scope) {
	  $scope.message = 'Hello from AdminController';
	});

app.controller('CatalogoController', function($scope) {
	  $scope.message = 'Hello from CatalogoController';
	});

app.controller('FavoritosController', function($scope) {
	  $scope.message = 'Hello from FavoritosController';
	});

app.controller('GruposController', function($scope) {
	  $scope.message = 'Hello from GruposController';
	});

app.controller('LoginController', function($scope) {
	  $scope.message = 'Hello from LoginController';
	});

