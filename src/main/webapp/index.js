'use strict';

// Declare app level module which depends on views, and components
angular.module('xhc.app', ['ngResource'])
    .factory('DocumentService', ['$resource', function($resource) {
        return $resource('document/:documentId', {documentId: '@documentId'});
    }])
    .factory('SearchService', ['$resource', function($resource) {
        return $resource('document/search', {phrase: '@phrase'});
    }])
    .controller('DocumentController', ['$scope', 'SearchService', function ($scope, SearchService) {

        $scope.name = "Wojtek";
        $scope.documents = [];

        $scope.search = function(text) {
            $scope.documents = SearchService.query({phrase: text});
            console.log("SEARCH=" + $scope.documents.length);
        };

    }]);
