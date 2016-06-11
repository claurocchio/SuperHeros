app.controller('FavoritosController',  ['$scope', 'FavoritosFactory', function($scope,Favoritos) {
   //$scope.message = 'Hello from FavoritosController';
    Favoritos.getFavoritos()
     .success(function(data) {
    	 $scope.message = 'Hello '+data.favoritos[0].name;
    })

}]);