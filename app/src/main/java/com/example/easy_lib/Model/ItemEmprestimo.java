package com.example.easy_lib.Model;

import java.util.Objects;

public class ItemEmprestimo {

    private String codigo_livro;
    private String nome_livro;
    private int quantidade;

    public ItemEmprestimo() {
    }

    public ItemEmprestimo(String codigo_livro, String nome_livro, int quantidade) {
        this.codigo_livro = codigo_livro;
        this.nome_livro = nome_livro;
        this.quantidade = quantidade;
    }

    public void updateQuantidade(int quantidade){
        this.quantidade += quantidade;
    }

    public String getCodigo_livro() {
        return codigo_livro;
    }

    public void setCodigo_livro(String codigo_livro) {
        this.codigo_livro = codigo_livro;
    }

    public String getNome_livro() {
        return nome_livro;
    }

    public void setNome_livro(String nome_livro) {
        this.nome_livro = nome_livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEmprestimo that = (ItemEmprestimo) o;
        return Objects.equals(codigo_livro, that.codigo_livro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo_livro);
    }

    @Override
    public String toString() {
        return quantidade + "x - " + nome_livro;
    }
}
