(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        // .config(config)
        .configuration(config)
        .run(run);


    function config($routeProvider){
        $routeProvider
            .when('/', {
                templateUrl: 'index.html',
                controller: 'index.js'
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'order.js'
            })
            .when('/product', {
                templateUrl: 'product/product.html',
                controller: 'product.js'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webmarketUser) {
            try {
                let jwt = $localStorage.webmarketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Время жизни токена истекло");
                    delete $localStorage.webmarketUser;
                    $http.defaults.headers.common.Authorization = '';
                    // $scope.clearUser();
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
    let number = 1;
    let totalNumber;
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

    $scope.clearUser = function (){
        delete $localStorage.webmarketUser;
        $http.defaults.headers.common.Authorization = '';
    }

    $scope.ifUserLoggedIn = function (){
        return !!$localStorage.webmarketUser;
    }

    $scope.filter = null;
});

