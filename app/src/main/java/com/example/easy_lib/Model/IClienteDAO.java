package com.example.easy_lib.Model;

import java.util.List;

public interface IClienteDAO {


    static final String NOME_BANCO = "db_easy_lib";
    static final int VERSAO_BANCO = 1;

    static final String TABELA_CLIENTE = "tb_cliente";
    static final String COLUNA_CPF = "cpf";
    static final String COLUNA_NOME = "nome";
    static final String COLUNA_DATA_NASCIMENTO = "data_nascimento";
    static final String COLUNA_TELEFONE = "telefone";
    static final String COLUNA_CIDADE = "cidade";
    static final String COLUNA_BAIRRO = "bairro";
    static final String COLUNA_RUA = "rua";
    static final String COLUNA_NUMERO_COMPLEMENTO = "numero_complemento";

    static final String QUERY_QUANTIDADE_CLIENTES = "SELECT count(" + COLUNA_CPF + ") FROM " + TABELA_CLIENTE + ";";
    static final String QUERY_TODOS_CLIENTES = "SELECT * FROM " + TABELA_CLIENTE + ";";

    void insertCliente (Cliente cliente);
    void updateCliente (Cliente cliente);
    void deleteCliente (String cpf);

    List<Cliente> getTodosClientes();
    Cliente getCliente(String cpf);
    List<Cliente> getClientePorNome (String nome);
    int getQuantidadeClientes();

}
