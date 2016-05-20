/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */

app.controller('PublicacaoController', PublicacaoController);

function PublicacaoController($scope, $http, $window, $location, $q) {
    this.mensagem = "";
    var idPublicacao = $location.search().id;
    var listar = $location.absUrl();
    $scope.alunos = {};
    $scope.professores = {};
    $scope.pesquisadores = {};
    $scope.projetos = {};
    $scope.autores = [];
    $scope.autoresprof = [];
    $scope.autorespesq = [];
    this.chamada = {};

    if (listar.indexOf("publicacaolistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarPublicacoes";
        $http.post('PublicacaoServlet', this.chamada).
                success(function (data) {
                    $scope.publicacoes = data;

                    for (var i = 0; i < $scope.publicacoes.length; i++) {
                        var publicacao = $scope.publicacoes[i];

                        if (publicacao.projeto)
                            publicacao.projeto = publicacao.projeto.titulo;

                        var participantes = "";
                        for (var j = 0; j < publicacao.autores.length; j++) {
                            participantes += publicacao.autores[j].nome;
                            if (j !== publicacao.autores.length - 1)
                                participantes += ", ";
                        }

                        publicacao.nomes = participantes;
                    }
                }).
                error(function (data) {
                    // log error
                });
    }

    var carregarCheckboxes = function () {

        if (listar.indexOf("publicacaocadastrar.html") >= 0 || listar.indexOf("publicacaoeditar.html") >= 0) {
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
        }
        return $q.when(true);
    }


    $scope.exibeAutores = false;
    $scope.aut = [];
    $scope.autp = [];
    $scope.autpe = [];
    $scope.form = {};
    $scope.exibeColaboradores = function () {
        if ($scope.exibeAutores) {
            $scope.aut = [];
            for (var j = 0; j < $scope.autores.length; j++) {
                if ($scope.autores[j].selected) {
                    $scope.aut.push($scope.alunos[j].nome);
                }
            }
            $scope.autp = [];
            for (var j = 0; j < $scope.autoresprof.length; j++) {
                if ($scope.autoresprof[j].selected) {
                    $scope.autp.push($scope.professores[j].nome);
                }
            }
            $scope.autpe = [];
            for (var j = 0; j < $scope.autorespesq.length; j++) {
                if ($scope.autorespesq[j].selected) {
                    $scope.autpe.push($scope.pesquisadores[j].nome);
                }
            }
        } else {
            if (listar.indexOf("publicacaocadastrar.html") >= 0) {
                $scope.aut = [];
                $scope.autp = [];
                $scope.autpe = [];
            }
        }


        $scope.exibeAutores = !$scope.exibeAutores;
    };

    $scope.exibeSelecaoProjetos = false;
    $scope.projetoSelecionado = {};
    $scope.exibeProjetos = function () {
        if ($scope.exibeSelecaoProjetos) {
            $scope.projetoSelecionado = {};

            for (var j = 0; j < $scope.projetos.length; j++) {
                if ($scope.projetos[j].identificador == $scope.identificadorProjetoSelecionado) {
                    $scope.projetoSelecionado = $scope.projetos[j];
                    break;
                }
            }
            if ($scope.identificadorProjetoSelecionado == -1)
                $scope.projetoSelecionado = {};
        } else {
            if (listar.indexOf("publicacaocadastrar.html") >= 0) {
                $scope.identificadorProjetoSelecionado = $scope.projetoSelecionado.identificador;
                $scope.projetoSelecionado = {};
            }
        }
        $scope.exibeSelecaoProjetos = !$scope.exibeSelecaoProjetos;
    };

    if (listar.indexOf("publicacaover.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarPublicacao";
        this.chamada.identificador = idPublicacao;
        this.orientador = "";
        // Buscar pelo id
        $http.post('PublicacaoServlet', this.chamada).
                success(function (data) {
                    $scope.form = data;
                }).
                error(function (data) {
                    // log error
                });
    }

    var buscaPublicacao = function () {
        if (listar.indexOf("publicacaoeditar.html") >= 0) {
            this.chamada = {};
            this.chamada.comando = "buscarPublicacao";
            this.chamada.identificador = idPublicacao;
            this.orientador = "";
            // Buscar pelo id
            return $http.post('PublicacaoServlet', this.chamada).
                    success(function (data) {
                        $scope.form = data;
                        if (data.projeto && data.projeto.status === "CONCLUIDO") {
                            alert("O Projeto desta Publicação está Concluído. Por isso ela não pode ser editada.");
                            $window.location.href = 'publicacaover.html#/?id=' + data.identificador;
                        }
                        function existeNaLista(identificador) {
                            var existe = $scope.form.autores.filter(function (value) {
                                return value.identificador === identificador;
                            });
                            return existe && existe.length > 0;
                        }

                        $scope.autores.map(function (value) {
                            value.selected = existeNaLista(value.identificador);
                        });

                        $scope.autoresprof.map(function (value) {
                            value.selected = existeNaLista(value.identificador);
                        });

                        $scope.autorespesq.map(function (value) {
                            value.selected = existeNaLista(value.identificador);
                        });
                        if ($scope.form.projeto)
                            $scope.projetos.filter(function (value) {
                                if (value.identificador === $scope.form.projeto.identificador)
                                    value.selected = value.identificador + "";
                            });
                        else
                            $scope.identificadorProjetoSelecionado = -1;

                        $scope.aut = [];
                        for (var j = 0; j < $scope.autores.length; j++) {
                            if ($scope.autores[j].selected) {
                                $scope.aut.push($scope.alunos[j].nome);
                            }
                        }
                        $scope.autp = [];
                        for (var j = 0; j < $scope.autoresprof.length; j++) {
                            if ($scope.autoresprof[j].selected) {
                                $scope.autp.push($scope.professores[j].nome);
                            }
                        }
                        $scope.autpe = [];
                        for (var j = 0; j < $scope.autorespesq.length; j++) {
                            if ($scope.autorespesq[j].selected) {
                                $scope.autpe.push($scope.pesquisadores[j].nome);
                            }
                        }

                        $scope.projetoSelecionado = {};
                        for (var j = 0; j < $scope.projetos.length; j++) {
                            if ($scope.projetos[j].identificador == $scope.form.projeto.identificador) {
                                $scope.projetoSelecionado = $scope.projetos[j];
                                identificadorProjetoSelecionado = $scope.projetoSelecionado.identificador;
                                break;
                            }
                        }
                    }).
                    error(function (data) {
                        // log error
                    });
        }

        return $q.when(true);
    }

    carregarCheckboxes().then(buscaPublicacao);

    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";

        if (!$scope.form.titulo) {
            $scope.mensagem = "Campo Título é obrigatório!";
            return;
        }
        if (!$scope.form.conferencia) {
            $scope.mensagem = "Campo Conferencia é obrigatório!";
            return;
        }
        if (!$scope.form.ano) {
            $scope.mensagem = "Campo Ano é obrigatório!";
            return;
        }
        if ($scope.aut.length === 0 && $scope.autp.length === 0 && $scope.autpe.length === 0) {
            $scope.mensagem = "É obrigatório que a publicação tenha ao menos um autor.";
            return;
        }
        $scope.form.comando = "cadastrarPublicacao";
        var autores = "";
        for (var j = 0; j < $scope.autorespesq.length; j++) {
            if ($scope.autorespesq[j].selected) {
                autores += $scope.autorespesq[j].identificador + " ";
            }
        }
        for (var j = 0; j < $scope.autoresprof.length; j++) {
            if ($scope.autoresprof[j].selected) {
                autores += $scope.autoresprof[j].identificador + " ";
            }
        }
        for (var j = 0; j < $scope.autores.length; j++) {
            if ($scope.autores[j].selected) {
                autores += $scope.autores[j].identificador + " ";
            }
        }
        $scope.form.autores = autores.trim();
        $scope.form.projeto = $scope.projetoSelecionado.identificador || -1;
        console.log($scope.form.projeto);
        req = {
            method: 'POST',
            url: 'PublicacaoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        $window.location.href = 'publicacaolistar.html';
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


    $scope.submitFormEditar = function () {
        $scope.mensagem = "";

        if (!$scope.form.titulo) {
            $scope.mensagem = "Campo Título é obrigatório!";
            return;
        }
        if (!$scope.form.conferencia) {
            $scope.mensagem = "Campo Conferencia é obrigatório!";
            return;
        }
        if (!$scope.form.ano) {
            $scope.mensagem = "Campo Ano é obrigatório!";
            return;
        }
        if ($scope.aut.length === 0 && $scope.autp.length === 0 && $scope.autpe.length === 0) {
            $scope.mensagem = "É obrigatório que a publicação tenha ao menos um autor.";
            return;
        }
        $scope.form.comando = "editarPublicacao";
        var autores = "";
        for (var j = 0; j < $scope.autorespesq.length; j++) {
            if ($scope.autorespesq[j].selected) {
                autores += $scope.autorespesq[j].identificador + " ";
            }
        }
        for (var j = 0; j < $scope.autoresprof.length; j++) {
            if ($scope.autoresprof[j].selected) {
                autores += $scope.autoresprof[j].identificador + " ";
            }
        }
        for (var j = 0; j < $scope.autores.length; j++) {
            if ($scope.autores[j].selected) {
                autores += $scope.autores[j].identificador + " ";
            }
        }
        $scope.form.autores = autores.trim();
        $scope.form.projeto = $scope.projetoSelecionado.identificador || -1;
        console.log($scope.form.projeto);
        req = {
            method: 'POST',
            url: 'PublicacaoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
                        $window.location.href = 'publicacaolistar.html';
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


    $scope.submitFormExcluir = function () {
        var resposta = confirm("Confirmar exclusão da publicação?");
        if (resposta == true) {
            $scope.mensagem = "";
            this.chamada = {};
            this.chamada.comando = "excluirPublicacao";
            this.chamada.identificador = idPublicacao;
            req = {
                method: 'POST',
                url: 'PublicacaoServlet',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.chamada
            };

            $http(req)
                    .success(function (data, status) {
                        if (status === 200) {
                            alert("Exclusão realizada com sucesso!");
                            $window.location.href = 'publicacaolistar.html';
                        }
                    })
                    .error(function (data, status) {
                        if (status === 401) {
                            $scope.mensagem = "Você não tem permissão para realizar esta ação.";
                        }
                        else if (status === 500) {
                            $scope.mensagem = "Não foi possível processar a operação, favor tente mais tarde.";
                        }
                    });
        }
    };
}


