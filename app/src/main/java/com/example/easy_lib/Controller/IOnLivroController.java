package com.example.easy_lib.Controller;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.Livro;

public interface IOnLivroController {
    void insertLivro (int codigo, String prateleira, int ano, String nome, String estante, String autor, String editora, int quantidade, String genero);
    void updateLivro (int codigo, String prateleira, int ano, String nome, String estante, String autor, String editora, int quantidade, String genero);
    void deleteLivro (int codigo);
    void updateQuantidadeLivro(int codigo, int quantidade_atual, int retirada);

    void onExibirTodosLivros();
    void onExibirLivro(int codigo);
    void onExibirLivrosPorNome (String nome);
    void onExibirQuantidadeLivros();

    boolean onValidarCampos(Livro livro);
}
