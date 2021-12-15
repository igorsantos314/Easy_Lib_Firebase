package com.example.easy_lib.Model;

import java.util.List;

public interface IUsuarioDAO {

    static final int VERSAO_BANCO = 1;
    static final String NOME_BANCO = "db_easy_lib";

    static final String TABELA_USUARIO = "tb_usuario";

    static final String COLUNA_CPF = "cpf";
    static final String COLUNA_NOME = "nome";
    static final String COLUNA_DATA_NASCIMENTO = "data_nascimento";
    static final String COLUNA_TELEFONE = "telefone";
    static final String COLUNA_CIDADE = "cidade";
    static final String COLUNA_BAIRRO = "bairro";
    static final String COLUNA_RUA = "rua";
    static final String COLUNA_NUMERO_COMPLETO = "numero_completo";
    static final String COLUNA_LOGIN = "login";
    static final String COLUNA_SENHA = "senha";
    static final String COLUNA_CARGO = "cargo";

    static final String QUERY_QUANTIDADE_CLIENTES = "SELECT count(" + COLUNA_CPF + ") FROM " + TABELA_USUARIO + ";";

    void insertUsuario (Usuario cpf);
    void deleteUsuario (String cpf);
    void updateUsuario (Usuario usuario);
    Usuario getUsuario (String cpf);
    int validateLogin (String login, String senha);
    List<Usuario> getUsuarioPorNome (String nome);

    boolean existCliente(String cpf);
    public boolean existLogin(String login);
}

