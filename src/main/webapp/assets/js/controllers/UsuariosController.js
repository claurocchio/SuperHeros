app.controller('UsuariosController', ['$scope', 'UsuariosFactory', function($scope,Usuarios) {
	 
	  $scope.usuariosList = [];
	  $scope.gruposList = [];
	  	  
	  $scope.seleccionar = function(usuario) {
		 $scope.usuarioActivo;
		 Usuarios.getUsuarioById(usuario.idPerfil)
		   .success(function(data) {
			   $('#x').hide();
			   $('#y').hide();
			   $('#panel').hide('slow');
			
		   $scope.usuarioActivo = data.perfil;
		   console.log("va el data de perfil en usuario");
		   console.log(data.perfil);
		   $scope.ultimoAcceso = usuario.lastAccess;
		   $scope.nombre = $scope.usuarioActivo.username;
		   $scope.cantFavoritos = $scope.usuarioActivo.favoritos.length;
		   $scope.cantGrupos = $scope.usuarioActivo.grupos.length;
		   if ($scope.usuarioActivo.grupos.length > 0){
			   console.log("va para grupos");
			   console.log($scope.usuarioActivo.grupos);
			   $scope.gruposList = $scope.usuarioActivo.grupos;
			}
		 });
	
		   };  
		
	  Usuarios.getUsuarios()
	     .success(function(data) {
	    	 $scope.usuariosList = data.usuarios; 
	    	 $scope.seleccionar( $scope.usuariosList[0]);
	    });
	  
	  var grupoAnt;	  
	  $scope.detalle = function(grupo) {
		  $scope.x = grupo.name;
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

