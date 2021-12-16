package com.example.easy_lib.Model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ClienteFirebaseDAO implements IClienteFirebaseDAO{

    private DatabaseReference databaseReference;
    private final String father = Cliente.class.getSimpleName();

    public ClienteFirebaseDAO(){
        //INSTANCIA O BANCO DE DADOS
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        //REFERENCIA DO BANCO DE DADOS
        databaseReference = db.getReference();
    }

    @Override
    public Task<Void> insertCliente(Cliente cliente) {
        //CPF PARA PAI DO NÓ SEM PONTOS E TRAÇOS
        String cpf_formated = cliente.getCpf().replace(".", "").replace("-", "");

        //ADICIONA NO BD
        return databaseReference.child(father).child(cpf_formated).setValue(cliente);
    }

    @Override
    public Task<Void> updateCliente(Cliente cliente) {
        return null;
    }

    @Override
    public Task<Void> deleteCliente(String cpf) {
        return null;
    }

    @Override
    public Query getTodosClientes() {
        return null;
    }

    @Override
    public Query getCliente(String cpf) {
        //RETORNA A QUERY COM O CLIENTE
        return databaseReference.child(father).orderByChild("cpf").equalTo(cpf);
    }

    @Override
    public Query getClientePorNome(String nome) {
        return null;
    }

    @Override
    public Query getQuantidadeClientes() {
        return null;
    }

    @Override
    public boolean existCliente(String cpf){

        //RETORNA A QUERY COM O CLIENTE
        Query query = databaseReference.child(father).orderByChild("cpf").equalTo(cpf);

        // method call or code to be asynch.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot obj: snapshot.getChildren()){
                    Cliente cliente = obj.getValue(Cliente.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return false;
    }
}
