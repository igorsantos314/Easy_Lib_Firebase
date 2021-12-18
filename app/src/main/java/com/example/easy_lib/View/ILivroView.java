package com.example.easy_lib.View;

import com.example.easy_lib.Model.Livro;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public interface ILivroView {
    void adicionarLivro(Livro livro);
    void exibirLivros(ArrayList<Livro> livros);
    void exibirLivro(Query query);

    void errorLivroExist();
    void showMensagem(String mensagem);
    void limparCampos();
}
