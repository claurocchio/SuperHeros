app.controller('UsuariosController', ['$scope', 'UsuariosFactory', function($scope,Usuarios) {
	  $scope.message = 'Hello from UsuariosController';
       
	  $scope.usuariosList = [];
	  $scope.usuarioActivo;
	  
	  $scope.seleccionar = function(usuario) {
		  console.log(usuario.lastAccess)
	    	$scope.ultimoAcceso = usuario.lastAccess;
	    	$scope.nombre = $scope.usuarioActivo.username;
	    	$scope.cantFavoritos = $scope.usuarioActivo.favoritos.length;
	    	$scope.cantGrupos = $scope.usuarioActivo.grupos.length;
		};   
		
	  Usuarios.getUsuarios()
	     .success(function(data) {
	    	 $scope.usuariosList = data.usuarios; 
	    });
	    
	  Usuarios.getUsuarioById(id)
	     .success(function(data) {
	    	 $scope.usuarioActivo = data.perfil;
	    	 
	    })
	    
	}]);

