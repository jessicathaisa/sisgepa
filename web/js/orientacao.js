/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */

app.controller('OrientacaoController', OrientacaoController);

function OrientacaoController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idOrientacao = $location.search().id;
    var listar = $location.absUrl();
    var orientacoes = {}
    this.chamada = {};

    this.chamada.comando = "listarOrientacoes";
    $http.post('OrientacaoServlet', this.chamada).
            success(function (data) {
                $scope.orientacoes = data;
            }).
            error(function (data) {
                // log error
            });

    if (listar.indexOf("orientacaolistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarOrientacoes";
        $http.post('OrientacaoServlet', this.chamada).
                success(function (data) {
                    $scope.orientacoes = data;
                }).
                error(function (data) {
                    // log error
                });
    }


    $scope.form = {};
    this.alunoSelecionado = {};
    this.professorSelecionado = {};


    if (listar.indexOf("orientacaover.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarOrientacao";
        this.chamada.identificador = idOrientacao;
        this.orientador = "";
        // Buscar pelo id
        $http.post('OrientacaoServlet', this.chamada).
                success(function (data) {
                    $scope.form = data;
                    $scope.form.tipoProjeto = data.aluno.tipoProjeto;

                    if ($scope.form.tipoProjeto === "TRABALHO_CONCLUSAO_CURSO")
                        $scope.form.tipoProjeto = "Trabalho de Conclusão de Curso";
                    else if ($scope.form.tipoProjeto === "DISSERTACAO")
                        $scope.form.tipoProjeto = "Dissertação";
                    else
                        $scope.form.tipoProjeto = "Tese";
                }).
                error(function (data) {
                    // log error
                });
    }

    if (listar.indexOf("orientacaocadastrar.html") >= 0) {
        $scope.professores = {};
        this.chamada = {};
        this.chamada.comando = "listarProfessores";
        $http.post('ProfessorServlet', this.chamada).
                success(function (data) {
                    $scope.professores = data;
                }).
                error(function (data) {
                    // log error
                });

        $scope.alunos = {};
        this.chamada = {};
        this.chamada.comando = "listarAlunos";
        $http.post('AlunoServlet', this.chamada).
                success(function (data) {
                    $scope.alunos = data;
                }).
                error(function (data) {
                    // log error
                });
    }

    if (listar.indexOf("orientacaoeditar.html") >= 0) {

        $scope.professores = {};
        this.chamada = {};
        this.chamada.comando = "listarProfessores";
        $http.post('ProfessorServlet', this.chamada).
                success(function (data) {
                    console.log(1);
                    $scope.professores = data;
                    listaAlunos();
                }).
                error(function (data) {
                    // log error
                });

        var listaAlunos = function () {
            $scope.alunos = {};
            this.chamada = {};
            this.chamada.comando = "listarAlunos";
            $http.post('AlunoServlet', this.chamada).
                    success(function (data) {
                        console.log(2);
                        $scope.alunos = data;
                        buscaOrientacoes();
                    }).
                    error(function (data) {
                        // log error
                    });
        };
        var buscaOrientacoes = function () {
            this.chamada = {};
            this.chamada.comando = "buscarOrientacao";
            this.chamada.identificador = idOrientacao;
            // Buscar pelo id
            $http.post('OrientacaoServlet', this.chamada).
                    success(function (data) {
                        console.log(3);
                        $scope.form = data;

                        for (var i = 0; i < $scope.professores.length; i++) {
                            $scope.professores[i].selecionado = $scope.professores[i].identificador === data.professor.identificador;
                        }
                        for (var i = 0; i < $scope.alunos.length; i++) {
                            $scope.alunos[i].selecionado = $scope.alunos[i].identificador === data.aluno.identificador;
                        }
                    }).
                    error(function (data) {
                        // log error
                    });
        };
    }


    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";

        if (!$scope.form.aluno) {
            $scope.mensagem = "Campo Aluno é obrigatório!";
            return;
        }
        if (!$scope.form.aluno) {
            $scope.mensagem = "Campo Professor é obrigatório!";
            return;
        }
        $scope.form.comando = "cadastrarOrientacao";
        req = {
            method: 'POST',
            url: 'OrientacaoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        $window.location.href = 'orientacaolistar.html';
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
                        $scope.mensagem = "Já existe uma orientação este aluno e este professor.";
                    }
                });
    };


    $scope.submitFormEditar = function () {
        $scope.mensagem = "";

        if (!$scope.form.aluno) {
            $scope.mensagem = "Campo Aluno é obrigatório!";
            return;
        }
        if (!$scope.form.professor) {
            $scope.mensagem = "Campo Professor é obrigatório!";
            return;
        }
        if (!$scope.form.ano) {
            $scope.mensagem = "Campo Ano é obrigatório!";
            return;
        }
        if (!$scope.form.titulo) {
            $scope.mensagem = "Campo Título é obrigatório!";
            return;
        }

        $scope.form.comando = "editarOrientacao";
        $scope.form.aluno = ($scope.form.aluno.identificador) ? $scope.form.aluno.identificador + "" : $scope.form.aluno + "";
        $scope.form.professor = ($scope.form.professor.identificador) ? $scope.form.professor.identificador + "" : $scope.form.professor + "";
        console.log($scope.form);
        req = {
            method: 'POST',
            url: 'OrientacaoServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
                        $window.location.href = 'orientacaolistar.html';
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
                        $scope.mensagem = "Já existe uma orientação este aluno e este professor.";
                    }
                });
    };

    $scope.submitFormExcluir = function () {
        var resposta = confirm("Confirmar exclusão da orientação?");
        if (resposta == true) {
            $scope.mensagem = "";
            this.chamada = {};
            this.chamada.comando = "excluirOrientacao";
            this.chamada.identificador = idOrientacao;
            req = {
                method: 'POST',
                url: 'OrientacaoServlet',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.chamada
            };

            $http(req)
                    .success(function (data, status) {
                        if (status === 200) {
                            alert("Exclusão realizada com sucesso!");
                            $window.location.href = 'orientacaolistar.html';
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