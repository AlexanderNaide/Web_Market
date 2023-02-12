angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    // const contextPath = 'http://localhost:8080';
    const contextPathProducts = 'http://localhost:8084/market-products';
    const contextPathAuth = 'http://localhost:8080/auth';
    const contextPathCart = 'http://localhost:8080/api/v1/cart';
    let number = 1;
    let totalNumber;
    $scope.modalStatus = null;

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

    $scope.updateProducts = function () {
        let min;
        let max;
        let bF;
        let cat;
        let sub_cat;
        let man;
        if ($scope.filter !== null){
            bF = true;
            min = $scope.filter.min !== null ? $scope.filter.min : null;
            max = $scope.filter.max !== null ? $scope.filter.max : null;
            cat = $scope.filter.cat !== "Все" ? $scope.filter.cat.id : null;
            sub_cat = $scope.filter.sub_cat !== "Все" ? $scope.filter.sub_cat.id : null;
            man = $scope.filter.man !== "Все" ? $scope.filter.man : null;
        }
        $http({
            url: contextPathProducts + "/api/v1/products",
            method: 'POST',
            params: {
                val: $scope.value !== null ? $scope.value : null,
                min: bF ? min : null,
                max: bF ? max : null,
                cat: cat,
                sub_cat: sub_cat,
                man: man,
                page: number
            }
        }).then(function (response) {
            $scope.pagination(response);
            $scope.ProductsList = response.data.content;
            // console.log(response.data)
            });
    };

    $scope.loadProducts = function () {
        $http({
            url: contextPathProducts + "/api/v1/products",
            method: 'GET'
        }).then(function (response) {
            $scope.pagination(response);
            $scope.ProductsList = response.data.content;
            // console.log(response.data)
        });
    };

    $scope.createOrder = function () {
        $http({
            url: "http://localhost:8080/orders/create",
            method: 'PUT'
        }).then(function (response) {
            $scope.CardList = null;
            $('#cartRes').click();
            alert("Заказ оформлен успешно")
        }).catch(function (response){
            alert(response.data.message)
        });
    };

    $scope.getProduct = function (id) {
        $http({
            url: contextPathProducts + "/api/v1/products/" + id,
            // url: contextPath + "/" + 5965165165,
            method: 'GET'
        }).then(function (response) {
            $scope.Product = response.data;
            let descStr = response.data.description;
            let st = descStr.indexOf("<");
            if(st === -1){
                $scope.ProductDescription = descStr.split("; ");
            } else {
                let desc = descStr.slice(0, st);
                $scope.ProductDescription = desc.split("; ");
            }
        }).catch(function (response) {
            alert(response.data.message)
        });
    };

    $scope.manufacturer = function () {
        let cat;
        let sub_cat;
        if ($scope.filter !== null){
            cat = $scope.filter.cat !== "Все" ? $scope.filter.cat.id : null;
            sub_cat = $scope.filter.sub_cat !== "Все" ? $scope.filter.sub_cat.id : null;
        }
        $http({
            url: contextPathProducts + "/api/v1/manufacturers",
            method: 'GET',
            params: {
                cat: cat,
                sub_cat: sub_cat
            }
        }).then(function (response) {
            // console.log(response.data)
            $scope.ManufacturerList = response.data;
        });
    };

    $scope.showCart = function () {
        $http({
            url: contextPathCart,
            method: 'GET',
            // headers: {
            //     Authorization: "Bearer " + token
            // }
        }).then(function (response) {
            $scope.CardList = response.data;
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
                url: contextPathCart + "/count",
                method: 'GET',
                // headers: {
                //     Authorization: "Bearer " + token
                // }
            }).then(function (response) {
                $scope.CardTotalProduct = response.data;
            });
        }
    };

    $scope.addToCart = function (id, count) {
        $http({
            url: contextPathCart + "/add_to_cart",
            method: 'GET',
            // headers: {
            //     Authorization: "Bearer " + token
            // },
            params: {
                id: id,
                count: count
            }
        }).then(function (response) {
            $scope.showCartCount();
        });
    };

    $scope.setCountToCart = function (id, count) {
        $http({
            url: contextPathCart + "/add_to_cart",
            method: 'GET',
            // headers: {
            //     Authorization: "Bearer " + token
            // },
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
            url: contextPathCart + "/dell_from_cart",
            method: 'GET',
            // headers: {
            //     Authorization: "Bearer " + token
            // },
            params: {
                id: id
            }
        }).then(function (response) {
            $scope.showCart();
        });
    };

    $scope.searchForm = function () {
        number = 1;
        $scope.updateProducts();
        $scope.manufacturer();
    };

    $scope.categories = function () {
        $http({
            url: contextPathProducts + "/api/v1/categories",
            method: 'POST'
        }).then(function (response) {
            // console.log(response.data);
            $scope.CategoriesList = response.data;
        });
    };

    // $scope.catForm = function () {
    //     if($scope.filter !== null && $scope.filter.cat !== null){
    //         $('#sub').prop('disabled', false);
    //         $http.post(contextPath + "/api/v1/products/test", $scope.filter.cat).then(function (response) {
    //             if(response.data.length === 0){
    //                 $scope.SubCategoriesList = null;
    //                 $('#sub').prop( 'disabled',true );
    //             } else {
    //                 console.log(response.data);
    //                 $scope.SubCategoriesList = response.data;
    //             }
    //         });
    //     } else {
    //         $scope.SubCategoriesList = null;
    //         $('#sub').prop( 'disabled',true );
    //     }
    // };

    $scope.catForm = function () {
        if($scope.filter !== null && $scope.filter.cat !== null){
            $('#sub').prop('disabled', false);
            $http({
                url: contextPathProducts + "/api/v1/categories",
                method: 'POST',
                params: {
                    cat: $scope.filter.cat.id
                }
            }).then(function (response) {
                if(response.data.length === 0){
                    $scope.SubCategoriesList = null;
                    $('#sub').prop( 'disabled',true );
                } else {
                    // console.log(response.data);
                    $scope.SubCategoriesList = response.data;
                }
            });
        } else {
            $scope.SubCategoriesList = null;
            $('#sub').prop( 'disabled',true );
        }
    };

    $scope.addFilter = function () {
        number = 1;
        $scope.updateProducts();
    };

    $scope.resetFilter = function () {
        $scope.filter = null;
        $scope.categories();
        $scope.catForm();
        $scope.manufacturer();
        $scope.updateProducts();
    };

    $scope.pagination = function (response) {
        totalNumber = response.data.totalPages;
        $scope.totalNumber = response.data.totalPages;
        $scope.first = response.data.first === true ? 'disabled' : null;
        $scope.first10 = response.data.number < 10 ? 'disabled' : null;
        $scope.page1 = response.data.number + 1;
        $scope.last10 = response.data.number > totalNumber - 11 ? 'disabled' : null;
        $scope.last = response.data.last === true ? 'disabled' : null;
    };

    $scope.pageClick = function (delta) {
        number = number + delta;
        $scope.updateProducts();
    };

    $scope.pageStart = function () {
        number = 1;
        $scope.updateProducts();
    };

    $scope.pageFinish = function () {
        number = totalNumber;
        $scope.updateProducts();
    };

/*    $scope.authentications = function () {
        $http.post(contextPathAuth + '/token', $scope.auth)
            .then(function (response) {
                // console.log(response.data);
                token = response.data.token;
                $scope.buttonCart();
                $('#authRes').click();
            }).catch(function (response) {
                // console.log(response.data.message)
            $scope.modalStatus = response.data.message;
        });
    };*/

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

    // $scope.buttonCartFunction = function () {
    //     if(token !== null){
    //         $scope.showCart();
    //     }
    // };

    // $scope.buttonCart = function () {
    //     $scope.buttonCartTitle = token === null ? 'Авторизация' : 'Корзина';
    //     $scope.buttonTargetClick = token === null ? '#modalAuthentication' : '#exampleModalCart';
    // };

    // $scope.visibleButtonAddToCart = function () {
    //     return token !== null;
    // };

    $scope.filter = null;
    $scope.loadProducts();
    $scope.categories();
    $scope.manufacturer();
    $scope.showCartCount();
    $('#sub').prop( 'disabled',true );

});