/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */

app.controller('ProjetoController', ProjetoController);

function ProjetoController($scope, $http, $window, $location, $q) {
    this.mensagem = "";
    var idProjeto = $location.search().id;
    var listar = $location.absUrl();
    $scope.projetos = {};
    this.chamada = {};

    $scope.trazData = function (data) {
        if (!data)
            return "-";
        else
            return (new Date(data)).getTime();
    };

    if (listar.indexOf("projetolistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarProjetos";
        $http.post('ProjetoServlet', this.chamada).
                success(function (data) {
                    $scope.projetos = data;
                }).
                error(function (data) {
                    // log error
                });
    } else if (listar.indexOf("projetover.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarProjeto";
        this.chamada.identificador = idProjeto;
        this.orientador = "";
        // Buscar pelo id
        $http.post('ProjetoServlet', this.chamada).
                success(function (data) {
                    $scope.form = data;
                }).
                error(function (data) {
                    // log error
                });
    }
    
    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "cadastrarProjeto";
        
        if($scope.form.dataInicio && $scope.form.dataTermino && ($scope.form.dataInicio > $scope.form.dataTermino)){
            $scope.mensagem = "Data de início não pode ser maior que a de término";
            return;
        }
        
        req = {
            method: 'POST',
            url: 'ProjetoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        $window.location.href = 'projetolistar.html';
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


