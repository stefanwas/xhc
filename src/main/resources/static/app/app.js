'use strict';

// Declare app level module which depends on views, and components
angular.module('xhc.app', ['ui.router', 'xhc.app.config', 'xhc.app.services'])


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
                    $state.go('login', {user: null, authenticated: false});
                });
            };
        }]
    })

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
                        $state.go('app', {user: $scope.credentials.username, authenticated: true});
                    },
                    function(result) {
                    //TODO add login error
                        $scope.authenticated = false;
                        console.log("LoginComponent: Login result error: " + result.code);
                    }
                );
            };
        }]
    })

    // this component will be displayed when user logged in
    .component('appComponent', {

        templateUrl : 'templates/app.html',

        controller: ['$scope', '$state', '$stateParams', 'SearchService', 'DocumentService',
                function($scope, $state, $stateParams, SearchService, DocumentService) {

            $scope.authenticated = $stateParams.authenticated;
            $scope.user = $stateParams.user;

//            $scope.logout = function() {
//
//                console.log("header logout called.");
//
//                LogoutService.get({}, function(result) {
//                    $scope.authenticated = false;
//                    console.log("Logout result: " + result)
//                    $state.go('out', {user: null, authenticated: false});
//                });
//            };

            $scope.documents = [];
            $scope.original = {};
            $scope.document = {};

            $scope.titleChanged = false;
            $scope.contentChanged = false;


            function setOriginal(document) {
                $scope.original = _.clone(document);
            }

            function removeFromDocuments(documentId) {
                _.remove($scope.documents, {id: documentId});
            }

            $scope.search = function(searchPhrase) {
                console.log("SEARCH=" + searchPhrase);
                $scope.documents = SearchService.query({phrase: searchPhrase});
                $scope.document = {};
                setOriginal({});
            };

            $scope.open = function(documentId) {
                console.log("OPEN=" + documentId);
                $scope.document = DocumentService.get(
                    {documentId: documentId},
                    function(document) {
                        $scope.document = document;
                        setOriginal($scope.document);
                    }
                );
            };

            $scope.detectChanges = function() {
                if ($scope.original.title != $scope.document.title) {
                    $scope.titleChanged = true;
                } else {
                    $scope.titleChanged = false;
                }

                if ($scope.original.content != $scope.document.content) {
                    $scope.contentChanged = true;
                } else {
                    $scope.contentChanged = false;
                }
            };

            $scope.save = function() {
                console.log("SAVE=" + $scope.document.id);
                DocumentService.save($scope.document, function(document) {
                    $scope.document = document;
                    setOriginal($scope.document);
                    $scope.titleChanged = false;
                    $scope.contentChanged = false;
                });
            };

            $scope.delete = function() {
                var documentId = $scope.document.id;
                console.log("DELETE=" + documentId);
                if (!_.isEmpty(documentId)) {
                    $scope.document = DocumentService.delete({documentId: documentId});
                    removeFromDocuments(documentId);
                    $scope.new();
                }
            };

            $scope.new = function() {
                console.log("NEW!");
                $scope.document = {
                    id : null,
                    title : "",
                    content : ""
                };
                setOriginal($scope.document);
                $scope.titleChanged = false;
                $scope.contentChanged = false;
            };

            // TODO trzeba zrobić autonawigację do login jeśli nie authenticated
            $scope.search();
            $scope.new();

        }]
    })


    .controller('DocumentController', ['$scope', '$state', 'DocumentService', 'SearchService', 'UserService',
        function ($scope, $state, DocumentService, SearchService, UserService) {

        $scope.authenticated = false;

        UserService.get({},
            function(result) {
                $scope.authenticated = true;
                console.log("Get user success: " + result.principal.username);
                $state.go('app', {authenticated: true, user: result.principal.username})
            },
            function(result) {
                $scope.authenticated = false;
//                console.log("Get user failed: " + result.code) // TODO to nie działa, dlaczego?
                $state.go('login', {authenticated: false, user: null});
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

