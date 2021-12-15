package com.example.easy_lib.View;

import com.example.easy_lib.Model.Cliente;
import com.google.firebase.database.Query;

public interface IEmprestimoView {
    void exibirEmprestimos(Query query);
    void exibirEmprestimo(Query query);

    void exibirConsultaClienteEmprestimo(Cliente cliente);

    void showMensagem(String mensagem);
    void errorClienteNoExist();

    void limparCampos();

    void limparMensagemBuscando();
}
