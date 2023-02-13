angular.module('app', ['ngStorage']).controller('orderController', function ($scope, $http, $localStorage) {
    const contextPathCore = 'http://localhost:8888/market-core/api/v1';

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
            url: contextPathCore + '/orders',
            method: 'GET'
        }).then(function (response) {
            console.log(response.data)
            $scope.OrderList = response.data;
        });
    };


    $scope.getOrderById = function (id){
        $http({
            url: contextPathCore + "/orders/" + id,
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