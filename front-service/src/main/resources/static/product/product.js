angular.module('market').controller('productController', function ($scope, $http, $localStorage) {
    const contextPathProducts = 'http://localhost:8066/products/api/v1';
    const contextPathCart = 'http://localhost:8066/cart/api/v1';
    let number = 1;
    let totalNumber;
    $scope.modalStatus = null;

    // if($localStorage.webmarketUser){
    //     try {
    //         let jwt = $localStorage.webmarketUser.token;
    //         let payload = JSON.parse(atob(jwt.split('.')[1]));
    //         let currentTime = parseInt(new Date().getTime() / 1000);
    //         if (currentTime > payload.exp){
    //             console.log("Время жизни токена истекло");
    //             delete $localStorage.webmarketUser;
    //             $http.defaults.headers.common.Authorization = '';
    //             // $scope.clearUser();
    //         } else {
    //             $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;
    //         }
    //     } catch (e){
    //     }
    // }

    $scope.updateProducts = function () {
        let min;
        let max;
        let bf;
        let cat;
        let sub_cat;
        let man;
        if ($scope.filter !== null){
            bf = true;
            min = $scope.filter.min !== undefined ? $scope.filter.min : null;
            max = $scope.filter.max !== undefined ? $scope.filter.max : null;
            cat = ($scope.filter.cat !== undefined && $scope.filter.cat !== null) ? $scope.filter.cat.id : null;
            sub_cat = ($scope.filter.sub_cat !== undefined && $scope.filter.sub_cat !== null) ? $scope.filter.sub_cat.id : null;
            man = $scope.filter.man !== undefined ? $scope.filter.man : null;
        }
        $http({
            url: contextPathProducts + "/products",
            method: 'POST',
            params: {
                val: $scope.value !== null ? $scope.value : null,
                min: bf ? min : null,
                max: bf ? max : null,
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
            url: contextPathProducts + "/products",
            method: 'GET'
        }).then(function (response) {
            $scope.pagination(response);
            $scope.ProductsList = response.data.content;
            // console.log(response.data)
        });
    };

    $scope.getProduct = function (id) {
        $http({
            url: contextPathProducts + "/products/" + id,
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
            cat = ($scope.filter.cat !== undefined && $scope.filter.cat !== null) ? $scope.filter.cat.id : null;
            sub_cat = ($scope.filter.sub_cat !== undefined && $scope.filter.sub_cat !== null) ? $scope.filter.sub_cat.id : null;
        }
        $http({
            url: contextPathProducts + "/manufacturers",
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

    $scope.addToCart = function (id, count) {
        $http({
            url: contextPathCart + "/cart/add_to_cart",
            method: 'GET',
            params: {
                id: id,
                count: count
            }
        }).then(function (response) {
            $scope.showCartCount();
        });
    };

    $scope.searchForm = function () {
        number = 1;
        $scope.updateProducts();
        $scope.manufacturer();
    };

    $scope.categories = function () {
        $http({
            url: contextPathProducts + "/categories",
            method: 'POST'
        }).then(function (response) {
            // console.log(response.data);
            $scope.CategoriesList = response.data;
        });
    };

    $scope.catForm = function () {
        if($scope.filter !== null && $scope.filter.cat !== null){
            $('#sub').prop('disabled', false);
            $http({
                url: contextPathProducts + "/categories",
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

    $scope.ifUserLoggedIn = function (){
        return !!$localStorage.webmarketUser;
    }

    $scope.filter = null;
    $scope.loadProducts();
    $scope.categories();
    $scope.manufacturer();
    $('#sub').prop( 'disabled',true );

});