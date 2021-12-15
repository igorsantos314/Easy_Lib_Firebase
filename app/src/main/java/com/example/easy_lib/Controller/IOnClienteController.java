package com.example.easy_lib.Controller;

import com.example.easy_lib.Model.Cliente;

public interface IOnClienteController {
    void insertCliente (String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade);
    void updateCliente (String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade);
    void deleteCliente (String cpf);

    void onExibirTodosClientes();
    void onExibirCliente(String cpf);
    void onExibirClientesPorNome (String nome);
    void onExibirQuantidadeClientes();

    void onExistCliente(String cpf);

    boolean onValidarCampos(Cliente cliente);
}
