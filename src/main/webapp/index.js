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

        function setOriginal(document) {
            $scope.original = _.clone(document);
        }

        function removeFromDocuments(documentId) {
            _.remove($scope.documents, {id: documentId});
        }

        $scope.search = function(searchPhrase) {
            console.log("SEARCH=" + searchPhrase);
            $scope.documents = SearchService.query({phrase: searchPhrase});
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

        $scope.autosave = function() {
        //TODO improve save condition
            console.log("AUTOSAVE=" + $scope.document.id);
            if (!_.isEmpty($scope.document.title) && !_.isEmpty($scope.document.content)
                && ($scope.original.title != $scope.document.title || $scope.original.content != $scope.document.content)) {
                $scope.save();
            }

        }

        $scope.save = function() {
            console.log("SAVE=" + $scope.document.id);
            DocumentService.save($scope.document, function(document) {
                if ($scope.document.id == null) {
                    $scope.document.id = document.id;
                }
            });
        };

        $scope.delete = function() {
            var documentId = $scope.document.id;
            console.log("DELETE=" + documentId);
            if (!_.isEmpty(documentId)) {
                $scope.document = DocumentService.delete({documentId: documentId});
                removeFromDocuments(documentId);
            }
        };

        $scope.create = function() {
            console.log("CREATE=NEW");
            $scope.document = {
                id : null,
                title : "",
                content : ""
            };
            setOriginal($scope.document);
        };

    }]);
