angular.module('market').controller('userController', function ($scope, $http, $localStorage) {
    const contextPathAuth = 'http://localhost:8066/auth/api/v1/user';

    $scope.loadInformation = function () {
        $http({
            url: contextPathAuth + '/information',
            method: 'GET'
        }).then(function (response) {
            console.log(response.data);
            $scope.UserInformation = response.data;
            if(response.data.birthday !== null) {
                $scope.UserInformation.birthday = new Date(response.data.birthday);
            }
        });
    };

    $scope.saveInformation = function () {
        let setUserInformation = {
            username: $scope.UserInformation.username,
            password: $scope.UserInformation.password,
            email: $scope.UserInformation.email,
            phone: $scope.UserInformation.phone,
            birthday: $scope.UserInformation.birthday.getTime()
        };
        $http({
            url: contextPathAuth + '/save',
            method: 'POST',
            data: setUserInformation
        }).then(function (response) {
            $scope.loadInformation();
        });
    };

    $scope.loadInformation();

});