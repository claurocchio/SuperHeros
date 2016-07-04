app.controller('CatalogoController',  ['$scope', 'CatalogoFactory', function($scope,Catalogo) {
 
	var pag = 0;
	
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
	
	 $scope.buscarPersonajes = function() {
		 Catalogo.getPersonajesPorPag(pag)
    	.success(function(data) {
    		console.log(data);
    		$scope.personajesList = data.personajes; 
    		 if(data.personajes.length < 6)
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
