var app = angular.module('webApp', ['ngRoute']);

app.controller('furnitureStore', function($scope, $http, $location){
	var baseUrl = '/api/items';
	var baseUrlCategory = '/api/categories';
	
	$scope.items = [];
	$scope.categories = [];
	
	$scope.newItem = {};
	$scope.searchItem = {};
	
	$scope.pageNum = 0;
	$scope.totalPages = 1;
	
	var getItems = function(){
		var conf = {params: {}};
		
		if($scope.searchItem.itemName != ''){
			conf.params.itemName = $scope.searchItem.itemName;
		}
		if($scope.searchItem.categoryId != ''){
			conf.params.categoryId = $scope.searchItem.categoryId;
		}
		if($scope.searchItem.maxPrice != ''){
			conf.params.maxPrice = $scope.searchItem.maxPrice;
		}
		
		conf.params.pageNum = $scope.pageNum;
		
		var promise = $http.get(baseUrl, conf).then(
				function success(answ){
					$scope.items = answ.data;
					$scope.totalPages = answ.headers('totalPages');
				},
				function error(answ){
					alert('Something went wrong!');
				}
		);
	}
	
	var getCategories = function() {
		var promise = $http.get(baseUrlCategory).then(
				function success(answ){
					$scope.categories = answ.data;
				},
				function error(answ){
					alert('Something went wrong!')
				}
		);
	}
	
	getItems();
	getCategories();
	
	$scope.search = function(){
		getItems();
	}
	
	$scope.changePage = function(direction){
		$scope.pageNum = $scope.pageNum + direction;
		getItems();
	}
	
	$scope.clearSearch = function(){
		$scope.searchItem.itemName = '';
		$scope.searchItem.categoryId = '';
		$scope.searchItem.maxPrice = '';
	}
	
	$scope.addItem = function(){
		var promise = $http.post(baseUrl, $scope.newItem).then(
			function success(answ){
				getItems();
				$scope.newItem.itemName = '';
				$scope.newItem.price = '';
				$scope.newItem.quantity = '';
				$scope.newItem.categoryId = 0;
			},
			function error(answ){
				alert('Something went wrong!');
			}
		);
	}
	
	$scope.deleteItem = function(itemId){
		var promise = $http.delete(baseUrl + '/' + itemId).then(
			function success(answ){
				if (!$scope.items[1] && $scope.totalPages > 1) {
					$scope.pageNum -= 1;
				}
				getItems();
			},
			function error(answ){
				alert('Something went wrong!');
			}
		);
	}
	
	$scope.goToEdit = function(id){
		$location.path('/items/edit/' + id );
	}
	
	$scope.buyItem = function(id, purchaseQuantity){
		if(purchaseQuantity == null){
			alert('You must enter quantity!');
			return;
		}
		$scope.purchase = {};
		$scope.purchase.itemId = id;
		$scope.purchase.quantity = purchaseQuantity;
		var promise = $http.post(baseUrl + '/purchase', $scope.purchase).then(
			function success(answ){
				getItems();
				alert('You have succesfully purchased ' + answ.data.itemName +'\nTotal cost: ' + answ.data.totalPrice);
			},
			function error(answ){
				alert('Not enough items on stock!');
				getItems();
			}
		);
	}
	
	$scope.defaultSearchButton = 'Search item';
	$scope.searchFormVisibility = false;
	
	$scope.searchButton = function(){
		$scope.searchFormVisibility = true;
		if($scope.defaultSearchButton == 'Search item'){
			$scope.defaultSearchButton = 'Close search';
		}else if($scope.defaultSearchButton == 'Close search'){
			$scope.defaultSearchButton = 'Search item';
			$scope.searchFormVisibility = false;
		}
	}
	
});

app.controller('itemEditController', function($scope, $routeParams, $http, $location, $timeout){
	var baseUrl = '/api/items';
	var baseUrlCategory = '/api/categories';
	var id = $routeParams.id;
	
	$scope.categories = [];
	$scope.oldItem = {};
	
	var getCategories = function() {
		var promise = $http.get(baseUrlCategory).then(
				function success(answ){
					$scope.categories = answ.data;
				},
				function error(answ){
					alert('Something went wrong!')
				}
		);
	}
	getCategories();
	
	var getItem = function(){
		$http.get(baseUrl + '/' + id).then(
			function success(answ){
				$timeout(function(){
					$scope.oldItem = answ.data;
				},0)
			},
			function error(answ){
				alert('Something went wrong!');
			}
		);		
	}
	
	getItem();
	
	$scope.edit = function(){
		$http.put(baseUrl + '/' + $scope.oldItem.id, $scope.oldItem).then(
			function success(answ){
				$location.path('/');
			},
			function error(answ){
				alert('Something went wrong!');
				console.log($scope.oldItem)
			}
		);
	}
});


app.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : '/app/partial/webShop.html'
		})
		.when('/webShop', {
			templateUrl : '/app/partial/webShop.html'
		})
		.when('/items/edit/:id', {
			templateUrl : '/app/partial/itemEdit.html'
		})
		.otherwise({
			redirectTo: '/'
		});
}]);
