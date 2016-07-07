app.controller('LoginController', ['$scope','$location','UsuariosFactory', function($scope,$location,Usuarios) {
	
	
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
	
	$scope.entrar = function()
	{
		var login = {
	            'userName':$scope.username,
	            'pass':$scope.pass
	        };
		
		console.log($scope.username);
		console.log($scope.pass);
		console.log(login);
		
		
		if ($('#username').val() === "") {
			$('#divUser').addClass('has-error');
		}
		else{
			$('#divUser').removeClass('has-error');
		}
		
		if ($('#pass').val() === "") {
			$('#divPass').addClass('has-error');
		}
		else{
			$('#divPass').removeClass('has-error');
		}
		
		if ( ($('#username').val() != "") && ($('#pass').val() != "")  ){
			Usuarios.login(login)
			.success(function(data){
				console.log(data);
				sessionStorage.USERID = data.idUsuario;
				sessionStorage.TOKEN = data.token;
				
				
				console.log("estoy viendo si es admin");
				console.log(data.admin);
				if (data.admin != true)
				{
					console.log("entre a hide");
					$("#menuAdmin").hide();
				}
				else
				{
					$("#menuAdmin").show();
				}
				
				$location.path("/home");
				$('.navbar').show('slow');
			})
			.error(function(data){
				alert(data.status.message);
			});
		}
	
	}
	
	
}]);
