'use strict';

// Declare app level module which depends on views, and components
angular.module('xhc.app', ['ngResource'])

    .factory('DocumentService', ['$resource', function($resource) {
        return $resource('document/:documentId', {documentId : '@documentId'});
    }])

    .factory('SearchService', ['$resource', function($resource) {
        return $resource('document/search', {phrase: '@phrase'});
    }])

    .controller('DocumentController', ['$scope', 'DocumentService', 'SearchService', function ($scope, DocumentService, SearchService) {

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

        $scope.search();
        $scope.new();
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

