/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
var app = angular.module('sisgepa', []);
app.controller('VerificaLoginController', VerificaLoginController);
app.controller('SendLoginController', SendLoginController);
app.controller('SendLogoutController', SendLogoutController);
app.controller('RelatorioController', RelatorioController);


function VerificaLoginController($scope, $http, $window) {
    // calling our submit function.
    $scope.verificaLogin = function () {
        var dados = {};
        dados.comando = "verificaLogado";
        console.log($scope.form);
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data : dados
        };
        $http(req).success(function (data, status) { $window.location.href = 'index.html';});
    };
    // calling our submit function.
    $scope.verificaNaoLogado = function () {
        console.log("VERIFICAR SE NÃO ESTÁ LOGADO");
        var dados = {};
        dados.comando = "verificaLogado";
        console.log($scope.form);
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data : dados
        };
        $http(req).error(function (data, status) { $window.location.href = 'login.html';});
    };
}


function SendLoginController($scope, $http, $window) {
    $mensagem = "";
    $scope.form = {};
    // calling our submit function.
    $scope.submitForm = function () {
        $scope.mensagem = "";
        $scope.form.comando = "realizarLogin";
        console.log($scope.form);
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
                    if(status === 401){
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
        console.log($scope.form);
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data : dados
        };
        $http(req).success(function (data, status) { $window.location.href = 'login.html';})
                .error(function (data, status) { if(status === 401)$window.location.href = 'login.html'; else $scope.mensagem="Erro ao realizar logout.";});
    };
}


function RelatorioController($scope, $http) {
    this.relatorio = {};
    $http.post('RelatorioServlet').
            success(function (data) {
                $scope.relatorio = data;
            }).
            error(function (data) {
                // log error
            });
}


/**************************************************/
