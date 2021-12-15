package com.example.easy_lib.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

public interface ILivroFirebaseDAO {
    Task<Void> insertLivro (Livro livro);
    Task<Void> updateLivro (Livro livro);
    Task<Void> deleteLivro (Livro livro);
    Task<Void> updateQuantidade(int codigo, int quantidade);

    Query getTodosLivros();
    Query getLivro(String codigo);
    Query getLivroPorNome(String codigo);
    Query getQuantidadeLivros();
}
