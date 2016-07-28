/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
var app = angular.module('sisgepa', []);
app.controller('VerificaLoginController', VerificaLoginController);
app.controller('VerificaPermissaoController', VerificaPermissaoController);
app.controller('SendLoginController', SendLoginController);
app.controller('SendLogoutController', SendLogoutController);


function VerificaLoginController($scope, $http, $window) {
    // calling our submit function.
    $scope.verificaLogin = function () {
        var dados = {};
        dados.comando = "verificaLogado";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).success(function (data, status) {
            $window.location.href = 'index.html';
        });
    };
    // calling our submit function.
    $scope.verificaNaoLogado = function () {
        var dados = {};
        dados.comando = "verificaLogado";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).error(function (data, status) {
            $window.location.href = 'login.html';
        });
    };
}

function VerificaPermissaoController($scope, $http, $window) {
    $scope.verificaEhAdministrador = function () {
        var dados = {};
        dados.comando = "verificaAdministrador";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).error(function (data, status) {
            alert("Você não tem permissão para realizar esta ação. Será redirecionado para a página inicial.");
            $window.location.href = 'index.html';
        });
    };
    
    $scope.verificaEhGerente = function () {
        var dados = {};
        dados.comando = "verificaGerente";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).error(function (data, status) {
            alert("Você não tem permissão para realizar esta ação. Será redirecionado para a página inicial.");
            $window.location.href = 'index.html';
        });
    }
}

function SendLoginController($scope, $http, $window) {
    this.mensagem = "";
    $scope.form = {};
    // calling our submit function.
    $scope.submitForm = function () {
        $scope.mensagem = "";
        $scope.form.comando = "realizarLogin";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    $window.location.href = 'index.html';
                })
                .error(function (data, status) {
                    if (status === 401) {
                        $scope.mensagem = "Usuário ou Senha inválidos";
                    }
                });
    };
}


function SendLogoutController($scope, $http, $window) {
    $scope.realizarLogout = function () {
        $scope.mensagem = "Realizando o Logout...";
        var dados = {};
        dados.comando = "realizarLogout";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).success(function (data, status) {
            $window.location.href = 'login.html';
        })
                .error(function (data, status) {
                    if (status === 401)
                        $window.location.href = 'login.html';
                    else
                        $scope.mensagem = "Erro ao realizar logout.";
                });
    };
}