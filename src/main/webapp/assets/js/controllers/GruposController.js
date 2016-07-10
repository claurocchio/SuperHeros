app.controller('GruposController', [ '$scope', 'GruposFactory', function($scope, Grupos) {
	
	$scope.gruposList = [];
	$scope.personajesList = [];
	$scope.personajesMiembros = [];

	$scope.listaIntermedia = [];
	
	$scope.show = true;
	
	
	var pag = 0;
	var grupoId;
	var grupoActual = null;
	
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
			$scope.personajesMiembros = [];
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
	
//	$scope.update = function() {
//		console.log("grupo select: " + $scope.grupoSelected);
//		if (!angular.isDefined($scope.grupoSelected) || $scope.grupoSelected===null) {
//			$("#bloqueMain").hide();
//			$scope.show = false;
//		} else {
//			$("#bloqueMain").show();
//			$scope.show = true;
//			console.log("ACA LLAMO AL GET GRUPO: "+$scope.grupoSelected);
//			$scope.getGrupo($scope.grupoSelected);
//		}
//	};

	$scope.pushear = function() {
		if ($scope.inputGrupo == null || $scope.inputGrupo == "")
		{
			alert("Debe seleccionar un grupo");
		}
		else
		{
			$scope.personajesList.forEach(function(personaje) {
				console.log("holis: "+$scope.personajesMiembros);
				if ($scope.personajesMiembros.indexOf(personaje.nombre) === -1) {
					personaje.checked ? $scope.personajesMiembros.push(personaje.nombre) : null;
				}
			});
			
			console.log("GRUPO SELECCIONADO: "+grupoActual);
			$scope.guardarMiembros(grupoActual);
		}
		
		
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
			console.log("VEO SI LO QUE ME LLEGA ES EL PROBLEMA");
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
		console.log(personaje);
		$scope.personajesMiembros.splice(index, 1);
		$scope.guardarMiembros(grupoActual);
		  
	};
	
	$scope.nuevoClick = function() {
		
			$scope.name = "";
			$('#newGroupModal').modal('toggle');

	};
	
	$scope.modif = function() {
		if ($scope.inputGrupo == null || $scope.inputGrupo == "")
		{
			alert("Debe seleccionar un grupo");
		}
		else
		{
			$scope.nameNuevo = "";
			$('#newGroupModal2').modal('toggle');
		}
	};

	
	$scope.modificar = function() { 
		console.log("ENTRE AL INPUT NULL");
		console.log($scope.inputGrupo);
		if ($scope.inputGrupo == null || $scope.inputGrupo == "")
		{
			console.log("ENTRE AL INPUT NULL");
			alert("Debe seleccionar un grupo");
		}
		else
		{
			console.log("personajesList a json MODIFICAR");
			console.log($scope.personajesMiembros);
			console.log($scope.nameNuevo);
			var request = {
				nombre: $scope.nameNuevo,
				personajes : $scope.personajesMiembros
			};
			console.log("va el json! MODIFICAR");
			console.log(request);
			Grupos.guardarMiembros(request, grupoActual)
			.success(function(data) {
				console.log("VEO SI LO QUE ME LLEGA ES EL PROBLEMA");
				console.log(data);
				$scope.inputGrupo="";    
				$scope.getGrupos(sessionStorage.USERID);
				$scope.personajesMiembros = [];
			})
			.error(function(data) {
				console.log(data);
				alert(data.status.message);
			});
					 
		}
		

	};

	
	$scope.eliminarGrupo = function(grupo) {
		
		if ($scope.inputGrupo == null || $scope.inputGrupo == "")
		{
			console.log("ENTRE AL INPUT NULL");
			alert("Debe seleccionar un grupo");
		}
		else
		{
			var r = confirm("Esta seguro que quiere elimar el grupo?");
			if (r == true) {
			console.log("ACA VA GRUPOS LIST:"+grupo);
			var index = $scope.gruposList.indexOf(grupo);
			$scope.gruposList.splice(index, 1);
			Grupos.eliminar(grupoActual)
			.success(function(data) {
				console.log(data);
				grupoActual = null;
				$scope.inputGrupo="";      
				$scope.getGrupos(sessionStorage.USERID);
				$scope.personajesMiembros = [];
			});
			}
		}
	
	};
	
	$scope.grupoSeleccionado = function(grupo){
		console.log("esto es lo que me llega cuando click en button");
		console.log(grupo);
		$scope.inputGrupo=grupo.name;
		$scope.getGrupo(grupo.id);
		grupoActual = grupo.id;
	}
	
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
