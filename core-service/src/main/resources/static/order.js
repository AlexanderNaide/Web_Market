angular.module('app', ['ngStorage']).controller('orderController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080';

    if($localStorage.webmarketUser){
        try {
            let jwt = $localStorage.webmarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp){
                window.location.href = 'index.html';
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;
            }
        } catch (e){
        }
    }


    $scope.loadOrders = function () {
        $http({
            url: contextPath + '/orders',
            method: 'GET'
        }).then(function (response) {
            console.log(response.data)
            $scope.OrderList = response.data;
        });
    };


    $scope.getOrderById = function (id){
        $http({
            url: contextPath + "/orders/" + id,
            method: 'GET'
        }).then(function (response) {
            console.log(response.data);
            $scope.currentOrder = response.data;
        }).catch(function (response) {
            alert(response.data.message)
        });
    };


    $scope.redirectHome = function (){
        window.location.href = 'index.html';
    };


    $scope.clearUser = function (){
        delete $localStorage.webmarketUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.redirectHome();
    };

    $scope.loadOrders();
});