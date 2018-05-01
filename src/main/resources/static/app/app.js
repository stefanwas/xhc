'use strict';

// Declare app level module which depends on views, and components
angular.module('xhc.app', [
        'ui.router',
        'xhc.app.config',
        'xhc.app.services',
        'xhc.app.component.header',
        'xhc.app.component.login',
        'xhc.app.component.main'])


    .controller('DocumentController', ['$scope', '$state', 'DocumentService', 'SearchService', 'UserService',
        function ($scope, $state, DocumentService, SearchService, UserService) {

        $scope.authenticated = false;

        UserService.get({},
            function(result) {
                $scope.authenticated = true;
                console.log("Get user success: " + result.principal.username);
                $state.go('in', {authenticated: true, user: result.principal.username})
            },
            function(result) {
                $scope.authenticated = false;
//                console.log("Get user failed: " + result.code) // TODO to nie działa, dlaczego?
                $state.go('out', {authenticated: false, user: null});
            }
        );


    }])

    .directive('ngEnter', function() {
        return function(scope, element, attrs) {
            element.bind("keydown keypress", function(event) {
                if(event.which === 13) {
                    scope.$apply(function(){
                        scope.$eval(attrs.ngEnter, {'event': event});
                    });
                    event.preventDefault();
                }
            });
        };
    });

