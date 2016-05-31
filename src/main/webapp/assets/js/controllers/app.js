var app = angular.module('marvelApp', ['ngRoute']);


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
      $scope.lastId = 1;
      $scope.capi = false;
      $scope.blackp = false;
      $scope.wolv = false;
      $scope.activeGroup = {id: 0, name: '', cant: '', checked: false, clicked: false, personajes: ['']};
      $scope.activeId = -1;
    
      $scope.data = [{
        id: 0,
        name: 'Grupo 1',
        cant: '3',
        checked: false,
        clicked: false,
        personajes: ['Hulk', 'Spiderman', 'IronMan']
      }, {
        id: 1,
        name: 'Grupo 2',
        cant: '2',
        checked: false,
        clicked: false,
        personajes: ['Spiderman', 'IronMan']
      }];
    
     $scope.newGroup = function() {
        $scope.data.push({id: $scope.lastId + 1, name: $scope.gname, cant: 0, checked: false, clicked: false});
        $scope.lastId = $scope.lastId + 1;
      };
    
   /* $scope.addChar = function() {
         angular.forEach($scope.data, function(value, key){
            if(value.id == activeId){
                if(capi == true) {
                    value.personajes.push('Capitain America');
                    value.cant = activeGroup.cant + 1;         
                };
                if(wolv == true) {
                    value.personajes.push('Wolverine');
                    value.cant = activeGroup.cant + 1; 
                };
                if(blackp == true) {
                    value.personajes.push('Black Panther');
                    value.cant = value.cant + 1; 
                }
            }
         }
     });*/
        
    $scope.assign = function(myId){
      activeId = myId;
    };
    
     $scope.chckedIndexs=[];
    
     $scope.checkedIndex = function (group) {
         if ($scope.chckedIndexs.indexOf(group) === -1) {
             $scope.chckedIndexs.push(group);
         }
         else {
             $scope.chckedIndexs.splice($scope.chckedIndexs.indexOf(group), 1);
         }
     }

     $scope.selectedGroups = function () {
         return $filter('filter')($scope.students, { checked: true });
     };
     $scope.remove=function(index){
          angular.forEach($scope.chckedIndexs, function (value, index) {
              var index = $scope.data.indexOf(value);
              $scope.data.splice($scope.data.indexOf(value), 1);
          });
          $scope.chckedIndexs = [];
     };
	});

app.controller('LoginController', function($scope) {
	  $scope.message = 'Hello from LoginController';
	});

