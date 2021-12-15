package com.example.easy_lib.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LivroFirebaseDAO implements ILivroFirebaseDAO{

    private DatabaseReference databaseReference;
    private final String father = Livro.class.getSimpleName();

    public LivroFirebaseDAO(){
        //INSTANCIA O BANCO DE DADOS
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        //REFERENCIA DO BANCO DE DADOS
        databaseReference = db.getReference();
    }

    @Override
    public Task<Void> insertLivro(Livro livro) {
        //ADICIONA NO BD
        return databaseReference.child(father).child(String.valueOf(livro.getCodigo())).setValue(livro);
    }

    @Override
    public Task<Void> updateLivro(Livro livro) {
        return null;
    }

    @Override
    public Task<Void> deleteLivro(Livro livro) {
        return null;
    }

    @Override
    public Task<Void> updateQuantidade(int codigo, int quantidade) {
        //ATUALIZA A QUANTIDADE DE LIVROS EM ESTOQUE
        return databaseReference.child(father).child(String.valueOf(codigo)).child("quantidade").setValue(quantidade);
    }

    @Override
    public Query getTodosLivros() {
        return null;
    }

    @Override
    public Query getLivro(String codigo) {
        return null;
    }

    @Override
    public Query getLivroPorNome(String codigo) {
        return null;
    }

    @Override
    public Query getQuantidadeLivros() {
        return null;
    }
}
