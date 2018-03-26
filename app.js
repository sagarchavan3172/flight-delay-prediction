(function () {

  'use strict';

  angular.module('LunchChecker',[])

  .controller('LunchCheckerController', LunchCheckerController);

LunchCheckerController.$inject = ['$scope'];
  function LunchCheckerController  ($scope) {
    $scope.name = "";

    $scope.displayMeg = function () {
      var message = lunchItemCalculator($scope.name);
      $scope.display = message;
    }

    function lunchItemCalculator(string) {

      var arrayOfString = string.split(",");

      if(arrayOfString.length>3){
        $scope.fontStyle = {"color":"green"};
        $scope.borderStyle = {"border-color":"green"};
        return "Too Much !";
      }else if(string==""){
        $scope.fontStyle = {"color":"red"};
        $scope.borderStyle = {"border-color":"red"};
        return "Please enter data first";
      }else{
        $scope.fontStyle = {"color":"green"};
        $scope.borderStyle = {"border-color":"green"};
        return "Enjoy !"
      }
};

  };


})();
