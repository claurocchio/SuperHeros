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
			
			if ($('#lg_nuevo_username').val() === "") {
				$('#user').addClass('has-error');
			}
			else{
				$('#user').removeClass('has-error');
			}
			
			if ($('#lg_nuevo_password').val() === "") {
				$('#pass').addClass('has-error');
			}
			else{
				$('#pass').removeClass('has-error');
			}
			
			if ($('#lg_nuevo_email').val() === "") {
				$('#email').addClass('has-error');
			}
			else{
				$('#email').removeClass('has-error');
			}
			
			if( ($('#lg_nuevo_username').val() != "") && ($('#lg_nuevo_password').val() != "") && ($('#lg_nuevo_email').val() != "") ){	
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
			}
		
	};
	
}]);
