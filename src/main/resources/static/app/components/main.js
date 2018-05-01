'use strict';

angular.module('xhc.app.component.main', ['ui.router', 'xhc.app.services'])

    // this component will be displayed when user logged in
    .component('mainComponent', {

        templateUrl : 'templates/main.html',

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
    });