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
app.controller('AlunoController', AlunoController);


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
            data: dados
        };
        $http(req).success(function (data, status) {
            $window.location.href = 'index.html';
        });
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
            data: dados
        };
        $http(req).error(function (data, status) {
            $window.location.href = 'login.html';
        });
    };
}


function SendLoginController($scope, $http, $window) {
    this.mensagem = "";
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
        console.log($scope.form);
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



function AlunoController($scope, $http, $window) {
    this.mensagem = "";

    this.professores = {};
    this.chamada = {};
    this.chamada.comando = "listarProfessores";
    /*req = {
     method: 'POST',
     url: 'ProfessorServlet',
     headers: {
     'Content-Type': 'application/json'
     },
     data: this.chamada
     };
     $http(req)
     .success(function (data, status) {
     this.professores = data;
     });
     */


    $http.post('ProfessorServlet', this.chamada).
            success(function (data) {
                $scope.professores = data;
            }).
            error(function (data) {
                // log error
            });


    $scope.form = {};
    // calling our submit function.
    $scope.submitForm = function () {
        $scope.mensagem = "";
        $scope.form.comando = "cadastrarAluno";
        console.log($scope.form);
        req = {
            method: 'POST',
            url: 'AlunoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                    alert("Cadastro realizado com sucesso!");
                    $window.location.href = 'alunolistar.html';
                    }
                })
                .error(function (data, status) {
                    if (status === 401) {
                        $scope.mensagem = "Você não tem permissão para realizar esta ação.";
                    }
                    else if (status === 500) {
                        $scope.mensagem = "Houve um problema ao reconhecer os dados digitados.";
                    }
                });
    };
}