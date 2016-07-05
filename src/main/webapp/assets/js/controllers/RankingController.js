app.controller('RankingController', ['$scope', 'RankingFactory', function($scope,Ranking) {
	  
	$scope.personajesList = [];
	
	Ranking.getRanking()
    .success(function(data) {
    	console.log(data);
    	$scope.personajesList = data.personajes;
     });
	
}]);