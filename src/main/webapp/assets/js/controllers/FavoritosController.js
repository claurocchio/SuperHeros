app.controller('FavoritosController',  ['$scope', 'FavoritosFactory', function($scope,Favoritos) {
   
    $scope.favoritosList = [];
    $scope.filtroFavoritos = [];
    
	Favoritos.getFavoritos()
     .success(function(data) {
    	 $scope.favoritosList = data.favoritos; 
    })
    
    Favoritos.getPersonajes()
     .success(function(data) {
    	 $scope.personajesList = data.personajes.name; 
    })
    
    $scope.pushear = function() {
    	$scope.filtroFavoritos = [];
    	
		$scope.favoritosList.forEach(function(favorito){
			favorito.checked ? $scope.filtroFavoritos.push(favorito) : null
		})
	};   
	
	$scope.eliminar = function(favorito) {
		var index = $scope.filtroFavoritos.indexOf(favorito);
		  $scope.filtroFavoritos.splice(index, 1); 
		  
	};
    
}]);





//REFERENCIA DE "HACER MAS TARDE"
//setTimeout(function () {
//  console.log("Favorito")
//  console.log($scope.favoritosList);
//  $scope.favoritosList.forEach(function(favorito){
//  	console.log("Favorito Button")
//  	console.log(favorito.checked)
//  })
//}, 10000);