/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
app.controller('AlunoController', AlunoController);

function AlunoController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idAluno = $location.search().id;
    var listar = $location.absUrl();

    $scope.professores = {};
    this.alunos = {};
    this.chamada = {};
    this.chamada.comando = "listarProfessores";
    $http.post('ProfessorServlet', this.chamada).
            success(function (data) {
                $scope.professores = data;
            }).
            error(function (data) {
                // log error
            });

    if (listar.indexOf("alunolistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarAlunos";
        $http.post('AlunoServlet', this.chamada).
                success(function (data) {
                    $scope.alunos = data;
                    for (var i = 0; i < $scope.alunos.length; i++) {
                        var aluno = $scope.alunos[i];

                        var di = new Date(aluno.dataIngresso);
                        aluno.dataIngresso = di.getTime();
                    }
                }).
                error(function (data) {
                    // log error
                });
    }

    $scope.form = {};

    if (listar.indexOf("alunoeditar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarAluno";
        this.chamada.identificador = idAluno;
        this.orientador = "";
        // Buscar pelo id
        $http.post('AlunoServlet', this.chamada).
                success(function (data) {
                    $scope.form.identificador = data.identificador;
                    $scope.form.nome = data.nome;
                    $scope.form.email = data.email;
                    $scope.form.tipo = data.tipoAluno;
                    $scope.form.regime = data.regimeCurso;
                    $scope.form.tipoUsuario = data.usuario.tipo;
                    $scope.form.senha = data.usuario.senha;
                    $scope.form.usuario = data.usuario.login;
                    $scope.form.orientador = $scope.orientador = data.orientador;
                    var d = new Date(data.dataIngresso);
                    $scope.form.dataIngresso = d;
                }).
                error(function (data) {
                    // log error
                });
    }

    if (listar.indexOf("alunover.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarAluno";
        this.chamada.identificador = idAluno;
        this.orientador = "";
        // Buscar pelo id
        $http.post('AlunoServlet', this.chamada).
                success(function (data) {
                    $scope.form.identificador = data.identificador;
                    $scope.form.nome = data.nome;
                    $scope.form.email = data.email;
                    $scope.form.tipo = data.tipoAluno;
                    $scope.form.regime = data.regimeCurso;
                    $scope.form.tipoUsuario = data.usuario.tipo;
                    $scope.form.senha = data.usuario.senha;
                    $scope.form.usuario = data.usuario.login;
                    $scope.form.orientador = $scope.orientador = data.orientador;
                    var d = new Date(data.dataIngresso);
                    $scope.form.dataIngresso = d.getTime();


                    this.chamada = {};
                    this.chamada.comando = "listarProjetosAluno";
                    this.chamada.identificador = idAluno;
                    // Buscar pelo id
                    $http.post('AlunoServlet', this.chamada).
                            success(function (data2) {
                                $scope.form.projetos = data2;
                                for (var i = 0; i < $scope.form.projetos.length; i++) {
                                    var proj = $scope.form.projetos[i];
                                    var di = new Date(proj.dataInicio);
                                    proj.dataInicio = di.getTime();
                                    var dt = new Date(proj.dataTermino);
                                    proj.dataTermino = dt.getTime();

                                    if (!proj.dataInicio)
                                        proj.dataInicio = "NÃO DEFINIDO";
                                    if (!proj.dataTermino)
                                        proj.dataTermino = "NÃO DEFINIDO";
                                }
                            }).
                            error(function (data2) {
                                // log error
                            });

                    this.chamada = {};
                    this.chamada.comando = "listarProducaoAluno";
                    this.chamada.identificador = idAluno;
                    // Buscar pelo id
                    $http.post('AlunoServlet', this.chamada).
                            success(function (data3) {
                                $scope.form.producoes = data3;
                            }).
                            error(function (data3) {
                                // log error
                            });
                }).
                error(function (data) {
                    // log error
                });
    }
    $scope.verCorProjeto = function (projeto) {
        var classe = "";
        if (projeto.status === "CONCLUIDO")
            classe = "panel-success";
        else if (projeto.status === "EM_ELABORACAO")
            classe = "panel-warning";
        else if (projeto.status === "EM_ANDAMENTO")
            classe = "panel-info";

        return "panel " + classe;
    };
    $scope.trazNomesAutores = function (producao) {
        var retorno = "";
        if(producao.conferencia){
            for(var i = 0; i < producao.autores.length ; i++){
                retorno += producao.autores[i].nome;
                if(i !== producao.autores.length - 1)
                    retorno += ", ";
            }
        }
        return retorno;
    };
    $scope.verCorProducao = function (producao) {
        var classe = "";
        if (producao.aluno)
            classe = "panel-success";
        else if (producao.conferencia)
            classe = "panel-info";

        return "panel " + classe;
    };
    $scope.verTipoProducao = function (producao) {
        if (producao.aluno)
            return "Orientação";
        else if (producao.conferencia)
            return "Publicação";
    };

    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "cadastrarAluno";
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
                    else if (status === 409) {
                        $scope.mensagem = "Já existe um usuário com este login, favor informar outro login.";
                    }
                });
    };

    $scope.submitFormEditar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "editarAluno";
        //$scope.form.orientador = $scope.orientador.identificador;
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
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
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
                    else if (status === 409) {
                        $scope.mensagem = "Já existe um usuário com este login, favor informar outro login.";
                    }
                });
    };

    $scope.submitFormExcluir = function () {
        var resposta = confirm("Confirmar exclusão do colaborador?");
        if (resposta == true) {
            $scope.mensagem = "";
            this.chamada = {};
            this.chamada.comando = "excluirAluno";
            this.chamada.identificador = idAluno;
            req = {
                method: 'POST',
                url: 'AlunoServlet',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.chamada
            };

            $http(req)
                    .success(function (data, status) {
                        if (status === 200) {
                            alert("Exclusão realizada com sucesso!");
                            $window.location.href = 'alunolistar.html';
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
