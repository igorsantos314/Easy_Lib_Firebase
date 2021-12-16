package com.example.easy_lib.Model;

import java.util.Objects;

public class Livro {

    private String codigo;
    private String prateleira;
    private String ano;
    private String nome;
    private String estante;
    private String autor;
    private String editora;
    private String quantidade;
    private String genero;

    public Livro() {
    }

    public Livro(String codigo, String prateleira, String ano, String nome, String estante, String autor, String editora, String quantidade, String genero) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
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

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
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
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(codigo, livro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
