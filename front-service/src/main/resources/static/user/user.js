angular.module('market').controller('userController', function ($scope, $http, $localStorage) {
    const contextPathAuth = 'http://localhost:8066/auth/api/v1/user';

    $scope.loadInformation = function () {
        $http({
            url: contextPathAuth + '/information',
            method: 'GET'
        }).then(function (response) {
            $scope.UserInformation = response.data;
            $scope.UserInformation.usernameOld = $scope.UserInformation.username;
            $scope.UserInformation.phoneOld = $scope.UserInformation.phone;
            $scope.UserInformation.emailOld = $scope.UserInformation.email;
            if(response.data.birthday !== null) {
                $scope.UserInformation.birthday = new Date(response.data.birthday);
                $scope.UserInformation.birthdayOld = $scope.UserInformation.birthday;
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
        clearClass();
        $http({
            url: contextPathAuth + '/save',
            method: 'POST',
            data: setUserInformation
        }).then(function (response) {
            $scope.loadInformation();
        }).catch(function (response) {
            alert(response.data.message)
        });
    };

    $scope.passwordMatching = function (){
        let password1 = $scope.UserInformation.password1;
        let password2 = $scope.UserInformation.password2;
        if (password1 === undefined || password1 === ''){
            $('#pass1').removeClass('border-success').addClass('border-danger');
        } else {
            $('#pass1').removeClass('border-danger');
        }
        if (password2 === undefined || password2 === ''){
            $('#pass2').removeClass('border-success').addClass('border-danger');
        } else {
            $('#pass2').removeClass('border-danger');
        }
        if (password1 !== password2){
            $('#pass1').removeClass('border-success');
            $('#pass2').addClass('border-danger');
        }
        if (password1 === password2){
            $('#pass1').removeClass('border-danger').addClass('border-success');
            $('#pass2').removeClass('border-danger').addClass('border-success');
            $scope.UserInformation.password = password1;
        }
    }

    $scope.phoneMatching = function (){
        if ($scope.UserInformation.phone !== $scope.UserInformation.phoneOld){
            const re = /^[\d\+][\d\(\)\ -]{7,14}\d$/;
            let valid = re.test($scope.UserInformation.phone);
            if (valid) {
                $('#phone').removeClass('border-danger').addClass('border-success');
            } else {
                $('#phone').removeClass('border-success').addClass('border-danger');
            }
        } else {
            $('#phone').removeClass('border-danger').removeClass('border-success');
        }
    }

    $scope.emailMatching = function (){
        if($scope.UserInformation.email !== $scope.UserInformation.emailOld){
            const re = /^[\w-\.]+@[\w-]+\.[a-z]{2,4}$/i;
            let valid = re.test($scope.UserInformation.email);
            if (valid) {
                $('#email').removeClass('border-danger').addClass('border-success');
            } else {
                $('#email').removeClass('border-success').addClass('border-danger');
            }
        } else {
            $('#email').removeClass('border-danger').removeClass('border-success');
        }
    }

    $scope.userNameMatching = function () {
        if($scope.UserInformation.username !== $scope.UserInformation.usernameOld){
            $http({
                url: contextPathAuth + '/valid_name',
                method: 'POST',
                params: {
                    newUserName: $scope.UserInformation.username
                }
            }).then(function (response) {
                if(response.data.message === 'Ok'){
                    $('#userName').removeClass('border-danger').addClass('border-success');
                    $scope.UserInformation.usernameMessage = '';
                } else {
                    $('#userName').removeClass('border-success').addClass('border-danger');
                    $scope.UserInformation.usernameMessage = response.data.message;
                }
            });
        } else {
            $('#userName').removeClass('border-danger').removeClass('border-success');
            $scope.UserInformation.usernameMessage = '';
        }
    };

    $scope.resetChanges = function () {
        $scope.UserInformation.password1 = null;
        $scope.UserInformation.password2 = null;
        $scope.UserInformation.username = $scope.UserInformation.usernameOld;
        $scope.UserInformation.phone = $scope.UserInformation.phoneOld;
        $scope.UserInformation.email = $scope.UserInformation.emailOld;
        $scope.UserInformation.birthday = $scope.UserInformation.birthdayOld;
        clearClass();
    };

    function clearClass(){
        $('#phone').removeClass('border-danger').removeClass('border-success');
        $('#email').removeClass('border-danger').removeClass('border-success');
        $('#pass1').removeClass('border-danger').removeClass('border-success');
        $('#pass2').removeClass('border-danger').removeClass('border-success');
        $('#userName').removeClass('border-danger').removeClass('border-success');
    }

    $scope.loadInformation();

});