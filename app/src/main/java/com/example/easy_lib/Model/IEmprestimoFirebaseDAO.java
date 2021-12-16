package com.example.easy_lib.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

public interface IEmprestimoFirebaseDAO {
    Task<Void> insertEmprestimo (Emprestimo emprestimo);
    Task<Void> updateEmprestimo (Emprestimo emprestimo);
    Task<Void> deleteEmprestimo (String uid);

    Query getTodosEmprestimos();
    Query getEmprestimo(String uid);
    Query getEmprestimoCpf (String cpf);
    Query getQuantidadeClientes();
}
