'use strict';

angular.module('xhc.app.config', ['ui.router'])

    .config(function($stateProvider, $urlRouterProvider) {

        // in this state user is logged out
        var loginState = {
            name: 'login',
            url: '/login',
            views: {
                'header-view': {
                    component: 'headerComponent'
                },
                'login-view': {
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
        var appState = {
            name: 'app',
            url: '/app',
            views: {
                'header-view': {
                    component: 'headerComponent'
                },
                'app-view': {
                    component: 'appComponent'
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

        $stateProvider.state(loginState);
        $stateProvider.state(appState);
//        $urlRouterProvider.otherwise('login');
    });