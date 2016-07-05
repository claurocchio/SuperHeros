app.controller('ComparacionGruposController', ['$scope', 'ComparacionGruposFactory', function($scope,Comparacion) {
	
	$scope.usuariosList1 = [];
	$scope.usuariosList2 = [];
	$scope.gruposListUser1 = [];
	$scope.gruposListUser2 = [];
	$scope.result = [];
	
	
	Comparacion.getUsuarios()
     .success(function(data) {
    	 console.log("muestro los usuarios");
    	 console.log(data);
    	 $scope.usuariosList1 = data.usuarios;
    	 $scope.usuariosList2 = data.usuarios;
    	 
    	 console.log("la lista de usuarios");
    		console.log($scope.usuariosList1);
    		console.log($scope.usuariosList2);
    });
	
	
	
	$scope.usuarioSeleccionado2 = function(usuario){
		Comparacion.getGruposDeUsuario(usuario.idPerfil)
	     .success(function(data) {
	    	 $scope.gruposListUser2 = data.usuarios;
	    });
	}
	
	$scope.usuarioSeleccionado1 = function(usuario){
		Comparacion.getGruposDeUsuario(usuario.idPerfil)
	     .success(function(data) {
	    	 $scope.gruposListUser1 = data.usuarios;
	    });
	}
	
	
	
	function intersect(a, b) {
	    var t;
	    if (b.length > a.length) t = b, b = a, a = t; // indexOf to loop over shorter
	    return a.filter(function (e) {
	        if (b.indexOf(e) !== -1) return true;
	    });
	}
	
	$scope.comparar = function(){
		console.log("insterseccion");
		console.log(intersect($scope.gruposListUser1,$scope.gruposListUser2));
		$scope.result = intersect($scope.gruposListUser1,$scope.gruposListUser2);
		if ($scope.result.length > 0){
			$('#tit').show('slow');
		}
		else
		{
			$('#tit').hide('slow');
			alert("No hay interseccion");
		}
	}
	

	
}]);
