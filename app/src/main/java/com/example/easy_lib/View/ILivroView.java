package com.example.easy_lib.View;

import com.google.firebase.database.Query;

public interface ILivroView {
    void exibirLivros(Query query);
    void exibirLivro(Query query);

    void showMensagem(String mensagem);
    void limparCampos();
}
