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
app.controller('PesquisadorController', PesquisadorController);
app.controller('OrientacaoController', OrientacaoController);
app.controller('PublicacaoController', PublicacaoController);


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


/**************************************************/


function ProfessorController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idProfessor = $location.search().id;
    var listar = $location.absUrl();
    $scope.professores = {}
    this.chamada = {};

    this.chamada.comando = "listarProfessores";
    $http.post('ProfessorServlet', this.chamada).
            success(function (data) {
                $scope.professores = data;
            }).
            error(function (data) {
                // log error
            });

    if (listar.indexOf("professorlistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarProfessores";
        $http.post('ProfessorServlet', this.chamada).
                success(function (data) {
                    $scope.professores = data;
                }).
                error(function (data) {
                    // log error
                });
    }


    $scope.form = {};

    if (listar.indexOf("professoreditar.html") >= 0) {
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
                }).
                error(function (data) {
                    // log error
                });
    }

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

    $scope.submitFormEditar = function () {
        $scope.mensagem = "";
        $scope.form.comando = "editarProfessor";
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
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
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

    $scope.submitFormExcluir = function () {
        var resposta = confirm("Confirmar exclusão do colaborador?");
        if (resposta == true) {
            $scope.mensagem = "";
            this.chamada = {};
            this.chamada.comando = "excluirProfessor";
            this.chamada.identificador = idProfessor;
            req = {
                method: 'POST',
                url: 'ProfessorServlet',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.chamada
            };

            $http(req)
                    .success(function (data, status) {
                        if (status === 200) {
                            alert("Exclusão realizada com sucesso!");
                            $window.location.href = 'professorlistar.html';
                        }
                    })
                    .error(function (data, status) {
                        if (status === 401) {
                            $scope.mensagem = "Você não tem permissão para realizar esta ação.";
                        }
                        else if (status === 500) {
                            $scope.mensagem = "Não foi possível processar a operação, favor tente mais tarde.";
                        }
                        else if (status === 409) {
                            $scope.mensagem = "Este professor possui orientandos. Para excluí-lo mude o orientador dos seus alunos.";
                        }
                    });
        }
    };

}


/**************************************************/


function PesquisadorController($scope, $http, $window, $location) {
    this.mensagem = "";
    var idPesquisador = $location.search().id;
    var listar = $location.absUrl();
    var pesquisadores = {}
    this.chamada = {};

    this.chamada.comando = "listarPesquisadores";
    $http.post('PesquisadorServlet', this.chamada).
            success(function (data) {
                $scope.pesquisadores = data;
            }).
            error(function (data) {
                // log error
            });

    if (listar.indexOf("pesquisadorlistar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "listarPesquisadores";
        $http.post('PesquisadorServlet', this.chamada).
                success(function (data) {
                    $scope.pesquisadores = data;
                }).
                error(function (data) {
                    // log error
                });
    }


    $scope.form = {};

    if (listar.indexOf("pesquisadoreditar.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarPesquisador";
        this.chamada.identificador = idPesquisador;
        this.orientador = "";
        // Buscar pelo id
        $http.post('PesquisadorServlet', this.chamada).
                success(function (data) {
                    $scope.form.identificador = data.identificador;
                    $scope.form.nome = data.nome;
                    $scope.form.email = data.email;
                    $scope.form.tipoUsuario = data.usuario.tipo;
                    $scope.form.senha = data.usuario.senha;
                    $scope.form.usuario = data.usuario.login;
                }).
                error(function (data) {
                    // log error
                });
    }

    if (listar.indexOf("pesquisadorver.html") >= 0) {
        this.chamada = {};
        this.chamada.comando = "buscarPesquisador";
        this.chamada.identificador = idPesquisador;
        this.orientador = "";
        // Buscar pelo id
        $http.post('PesquisadorServlet', this.chamada).
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
        $scope.form.comando = "cadastrarPesquisador";
        req = {
            method: 'POST',
            url: 'PesquisadorServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        $window.location.href = 'pesquisadorlistar.html';
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
        $scope.form.comando = "editarPesquisador";
        req = {
            method: 'POST',
            url: 'PesquisadorServlet',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.form
        };

        $http(req)
                .success(function (data, status) {
                    if (status === 200) {
                        alert("Edição realizada com sucesso!");
                        $window.location.href = 'pesquisadorlistar.html';
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
            this.chamada.comando = "excluirPesquisador";
            this.chamada.identificador = idPesquisador;
            req = {
                method: 'POST',
                url: 'PesquisadorServlet',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.chamada
            };

            $http(req)
                    .success(function (data, status) {
                        if (status === 200) {
                            alert("Exclusão realizada com sucesso!");
                            $window.location.href = 'pesquisadorlistar.html';
                        }
                    })
                    .error(function (data, status) {
                        if (status === 401) {
                            $scope.mensagem = "Você não tem permissão para realizar esta ação.";
                        }
                        else if (status === 500) {
                            $scope.mensagem = "Não foi possível processar a operação, favor tente mais tarde.";
                        }
                        else if (status === 409) {
                            $scope.mensagem = "Este pesquisador possui orientandos. Para excluí-lo mude o orientador dos seus alunos.";
                        }
                    });
        }
    };

}


/**************************************************/


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


/**************************************************/


function PublicacaoController($scope, $http, $window, $location, $q) {
    this.mensagem = "";
    var idPublicacao = $location.search().id;
    var listar = $location.absUrl();
    var publicacoes = {};
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
                        if (data.projeto.status === "CONCLUIDO") {
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