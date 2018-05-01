'use strict';

angular.module('xhc.app.component.login', ['ui.router', 'xhc.app.services'])

    // this component will be displayed when user logged out
    .component('loginComponent', {

        templateUrl : 'templates/login.html',

        controller: ['$scope', '$state', 'LoginService', function($scope, $state, LoginService) {
            $scope.user = null;     // it is also possible to use this instead of $scope

            $scope.credentials = {};
            $scope.credentials.username = "";
            $scope.credentials.password = "";

            $scope.login = function() {
                console.log("LoginComponent: login called. U=" + $scope.credentials.username + ", P=" + $scope.credentials.password);

                LoginService.save(
                    { username: $scope.credentials.username, password: $scope.credentials.password },
                    function(result) {
                        $scope.authenticated = true;
                        console.log("LoginComponent: Login result success: " + result.name);
                        $state.go('in', {user: $scope.credentials.username, authenticated: true});
                    },
                    function(result) {
                    //TODO add login error
                        $scope.authenticated = false;
                        console.log("LoginComponent: Login result error: " + result.code);
                    }
                );
            };
        }]
    });