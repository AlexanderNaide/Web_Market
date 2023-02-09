angular.module('app', []).controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/orders';


    $scope.loadOrders = function () {
        $http({
            url: contextPath,
            method: 'GET'
        }).then(function (response) {
            $scope.pagination(response);
            $scope.OrderList = response.data.content;
            // console.log(response.data)
        });
    };

    $scope.loadOrders();

});