app.controller('UsuariosController', ['$scope', 'UsuariosFactory', function($scope,Usuarios) {
	  $scope.message = 'Hello from UsuariosController';
       
	  $scope.usuariosList = [];
	  
	  
	  $scope.seleccionar = function(usuario) {
		 $scope.usuarioActivo;
		 console.log(usuario._id);
		 Usuarios.getUsuarioById(usuario._id)
		   .success(function(data) {
		   console.log(data.perfil)
		   $scope.usuarioActivo = data.perfil;
		   $scope.ultimoAcceso = usuario.lastAccess;
		   $scope.nombre = $scope.usuarioActivo.username;
		   $scope.cantFavoritos = $scope.usuarioActivo.favoritos.length;
		   $scope.cantGrupos = $scope.usuarioActivo.grupos.length;
		 });
	
		   };  
		
	  Usuarios.getUsuarios()
	     .success(function(data) {
	    	 $scope.usuariosList = data.usuarios; 
	    });
	    
	
	    
	}]);

