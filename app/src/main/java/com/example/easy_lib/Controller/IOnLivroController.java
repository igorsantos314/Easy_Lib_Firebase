package com.example.easy_lib.Controller;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.Livro;

public interface IOnLivroController {
    void insertLivro (String codigo, String prateleira, String ano, String nome, String estante, String autor, String editora, String quantidade, String genero);
    void updateLivro (String codigo, String prateleira, String ano, String nome, String estante, String autor, String editora, String quantidade, String genero);
    void deleteLivro (String codigo);
    void updateQuantidadeLivro(int codigo, int quantidade_atual, int retirada);

    void onExistLivro(String codigo);
    void onExibirTodosLivros();
    void onExibirLivro(String codigo);
    void onExibirLivrosPorNome (String nome);
    void onExibirQuantidadeLivros();

    void onExibirLivroConsulta(String codigo);

    boolean onValidarCampos(Livro livro);
}
