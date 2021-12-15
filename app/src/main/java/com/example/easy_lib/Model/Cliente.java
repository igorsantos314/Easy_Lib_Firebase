package com.example.easy_lib.Model;




public class Cliente implements ICliente {

    private String cpf;
    private String nome;
    private String data_nascimento;
    private String telefone;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero_complemento;

    public Cliente() {
    }

    public Cliente(String cpf, String nome, String data_nascimento, String telefone, String cidade, String bairro, String rua, String numero_complemento) {
        this.cpf = cpf;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.telefone = telefone;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero_complemento = numero_complemento;
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero_complemento() {
        return numero_complemento;
    }

    public void setNumero_complemento(String numero_complemento) {
        this.numero_complemento = numero_complemento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", data_nascimento='" + data_nascimento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numero_complemento='" + numero_complemento + '\'' +
                '}';
    }
}
