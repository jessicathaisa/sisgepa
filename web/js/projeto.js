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
    $scope.status = {};
    $scope.dadosBasicosHabilitados = false;
    $scope.opcoesHabilitadas = true;

    $scope.trazData = function (data) {
        if (!data)
            return "-";
        else
            return (new Date(data)).getTime();
    };

    $scope.estaEmAndamento = function (situacao) {
        return situacao === "EM_ANDAMENTO";
    };

    $scope.estaConcluido = function (situacao) {
        return situacao === "CONCLUIDO";
    };

    $scope.estaEmElaboracao = function (situacao) {
        return situacao === "EM_ELABORACAO";
    };

    $scope.desabilitaTudo = function () {
        $scope.dadosBasicosHabilitados = false;
        $scope.opcoesHabilitadas = true;
    };

    $scope.iniciarEdicaoDadosBasicos = function () {
        $scope.desabilitaTudo();

        $scope.opcoesHabilitadas = false;
        $scope.dadosBasicosHabilitados = true;
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
    } else if (listar.indexOf("projetover.html") >= 0 || listar.indexOf("projetoeditar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarProjeto";
        this.chamada.identificador = idProjeto;
        this.orientador = "";
        // Buscar pelo id
        $http.post('ProjetoServlet', this.chamada).
                success(function (data) {
                    $scope.form = data;
                    $scope.status = data.status;
                    var d = new Date(data.dataInicio);
                    $scope.form.dataInicio = d;
                    var d2 = new Date(data.dataTermino);
                    $scope.form.dataTermino = d2;
                }).
                error(function (data) {
                    // log error
                });
    }

    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "cadastrarProjeto";

        if ($scope.form.dataInicio && $scope.form.dataTermino && ($scope.form.dataInicio > $scope.form.dataTermino)) {
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
    $scope.editarDadosBasicos = function () {
        $scope.mensagem = "";
        $scope.form.comando = "editarProjeto";

        if ($scope.form.dataInicio && $scope.form.dataTermino && ($scope.form.dataInicio > $scope.form.dataTermino)) {
            $scope.mensagem = "Data de início não pode ser maior que a de término";
            return;
        }
        if (!$scope.estaEmElaboracao($scope.status)) {
            $scope.mensagem = "Não é possível editar os dados de um projeto que não está em elaboração";
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
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
                        $window.location.href = 'projetover.html#/?id=' + idProjeto;
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

    $scope.todosOsDadosPreenchidos = function (form) {
        if (form.dataInicio !== "" && form.dataTermino !== "" && form.titulo !== "" && form.descricao !== ""
                && form.objetivo !== "" && form.agenciaFinanciadora !== "" && form.valorFinanciado !== "")
            return true;
        return false;
    };

    $scope.possuiProfessor = function (form) {
        for (var i = 0; i < form.participantes.length; i++)
            if (form.participantes[i].tipoColaborador === "PROFESSOR")
                return true;
        return false;
    };

    $scope.darAndamento = function () {
        $scope.desabilitaTudo();
        $scope.opcoesHabilitadas = false;
        if (!$scope.estaEmElaboracao($scope.status)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não está em elaboração";
            return;
        }
        if (!$scope.todosOsDadosPreenchidos($scope.form)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não possui todos os dados básicos preenchidos";
            return;
        }
        if (!$scope.possuiProfessor($scope.form)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não possui ao menos 1 professor como participante";
            return;
        }


        var resultado = confirm("Confirmar a operação de DAR ANDAMENTO ao projeto?");
        if (!resultado)
            $scope.desabilitaTudo();
        else {
            $scope.mensagem = "";
            $scope.form.comando = "darAndamentoProjeto";
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
                        if (status === 200) {
                            alert("Edição realizada com sucesso!");
                            $window.location.href = 'projetover.html#/?id=' + idProjeto;
                        }
                    })
                    .error(function (data, status) {
                        if (status === 401) {
                            $scope.mensagem = "Você não tem permissão para realizar esta ação (Precisa ser Gerente ou Administrador).";
                        }
                        else if (status === 500) {
                            $scope.mensagem = "Houve um problema ao reconhecer os dados digitados.";
                        }
                    });
        }
    };
}


