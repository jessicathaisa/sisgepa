/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */

app.controller('ProjetoController', ProjetoController);

function ProjetoController($scope, $http, $window, $location, $q, $anchorScroll) {
    this.mensagem = "";
    var idProjeto = $location.search().id;
    var listar = $location.absUrl();
    $scope.projetos = {};
    this.chamada = {};
    $scope.status = {};
    $scope.dadosBasicosHabilitados = false;
    $scope.opcoesHabilitadas = true;
    $scope.autores = [];
    $scope.autoresprof = [];
    $scope.autorespesq = [];

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

    $scope.habilitaTodosBotoes = function () {
        $scope.dadosBasicosHabilitados = false;
        $scope.participantesHabilitados = false;
        $scope.opcoesHabilitadas = true;
    };

    $scope.iniciarEdicaoDadosBasicos = function () {
        $scope.habilitaTodosBotoes();

        $scope.opcoesHabilitadas = false;
        $scope.dadosBasicosHabilitados = true;
        $scope.mensagem = "";
    };


    $scope.iniciarEdicaoParticipantes = function () {
        $scope.participantesHabilitados = true;
        $location.hash("participantes");
        $anchorScroll();
        
        this.chamada = {};
        this.chamada.comando = "listarAlunos";
        var alunoPromise = $http.post('AlunoServlet', this.chamada).
                success(function (data) {
                    $scope.alunos = data;
                    for (var i = 0; i < $scope.alunos.length; i++) {
                        var aluno = $scope.alunos[i];
                        $scope.autores[i] = {};
                        $scope.autores[i].identificador = aluno.identificador;
                        $scope.autores[i].selected = false;
                        var di = new Date(aluno.dataIngresso);
                        aluno.dataIngresso = di.getTime();
                    }
                }).
                error(function (data) {
                    // log error
                });

        this.chamada = {};
        this.chamada.comando = "listarPesquisadores";
        var pesquisadorPromise = $http.post('PesquisadorServlet', this.chamada).
                success(function (data) {
                    $scope.pesquisadores = data;
                    for (var i = 0; i < $scope.pesquisadores.length; i++) {
                        var pesq = $scope.pesquisadores[i];
                        $scope.autorespesq[i] = {};
                        $scope.autorespesq[i].identificador = pesq.identificador;
                        $scope.autorespesq[i].selected = false;
                    }
                }).
                error(function (data) {
                    // log error
                });

        this.chamada = {};
        this.chamada.comando = "listarProfessores";
        var professorPromise = $http.post('ProfessorServlet', this.chamada).
                success(function (data) {
                    $scope.professores = data;
                    for (var i = 0; i < $scope.professores.length; i++) {
                        var prof = $scope.professores[i];
                        $scope.autoresprof[i] = {};
                        $scope.autoresprof[i].identificador = prof.identificador;
                        $scope.autoresprof[i].selected = false;
                    }
                }).
                error(function (data) {
                    // log error
                });

        this.chamada = {};
        this.chamada.comando = "listarProjetosEmAndamento";
        var projetoPromise = $http.post('ProjetoServlet', this.chamada).
                success(function (data) {
                    $scope.projetos = data;
                    for (var i = 0; i < $scope.projetos.length; i++) {
                        $scope.projetos[i].selected = false;
                    }
                }).
                error(function (data) {
                    // log error
                });

        return $q.all([projetoPromise, professorPromise, pesquisadorPromise, alunoPromise]);
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
    $scope.possuiPublicacoes = function (form) {
        return form.publicacoes && form.publicacoes.length > 0;
    };

    $scope.darAndamento = function () {
        $scope.habilitaTodosBotoes();
        $scope.opcoesHabilitadas = false;
        if (!$scope.estaEmElaboracao($scope.status)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não está em elaboração";
            $scope.habilitaTodosBotoes();
            return;
        }
        if (!$scope.todosOsDadosPreenchidos($scope.form)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não possui todos os dados básicos preenchidos";
            $scope.habilitaTodosBotoes();
            return;
        }
        if (!$scope.possuiProfessor($scope.form)) {
            $scope.mensagem = "Não é possível dar andamento a um projeto que não possui ao menos 1 professor como participante";
            $scope.habilitaTodosBotoes();
            return;
        }


        var resultado = confirm("Confirmar a operação de DAR ANDAMENTO ao projeto?");
        if (!resultado)
            $scope.habilitaTodosBotoes();
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
                        $scope.habilitaTodosBotoes();
                    });
            $scope.habilitaTodosBotoes();
        }
    };
    $scope.concluir = function () {
        $scope.habilitaTodosBotoes();
        $scope.opcoesHabilitadas = false;
        if (!$scope.estaEmAndamento($scope.status)) {
            $scope.mensagem = "Não é possível concluir a um projeto que não está em andamento";
            $scope.habilitaTodosBotoes();
            return;
        }
        if (!$scope.possuiPublicacoes($scope.form)) {
            $scope.mensagem = "Não é possível concluir um projeto sem publicações";
            $scope.habilitaTodosBotoes();
            return;
        }

        var resultado = confirm("Confirmar a operação de CONCLUIR ao projeto?");
        if (!resultado)
            $scope.habilitaTodosBotoes();
        else {
            $scope.mensagem = "";
            $scope.form.comando = "concluirProjeto";
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
                            $scope.mensagem = "Houve um problema no servidor. Tente mais tarde.";
                        }
                        $scope.habilitaTodosBotoes();
                    });
            $scope.habilitaTodosBotoes();
        }
    };
}


