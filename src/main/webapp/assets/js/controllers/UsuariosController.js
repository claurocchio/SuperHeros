app.controller('UsuariosController', ['$scope', 'UsuariosFactory', function($scope,Usuarios) {
	  $scope.message = 'Hello from UsuariosController';
       
	  $scope.usuariosList = [];
	  $scope.usuarioActivo;
	  
	  $scope.seleccionar = function(usuario) {
			
		  Usuarios.getUsuarioById(usuario.id)
		     .success(function(data) {
		    	 console.log(data.perfil)
		    	 $scope.usuarioActivo = data.perfil;
		    	 
		    });
		  
		  
		  setTimeout(function () {
		  console.log(usuario.lastAccess)
	    	$scope.ultimoAcceso = usuario.lastAccess;
		  	
		  
		    console.log("va");
		    console.log($scope.usuarioActivo);
		    console.log($scope.usuarioActivo.username);
		    console.log($scope.usuarioActivo.favoritos.length);
		    console.log($scope.usuarioActivo.grupos.length);
	    	
		    $scope.nombre = $scope.usuarioActivo.username;
	    	$scope.cantFavoritos = $scope.usuarioActivo.favoritos.length;
	    	$scope.cantGrupos = $scope.usuarioActivo.grupos.length;}
		  , 5000)
		};   
		
	  Usuarios.getUsuarios()
	     .success(function(data) {
	    	 $scope.usuariosList = data.usuarios; 
	    });
	    
	
	    
	}]);

