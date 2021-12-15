package com.example.easy_lib.Model;

import java.util.List;

public interface IEmprestimoDAO {

    static final String NOME_BANCO = "db_easy_lib";
    static final int VERSAO_BANCO = 1;

    static final String TABELA_EMPRESTIMO = "tb_emprestimo";
    static final String COLUNA_CODIGO = "codigo";
    static final String COLUNA_DATA_EMPRESTIMO = "data_emprestimo";
    static final String COLUNA_DATA_DEVOLUCAO = "data_devolucao";
    static final String COLUNA_STATUS = "status";
    static final String COLUNA_CPF_USUARIO = "cpf_usuario";
    static final String COLUNA_CODIGO_LIVRO = "codigo_livro";


    static final String QUERY_QUANTIDADE_EMPRESTIMOS = "SELECT count(" + COLUNA_CODIGO + ") FROM " + TABELA_EMPRESTIMO + ";";
    static final String QUERY_TODOS_EMPRESTIMOS = "SELECT * FROM " + TABELA_EMPRESTIMO + ";";
    public static final String QUERY_EMPRESTIMOS_PENDENTES = "SELECT count(" + COLUNA_CODIGO + ") FROM " + TABELA_EMPRESTIMO + " WHERE " + COLUNA_STATUS +" = 'PENDENTE';";


    void insertEmprestimo(Emprestimo emprestimo);
    void deleteEmprestimo(int codigo);
    void updateEmprestimo(Emprestimo emprestimo);

    Emprestimo getEmprestimo(int codigo);
    List<Emprestimo> getEmprestimoCPF(String cpf);
    List<Emprestimo> getTodosEmprestimos();
    List<Emprestimo> getEmprestimosPendentes();

}
