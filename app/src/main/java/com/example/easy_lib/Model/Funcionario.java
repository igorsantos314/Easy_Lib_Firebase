package com.example.easy_lib.Model;

public class Funcionario extends Usuario {

    public Funcionario(String login, String senha) {
        super(login, senha);
    }

    public Funcionario(String cpf, String nome, String data_nascimento, String telefone, String cidade, String bairro, String rua, String numero_completo, String login, String senha, String cargo) {
        super(cpf, nome, data_nascimento, telefone, cidade, bairro, rua, numero_completo, login, senha, cargo);
    }
}
