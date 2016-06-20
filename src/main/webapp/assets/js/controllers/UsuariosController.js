app.controller('UsuariosController', function($scope) {
	  $scope.message = 'Hello from UsuariosController';
       
	  $scope.usuariosList = [];
	  $scope.usuarioActivo;
	  
	  $scope.seleccionar = function(usuario) {
	    	$scope.filtroFavoritos = usuario;
		
		};   
		
	  Usuarios.getUsuarios()
	     .success(function(data) {
	    	 $scope.usuariosList = data.usuarios; 
	    })
	    
	  Usuarios.getUsuarioById(id)
	     .success(function(data) {
	    	 $scope.usuarioActivo;
	    	 
	    })
	    
	});

