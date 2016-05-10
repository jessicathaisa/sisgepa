/* 
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
var app = angular.module('sisgepa', []);
app.controller('VerificaLoginController', VerificaLoginController);
app.controller('VerificaPermissaoController', VerificaPermissaoController);
app.controller('SendLoginController', SendLoginController);
app.controller('SendLogoutController', SendLogoutController);
app.controller('RelatorioController', RelatorioController);
app.controller('AlunoController', AlunoController);
app.controller('ProfessorController', ProfessorController);


function VerificaLoginController($scope, $http, $window) {
    // calling our submit function.
    $scope.verificaLogin = function () {
        var dados = {};
        dados.comando = "verificaLogado";
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
        var dados = {};
        dados.comando = "verificaLogado";
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

function VerificaPermissaoController($scope, $http, $window) {
    $scope.verificaEhAdministrador = function () {
        var dados = {};
        dados.comando = "verificaAdministrador";
        req = {
            method: 'POST',
            url: 'UsuarioServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: dados
        };
        $http(req).error(function (data, status) {
            alert("Você não tem permissão para realizar esta ação. Será redirecionado para a página inicial.");
            $window.location.href = 'index.html';
        });
    }
}

function SendLoginController($scope, $http, $window) {
    this.mensagem = "";
    $scope.form = {};
    // calling our submit function.
    $scope.submitForm = function () {
        $scope.mensagem = "";
        $scope.form.comando = "realizarLogin";
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



function AlunoController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idAluno = $location.search().id;
    var listar = $location.absUrl();

    this.professores = {};
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

                    $scope.form.projetos = data.projetos;
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


/**************************************************/


function ProfessorController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idProfessor = $location.search().id;
    var listar = $location.absUrl();
    this.professores = {};
    this.chamada = {};

    this.chamada.comando = "listarProfessores";
    $http.post('ProfessorServlet', this.chamada).
            success(function (data) {
                $scope.professores = data;
            }).
            error(function (data) {
                // log error
            });

    $scope.form = {};
    /*
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
     */
    if (listar.indexOf("professorver.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarProfessor";
        this.chamada.identificador = idProfessor;
        this.orientador = "";
        // Buscar pelo id
        $http.post('ProfessorServlet', this.chamada).
                success(function (data) {
                    $scope.form.identificador = data.identificador;
                    $scope.form.nome = data.nome;
                    $scope.form.email = data.email;
                    $scope.form.tipoUsuario = data.usuario.tipo;
                    $scope.form.senha = data.usuario.senha;
                    $scope.form.usuario = data.usuario.login;

                    $scope.form.projetos = data.projetos;
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

    $scope.submitFormCadastrar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "cadastrarProfessor";
        req = {
            method: 'POST',
            url: 'ProfessorServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        $window.location.href = 'professorlistar.html';
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
    /*
     $scope.submitFormEditar = function () {
     $scope.mensagem = "";
     $scope.form.comando = "editarAluno";
     $scope.form.orientador = $scope.form.orientador.identificador;
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
     */
}