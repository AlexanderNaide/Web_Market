angular.module('app', ['ngStorage']).controller('orderController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080/orders';

    if($localStorage.webmarketUser){
        try {
            let jwt = $localStorage.webmarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp){
                console.log("Время жизни токена истекло");
                delete $localStorage.webmarketUser;
                $http.defaults.headers.common.Authorization = '';
                // $scope.clearUser();
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;
            }
        } catch (e){
        }
    }


    $scope.loadOrders = function () {
        $http({
            url: contextPath,
            method: 'GET'
        }).then(function (response) {
            $scope.OrderList = response.data.content;
            // console.log(response.data)
        });
    };

    $scope.loadOrders();
});