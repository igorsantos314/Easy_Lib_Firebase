package com.example.easy_lib.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.List;

public interface IClienteFirebaseDAO {
    Task<Void> insertCliente (Cliente cliente);
    Task<Void> updateCliente (Cliente cliente);
    Task<Void> deleteCliente (String cpf);

    Query getTodosClientes();
    Query getCliente(String cpf);
    Query getClientePorNome (String nome);
    Query getQuantidadeClientes();

    boolean existCliente(String cpf);
}
