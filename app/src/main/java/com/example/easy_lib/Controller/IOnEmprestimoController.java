package com.example.easy_lib.Controller;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.Emprestimo;
import com.example.easy_lib.Model.Livro;

import java.util.ArrayList;

public interface IOnEmprestimoController {
    void insertEmprestimo (String dataEmprestimo, String dataDevolucao, String status, String cpfCliente, String nomeCliente, ArrayList<Livro> livros_emprestimo);
    void updateEmprestimo (String uid, String dataEmprestimo, String dataDevolucao, String status, String cpfCliente, String nomeCliente, ArrayList<Livro> livros_emprestimo);
    void deleteEmprestimo (String uid);

    void onExibirTodosEmprestimos();
    void onExibirEmprestimo(String cpf);
    void onExibirQuantidadeEmprestimos();

    void onExibirDadosCliente(String cpf);

    boolean onValidarCampos(Emprestimo emprestimo);
}
