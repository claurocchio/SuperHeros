app.controller('GruposController', [ '$scope', 'GruposFactory', function($scope, Grupos) {
	$scope.message = 'Hello from GruposController';
	app.controller('ChildCtrl', function($scope, Utils) {
		$scope.utils = Utils;
	});
	$scope.gruposList = [];
	$scope.personajesList = [];
	$scope.personajesMiembros = [];

	$scope.show = false;
	var grupoSelected;
	var pag = 0;
	USERID = 1;

	$scope.primeraPag = function() {
		console.log("esta visible?");
		console.log($("#ant").is(":visible"));

		if (pag === 0 && $("#ant").is(":visible")) {
			$("#ant").hide();
		}

		if (pag > 0 && $("#ant").is(":hidden")) {
			console.log("entre al else de primeraPag");
			console.log(pag);
			$("#ant").show();
		}
	};

	// Grupos.buscarPersonajes().success(function(data) {
	// $scope.personajesList = data.personajes.name;
	// })

	$scope.buscarPersonajes = function() {
		Grupos.getPersonajesPorPag(pag).success(function(data) {
			console.log(data);
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

	$scope.getGrupos = function(userid) {
		Grupos.getGrupos(userid).success(function(data) {
			console.log("buscando grupos para usuario: " + userid);
			$scope.gruposList = data.grupos;
		})
	}

	$scope.getGrupos(sessionStorage.USERID);

	$scope.newGroup = function() {
		var request = {
			'name' : $scope.name,
			'idUsuario' : sessionStorage.USERID,
		};
		nuevo(request);
	};
	var nuevo = function(grupo) {
		Grupos.nuevo(grupo).success(function() {
			$scope.gruposList.push(grupo);
			$scope.buscarPersonajes();
			$scope.getGrupos(sessionStorage.USERID);
		})
	}

	$scope.update = function(grupo) {
		console.log("grupo select: " + grupo);
		if (!angular.isDefined(grupo) || grupo===null) {
			$("#bloqueMain").hide();
			show = false;
		} else {
			$("#bloqueMain").show();
			show = true;
		}
	};

	$scope.pushear = function() {
		$scope.personajesList.forEach(function(personaje) {
			console.log($scope.personajesMiembros.indexOf(personaje) === -1);
			if ($scope.personajesMiembros.indexOf(personaje) === -1) {
				personaje.checked ? $scope.personajesMiembros.push(personaje.nombre) : null;
			}
		});
		$scope.guardarMiembros();
	};

	$scope.guardarMiembros = function() {
		console.log("personajesList a json");
		console.log($scope.personajesMiembros);
		var request = {
			nombresPersonaje : $scope.personajesMiembros
		};
		console.log("va el json!");
		console.log(request);
		Grupos.guardarMiembros(request, USERID).success(function(data) {
			console.log(data);
		}).error(function(data) {
			console.log(data);
			alert(data.status.message);
		});
	}

	$scope.eliminar = function(personaje) {
		var index = $scope.personajesMiembros.indexOf(personaje);
		$scope.personajesMiembros.splice(index, 1);
		$scope.guardarMiembros();
		$scope.show = false;
	};
	$scope.eliminarGrupo = function(grupo) {
		var index = $scope.gruposList.indexOf(grupo);
		$scope.gruposList.splice(index, 1);
	};
	/*
	 * $scope.addChar = function() { angular.forEach($scope.data,
	 * function(value, key){ if(value.id == activeId){ if(capi == true) {
	 * value.personajes.push('Capitain America'); value.cant = activeGroup.cant +
	 * 1; }; if(wolv == true) { value.personajes.push('Wolverine'); value.cant =
	 * activeGroup.cant + 1; }; if(blackp == true) {
	 * value.personajes.push('Black Panther'); value.cant = value.cant + 1; } } }
	 * });
	 */


	// $scope.assign = function(myId) {
	// activeId = myId;
	// };
	//
	// $scope.chckedIndexs = [];
	//
	// $scope.checkedIndex = function(group) {
	// if ($scope.chckedIndexs.indexOf(group) === -1) {
	// $scope.chckedIndexs.push(group);
	// } else {
	// $scope.chckedIndexs.splice($scope.chckedIndexs.indexOf(group), 1);
	// }
	// }
	//
	// $scope.selectedGroups = function() {
	// return $filter('filter')($scope.students, {
	// checked : true
	// });
	// };
	//
	// $scope.remove = function(index) {
	// angular.forEach($scope.chckedIndexs, function(value, index) {
	// var index = $scope.data.indexOf(value);
	// $scope.data.splice($scope.data.indexOf(value), 1);
	// });
	// $scope.chckedIndexs = [];
	// };
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
