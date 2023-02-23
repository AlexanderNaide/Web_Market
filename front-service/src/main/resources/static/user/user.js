angular.module('market').controller('userController', function ($scope, $http, $localStorage) {
    const contextPathAuth = 'http://localhost:8066/auth/api/v1';

    $scope.loadInformation = function () {
        $http({
            url: contextPathAuth + '/information',
            method: 'GET'
        }).then(function (response) {
            console.log(response.data)
            $scope.UserInformation = response.data;
        });
    };

    $scope.loadInformation();

});