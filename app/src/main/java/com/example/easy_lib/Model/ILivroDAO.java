package com.example.easy_lib.Model;

import java.util.List;

public interface ILivroDAO {

    //Configuração da base de dados
     static final String NOME_BANCO = "db_easy_lib";
     static final int VERSAO_BANCO = 1;

     static final String TABELA_LIVRO = "tb_livro";
     static final String COLUNA_CODIGO= "codigo";
     static final String COLUNA_PRATELEIRA = "prateleira";
     static final String COLUNA_ESTANTE = "estante";
     static final String COLUNA_ANO = "ano";
     static final String COLUNA_NOME = "nome";
     static final String COLUNA_AUTOR = "autor";
     static final String COLUNA_EDITORA = "editora";
     static final String COLUNA_QUANTIDADE = "quantidade";
     static final String COLUNA_GENERO = "genero";


     static final String QUERY_TOTAL_LIVROS = "SELECT count(" + COLUNA_CODIGO + ") FROM " + TABELA_LIVRO + ";";
     static final String QUERY_TODOS_LIVROS = "SELECT * FROM " + TABELA_LIVRO + ";";

    void insertLivro (Livro livro);
    void deleteLivro (int codigo);
    void updateLivro (Livro livro);


    Livro getLivro (int codigo);
    List<Livro> getLivroNome (String nome);
    List<Livro> getTodosLivros();
    int getQuantidadeLivros();




}
