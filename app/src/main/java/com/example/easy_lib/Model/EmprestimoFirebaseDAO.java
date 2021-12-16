package com.example.easy_lib.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EmprestimoFirebaseDAO implements IEmprestimoFirebaseDAO{

    private DatabaseReference databaseReference;
    private final String father = Emprestimo.class.getSimpleName();

    public EmprestimoFirebaseDAO(){
        //INSTANCIA O BANCO DE DADOS
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        //REFERENCIA DO BANCO DE DADOS
        databaseReference = db.getReference();
    }

    @Override
    public Task<Void> insertEmprestimo(Emprestimo emprestimo) {
        //INSERE O EMPRESTIMO NA DATABASE
        return databaseReference.child(father).push().setValue(emprestimo);
    }

    @Override
    public Task<Void> updateEmprestimo(Emprestimo emprestimo) {
        return null;
    }

    @Override
    public Task<Void> deleteEmprestimo(String uid) {
        return null;
    }

    @Override
    public Query getTodosEmprestimos() {
        return null;
    }

    @Override
    public Query getEmprestimo(String uid) {
        return null;
    }

    @Override
    public Query getEmprestimoCpf(String cpf) {
        return null;
    }

    @Override
    public Query getQuantidadeClientes() {
        return null;
    }
}
