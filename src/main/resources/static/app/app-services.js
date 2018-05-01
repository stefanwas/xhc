'use strict';

angular.module('xhc.app.services', ['ngResource'])

    .factory('DocumentService', ['$resource', function($resource) {
        return $resource('document/:documentId', {documentId : '@documentId'});
    }])

    .factory('SearchService', ['$resource', function($resource) {
        return $resource('document/search', {phrase: '@phrase'});
    }])

    .factory('UserService', ['$resource', function($resource) {
        return $resource('user');
    }])

    .factory('LoginService', ['$resource', function($resource) {
        return $resource('login', { username: "@username", password: "@password" });
    }])

    .factory('LogoutService', ['$resource', function($resource) {
        return $resource('logout');
    }]);
