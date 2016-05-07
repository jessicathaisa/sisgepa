/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
var app = angular.module('sisgepa', []);
app.controller('RelatorioController', RelatorioController);
app.controller('SendLoginController', SendLoginController);
app.controller('VerificaLoginController', VerificaLoginController);


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
