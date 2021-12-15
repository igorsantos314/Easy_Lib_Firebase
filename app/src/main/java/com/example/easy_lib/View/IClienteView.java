package com.example.easy_lib.View;

import com.google.firebase.database.Query;

public interface IClienteView {

    String MSG_CLIENTE_EXIST = "CLIENTE JÁ CADASTRADO";
    String MSG_CPF_INVALIDO = "CPF INVÁLIDO";

    void exibirClientes(Query query);
    void exibirCliente(Query query);

    void errorClienteExist();
    void showMensagem(String mensagem);
    void limparCampos();
}
