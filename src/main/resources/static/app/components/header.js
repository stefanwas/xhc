'use strict';

angular.module('xhc.app.component.header', ['ui.router', 'xhc.app.services'])

    // this component will be displayed always - when user logged in or out
    .component('headerComponent', {

        templateUrl : 'templates/header.html',

        controller: ['$scope', '$state', '$stateParams', 'LogoutService',
                function($scope, $state, $stateParams, LogoutService) {

            $scope.authenticated = $stateParams.authenticated;
            $scope.user = $stateParams.user;

            this.logout = function() {

                console.log("HeaderComponent: header logout called.");

                LogoutService.get({}, function(result) {
                    $scope.authenticated = false;
                    console.log("HeaderComponent: Logout result: " + result)
                    $state.go('out', {user: null, authenticated: false});
                });
            };
        }]
    });