/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */

app.controller('ProjetoController', ProjetoController);

function ProjetoController($scope, $http, $window, $location, $q) {
    this.mensagem = "";
    var listar = $location.absUrl();
    $scope.projetos = {};
    this.chamada = {};

    $scope.trazData = function(data){
        if(!data)
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
    }
}


