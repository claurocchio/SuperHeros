app.controller('FavoritosController',  ['$scope', 'FavoritosFactory', function($scope,Favoritos) {
   
    $scope.favoritosList = [];
    $scope.personajesList = [];
    
    var pag = 0;
    
    USERID = 1;
    
	$scope.primeraPag = function() {
		if (pag === 0)
		{
			$("#ant").hide('slow');		
		}
		else
		{
			$("#ant").show('slow');	
		}
	};
	
	   $scope.primeraPag();
	   
    $scope.guardarFavoritos = function() {
    	
    	request = { listado : $.map( favoritosList, function( val ){
    					return val.id;
    				})
    			  };
    	
    	console.log(request);
    	    	
    	Favoritos.guardarFavoritos(request,USERID)
    	.success(function(data) {
    		 console.log(data);
    	})
    	.error(function(data){
    		 console.log(data);
			 alert(data.status.message);
		 });
    }
    
	Favoritos.getFavoritos(USERID)
     .success(function(data) {
    	 $scope.favoritosList = data.favoritos; 
    })
    
    $scope.buscarPersonajes = function() {
    	Favoritos.getPersonajesPorPag(pag)
    	.success(function(data) {
    	 console.log(data);
    	 $scope.personajesList = data.personajes; 
    	 if(data.personajes.length < 7)
    	 {
    		$("#sig").hide('slow');		
    	 }
    	 else
    	 {
    		$("#sig").show('slow');	 
    	 }
    	 
    	 if(data.personaje.length === 0)
    	 {
    		 $("#ant").hide('slow');
    	 }
    	 else
    	 {
    		 $("#ant").show('slow');
    	 }
    })
    };
    $scope.buscarPersonajes();
    
    $scope.pushear = function() {
//		debugger;
    	$scope.filtroFavoritos = [];
    	
		$scope.personajesList.forEach(function(personaje){
			personaje.checked ? $scope.favoritosList.push(personaje) : null
			
		})
		
		 $scope.guardarFavoritos;
	};   
	
	$scope.eliminar = function(personaje) {
		var index = $scope.favoritosList.indexOf(personaje);
		  $scope.favoritosList.splice(index, 1); 
		  $scope.guardarFavoritos;
	};
	

	
	$scope.ant = function(){
		
		pag--;
		$scope.primeraPag();
		
		$scope.buscarPersonajes();
	};
	
	$scope.sig = function(){
		
		pag++;
		$scope.primeraPag();
		
		$scope.buscarPersonajes();
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


