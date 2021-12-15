package com.example.easy_lib.Model;

public class Livro {

    private int codigo;
    private String prateleira;
    private int ano;
    private String nome;
    private String estante;
    private String autor;
    private String editora;
    private int quantidade;
    private String genero;

    public Livro() {
    }

    public Livro(int codigo, String prateleira, int ano, String nome, String estante, String autor, String editora, int quantidade, String genero) {
        this.codigo = codigo;
        this.prateleira = prateleira;
        this.ano = ano;
        this.nome = nome;
        this.estante = estante;
        this.autor = autor;
        this.editora = editora;
        this.quantidade = quantidade;
        this.genero = genero;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "codigo=" + codigo +
                ", prateleira='" + prateleira + '\'' +
                ", ano=" + ano +
                ", nome='" + nome + '\'' +
                ", estante='" + estante + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", quantidade=" + quantidade +
                ", genero='" + genero + '\'' +
                '}';
    }
}
