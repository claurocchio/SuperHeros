app.controller('LoginController', ['$scope', function($scope) {
	
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
		$scope.volver();
		
	};
	
}]);
