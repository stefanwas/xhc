'use strict';

angular.module('xhc.app.config', ['ui.router'])

    .config(function($stateProvider, $urlRouterProvider) {

        // in this state user is logged out
        var outState = {
            name: 'out',
            url: '/out',
            views: {
                'header-view': {
                    component: 'headerComponent'
                },
                'body-view': {
                    component: 'loginComponent'
                },
                'footer-view': {
                    templateUrl: 'templates/footer.html'
                }
            },
            params: {
                user:null,
                authenticated : false
            }
        };

        // in this state user is logged in
        var inState = {
            name: 'in',
            url: '/in',
            views: {
                'header-view': {
                    component: 'headerComponent'
                },
                'body-view': {
                    component: 'mainComponent'
                },
                'footer-view': {
                 templateUrl: 'templates/footer.html'
                }
            },
            params: {
                user:null,
                authenticated : true
            }
        };

        $stateProvider.state(outState);
        $stateProvider.state(inState);
        $urlRouterProvider.otherwise('out');    // TODO maybe not needed?
    });