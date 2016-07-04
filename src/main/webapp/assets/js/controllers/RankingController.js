app.controller('RankingController', ['$scope', 'RankingFactory', function($scope,Ranking) {
	  
	$scope.personajesList = [];
	
	Ranking.getRanking()
    .success(function(data) {
    	$scope.personajesList = data.personajes;
     });
	
}]);