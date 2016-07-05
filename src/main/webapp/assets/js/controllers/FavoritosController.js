app.controller('FavoritosController', [ '$scope', 'FavoritosFactory', function($scope, Favoritos) {

	$scope.favoritosList = [];
	$scope.personajesList = [];

	var pag = 0;

	$scope.primeraPag = function() {
		if (pag === 0 && $("#ant").is(":visible")) {
			$("#ant").hide();
		}
		if (pag > 0 && $("#ant").is(":hidden")) {
			$("#ant").show();
		}
	};

	Favoritos.getFavoritos(sessionStorage.USERID).success(function(data) {
		console.log("lo q llega en favoritos");
		console.log(data);
		if (data.favoritos.personajes.length > 0) {
			console.log("lo q llega en length");
			console.log(data);
			$scope.favoritosList = data.favoritos.personajes;
		}
	})

	$scope.guardarFavoritos = function() {

		var request = {
			nombresPersonaje : $scope.favoritosList
		};

		Favoritos.guardarFavoritos(request, sessionStorage.USERID).success(function(data) {
			console.log(data);
		}).error(function(data) {
			alert(data.status.message);
		});
	}

	$scope.buscarPersonajes = function() {
		Favoritos.getPersonajesPorPag(pag).success(function(data) {
			$scope.personajesList = data.personajes;
			if (data.personajes.length < 7) {
				$("#sig").hide();
			} else {
				$("#sig").show();
			}

			if (data.personajes.length === 0) {
				$("#ant").hide();
			} else {
				$("#ant").show();
			}
			$scope.primeraPag();
		})
	};

	$scope.buscarPersonajes();

	$scope.pushear = function() {

		$scope.personajesList.forEach(function(personaje) {
			console.log("aca va el push");
			console.log($scope.favoritosList.indexOf(personaje) === -1);
			if ($scope.favoritosList.indexOf(personaje.nombre) === -1) {
				personaje.checked ? $scope.favoritosList.push(personaje.nombre) : null;
			}
			else
				{
				console.log("ya esta en la lista");
				}
		});
		$scope.guardarFavoritos();
	};

	$scope.eliminar = function(personaje) {
		var index = $scope.favoritosList.indexOf(personaje);
		$scope.favoritosList.splice(index, 1);
		$scope.guardarFavoritos();
	};

	$scope.ant = function() {

		pag--;
		$scope.primeraPag();

		$scope.buscarPersonajes();
	};

	$scope.sig = function() {

		pag++;
		$scope.primeraPag();

		$scope.buscarPersonajes();
	};

} ]);

// REFERENCIA DE "HACER MAS TARDE"
// setTimeout(function () {
// console.log("Favorito")
// console.log($scope.favoritosList);
// $scope.favoritosList.forEach(function(favorito){
// console.log("Favorito Button")
// console.log(favorito.checked)
// })
// }, 10000);

