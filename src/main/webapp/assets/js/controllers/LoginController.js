app.controller('LoginController', ['$scope','UsuariosFactory', function($scope,Usuarios) {
	
	$scope.entrar = function()
	{
		$('.navbar').show('slow');
	};
	
	$scope.crearCuenta = function()
	{
		$("#loginForm").hide('slow');
		$("#registroForm").show('slow');
	};
	
	$scope.volver = function()
	{
		$("#registroForm").hide('slow');
		$("#loginForm").show('slow');
		
	};
	$scope.crearUsuario = function()
	{
		
		var usuarioNuevo = {
	            'userName':$scope.lg_nuevo_username,
	            'email':$scope.lg_nuevo_email,
	            'pass':$scope.lg_nuevo_password
	        };
//	
//		if($scope.lg_nuevo_username === "" || $scope.lg_nuevo_password === "") 
//		{
//			if($scope.lg_nuevo_username === "")
//			{
//				
//			}
//			if($scope.lg_nuevo_password === "")
//			{
//				
//			}
//		}
		 Usuarios.guardarUsuario(usuarioNuevo)
         .success(function(data){
            alert(data.status.message);
            $scope.lg_nuevo_username="";
            $scope.lg_nuevo_password="";
            $scope.lg_nuevo_email="";
            $scope.volver();
         })
		 .error(function(data){
			 alert(data.status.message);
		 });
		
		
	};
	
}]);
