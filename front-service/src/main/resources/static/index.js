(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        // .configuration(config)
        .run(run);


    function config($routeProvider){
        $routeProvider
            // .when('/', {
            //     templateUrl: 'index.html',
            //     controller: 'index.js'
            // })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .when('/', {
                templateUrl: 'product/product.html',
                controller: 'productController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage, $location) {
        if ($localStorage.webmarketUser) {
            try {
                let jwt = $localStorage.webmarketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Время жизни токена истекло");
                    delete $localStorage.webmarketUser;
                    $http.defaults.headers.common.Authorization = '';
                    $location.path('/')
                } else {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;
                }
            } catch (e) {
            }
        }
    }
})();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const contextPathProducts = 'http://localhost:8066/products/api/v1';
    const contextPathOrder = 'http://localhost:8066/order/api/v1';
    const contextPathAuth = 'http://localhost:8066/auth/api/v1';
    const contextPathCart = 'http://localhost:8066/cart/api/v1';
    $scope.modalStatus = null;



    $scope.authentications = function () {
        $http.post(contextPathAuth + '/token', $scope.auth)
            .then(function (response) {
                if(response.data.token){
                    console.log("Токен получен")
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webmarketUser = {username: $scope.auth.username, token: response.data.token};
                    // $scope.buttonCart();
                    $('#authRes').click();
                    $scope.showCartCount();

                    $location.path('/');
                }
            }).catch(function (response) {
            // console.log(response.data.message)
            $scope.modalStatus = response.data.message;
        });
    };

    $scope.showCart = function () {
        $http({
            url: contextPathCart + "/cart",
            method: 'GET',
        }).then(function (response) {
            // console.log(response.data)
            // console.log(response.data.cart)
            $scope.CardList = response.data.cart;
            $scope.CardTotalSize = $scope.CardList.length;
            let total = 0;
            let summ = 0;
            for (let p of $scope.CardList) {
                let count = p.count;
                let price = p.price;
                total += count;
                summ += (count * price);
            }
            $scope.CardTotalProduct = total;
            $scope.CardTotalSumm = summ;
        });
    };

    $scope.showCartCount = function () {
        if($localStorage.webmarketUser){
            $http({
                url: contextPathCart + "/cart/count",
                method: 'GET',
            }).then(function (response) {
                $scope.CardTotalProduct = response.data;
            });
        }
    };

    $scope.createOrder = function () {
        $http({
            url: contextPathOrder + "/orders/create",
            method: 'GET'
        }).then(function (response) {
            $scope.CardList = null;
            $scope.CardTotalProduct = 0;
            $('#cartRes').click();
            alert("Заказ оформлен успешно")
        }).catch(function (response){
            alert(response.data.message)
        });
    };

    $scope.setCountToCart = function (id, count) {
        $http({
            url: contextPathCart + "/cart/add_to_cart",
            method: 'GET',
            params: {
                id: id,
                count: count
            }
        }).then(function (response) {
            $scope.showCartCount();
            $scope.showCart();
        });
    };

    $scope.deleteProductFromCart = function (id) {
        $http({
            url: contextPathCart + "/cart/dell_from_cart",
            method: 'GET',
            params: {
                id: id
            }
        }).then(function (response) {
            $scope.showCart();
        });
    };

    $scope.clearUser = function (){
        delete $localStorage.webmarketUser;
        $http.defaults.headers.common.Authorization = '';
        $location.path('/')
    }

    $scope.ifUserLoggedIn = function (){
        return !!$localStorage.webmarketUser;
    }

    $scope.filter = null;
    $scope.showCartCount();
});

