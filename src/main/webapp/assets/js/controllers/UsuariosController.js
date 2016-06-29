app.controller('UsuariosController', ['$scope', 'UsuariosFactory', function($scope,Usuarios) {
	 
	  $scope.usuariosList = [];
	  $scope.gruposList = ["grupo1","grupo2","grupo3"];
	  	  
	  $scope.seleccionar = function(usuario) {
		 $scope.usuarioActivo;
		 Usuarios.getUsuarioById(usuario.idPerfil)
		   .success(function(data) {
			   $('#x').hide();
			   $('#y').hide();
			   $('#panel').hide('slow');
			
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
	    	 $scope.seleccionar( $scope.usuariosList[0]);
	    });
	  
	  var grupoAnt;	  
	  $scope.detalle = function(grupo) {
		  $scope.x = grupo;
		  $scope.y = $scope.usuarioActivo.username; 
		  
		 if(grupoAnt != grupo)
		 {
			 $('#x').hide();
			 $('#y').hide();
		   $('#panel').hide('slow');
		   grupoAnt = grupo;
		 }
		 
		 $('#panel').show('slow');
		 $('#x').show();
		 $('#y').show();
		
		
	  }

}]);

