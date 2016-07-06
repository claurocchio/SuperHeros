app.controller('GruposController', [ '$scope', 'GruposFactory', function($scope, Grupos) {
	
	$scope.gruposList = [];
	$scope.personajesList = [];
	$scope.personajesMiembros = [];

	$scope.listaIntermedia = [];
	
	$scope.show = true;
	$scope.grupoSelected;
	var pag = 0;
	
	var grupoId;
	
	$scope.grupoAId = function(id) {
		grupoId=id;
	};
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

	$scope.getGrupos = function(userId) {
		Grupos.getGrupos(userId).success(function(data) {
			console.log("buscando grupos para usuario: " + sessionStorage.USERID);
			console.log(data);
			$scope.gruposList = data.perfil.grupos;
		})
	}
	
	$scope.getGrupo = function(grupo) {
		Grupos.getGrupo(grupo).success(function(data) {
			console.log("buscando personajes: " + grupo);
			console.log(data.grupo);
			console.log(data.grupo.personajes );
			if (data.grupo.personajes.length > 0){
				$scope.listaIntermedia = data.grupo.personajes;
				$scope.listaIntermedia.forEach(function(personaje) {
					if ($scope.personajesMiembros.indexOf(personaje.nombre) === -1) {
						$scope.personajesMiembros.push(personaje.nombre);
					}
				});
				
			}
			console.log("VAMOS POR LO IMPORTANTE ");
			console.log($scope.personajesMiembros);
		})
	}

	$scope.getGrupos(sessionStorage.USERID);

	$scope.newGroup = function() {
		var request = {
			'name' : $scope.name,
			'idUsuario' : sessionStorage.USERID,
		};
		
		Grupos.nuevo(request)
		.success(function(data) {
			console.log("va el nombre");
			console.log(request.name);
			$scope.gruposList = $scope.getGrupos(sessionStorage.USERID);
//			$scope.gruposList.push(request.name);
//			$scope.buscarPersonajes();
		})
		
	};
	
	$scope.update = function() {
		console.log("grupo select: " + $scope.grupoSelected);
		if (!angular.isDefined($scope.grupoSelected) || $scope.grupoSelected===null) {
			$("#bloqueMain").hide();
			$scope.show = false;
		} else {
			$("#bloqueMain").show();
			$scope.show = true;
			console.log("ACA LLAMO AL GET GRUPO: "+$scope.grupoSelected);
			$scope.getGrupo($scope.grupoSelected);
		}
	};

	$scope.pushear = function() {
		$scope.personajesList.forEach(function(personaje) {
			console.log("holis: "+$scope.personajesMiembros);
			if ($scope.personajesMiembros.indexOf(personaje.nombre) === -1) {
				personaje.checked ? $scope.personajesMiembros.push(personaje.nombre) : null;
			}
		});
		
		console.log("GRUPO SELECCIONADO: "+$scope.grupoSelected);
		$scope.guardarMiembros($scope.grupoSelected);
	};

	$scope.guardarMiembros = function(grupoId) {
		console.log("personajesList a json");
		console.log($scope.personajesMiembros);
		var request = {
			nombre: null,
			personajes : $scope.personajesMiembros
		};
		console.log("va el json!");
		console.log(request);
		Grupos.guardarMiembros(request, grupoId)
		.success(function(data) {
			console.log(data);
		})
		.error(function(data) {
			console.log(data);
			alert(data.status.message);
		});
	}

	$scope.eliminar = function(personaje) {
		console.log("AQUI AQUI");
		console.log($scope.personajesMiembros);
		var index = $scope.personajesMiembros.indexOf(personaje);
		$scope.personajesMiembros.splice(index, 1);
		$scope.guardarMiembros($scope.grupoSelected);
//		$scope.show = false;
	};

	
	$scope.eliminarGrupo = function(grupo) {
		console.log("ACA VA GRUPOS LIST:"+gruposList);
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
