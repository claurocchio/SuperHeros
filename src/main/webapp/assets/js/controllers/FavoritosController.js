app.controller('FavoritosController',  ['$scope', 'FavoritosFactory', function($scope,Favoritos) {
   
    $scope.favoritosList = [];
    $scope.personajesList = [];
    
    var pag = 0;
    
    USERID = 1;
    
	$scope.primeraPag = function() {
		console.log("esta visible?");
		console.log($("#ant").is(":visible"));
		
		if (pag === 0 && $("#ant").is(":visible") )
		{
			$("#ant").hide();		
		}
		
		if (pag >0 && $("#ant").is(":hidden")){
			console.log("entre al else de primeraPag");
			console.log(pag);
			$("#ant").show();	
		}
	};
	

	   
	Favoritos.getFavoritos(USERID)
     .success(function(data) {
    	 if (data.favoritos.length > 0)
    	{
    		 $scope.favoritosList = data.favoritos;
    	}
    	 console.log( $scope.favoritosList );
	    })
	   
    $scope.guardarFavoritos = function() {
    	
		console.log("favoritosList a json");
		console.log($scope.favoritosList);
		
    	var request = { idsPersonaje : $.map(  $scope.favoritosList, function( val ){
    					return val.id;
    				})
    			  };
    	
    	console.log("va el json!");
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
    

    
    $scope.buscarPersonajes = function() {
    	Favoritos.getPersonajesPorPag(pag)
    	.success(function(data) {
    	 console.log(data);
    	 $scope.personajesList = data.personajes; 
    	 if(data.personajes.length < 7)
    	 {
    		$("#sig").hide();		
    	 }
    	 else
    	 {
    		$("#sig").show();	 
    	 }
    	 
    	 if(data.personajes.length === 0)
    	 {
    		 $("#ant").hide();
    	 }
    	 else
    	 {
    		 $("#ant").show();
    	 }
    	 $scope.primeraPag();
    })
    };
    
    $scope.buscarPersonajes();
   
    console.log(pag);
    console.log(pag);
    
    $scope.pushear = function() {

		$scope.personajesList.forEach(function(personaje){
			console.log($scope.favoritosList.indexOf(personaje) === -1);
			if ($scope.favoritosList.indexOf(personaje) === -1){				
			personaje.checked ? $scope.favoritosList.push(personaje) : null ;
			}
		});
			$scope.guardarFavoritos();
	};   
	
	$scope.eliminar = function(personaje) {
		var index = $scope.favoritosList.indexOf(personaje);
		  $scope.favoritosList.splice(index, 1); 
		  $scope.guardarFavoritos();
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


