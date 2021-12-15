package com.example.easy_lib.Controller;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.Emprestimo;

public interface IOnEmprestimoController {
    void insertEmprestimo (String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade);
    void updateEmprestimo (String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade);
    void deleteEmprestimo (String uid);

    void onExibirTodosEmprestimos();
    void onExibirEmprestimo(String cpf);
    void onExibirQuantidadeEmprestimos();

    void onExibirDadosCliente(String cpf);

    boolean onValidarCampos(Emprestimo emprestimo);
}
