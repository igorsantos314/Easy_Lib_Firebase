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
        //.d("ENTROU", "insertCliente: " + cliente);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean existCliente(String cpf){

        boolean[] exist_cliete = {false};

        CompletableFuture.runAsync(() -> {
            // method call or code to be asynch.
            readData(new FirebaseCallBack() {
                @Override
                public void onCallback(boolean result) {
                    Log.d("EXIST_CLIENTE", "onCallback: " + result);
                    exist_cliete[0] = result;
                }
            }, cpf);
        });

        return exist_cliete[0];
    }

    private void readData(FirebaseCallBack firebaseCallBack, String cpf){
        //RETORNA A QUERY COM O CLIENTE
        Query query = databaseReference.child(father).orderByChild("cpf").equalTo(cpf);

        // method call or code to be asynch.
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean result = false;

                for(DataSnapshot obj: snapshot.getChildren()){
                    Cliente cliente = obj.getValue(Cliente.class);

                    //Log.d("EXIST_CLIENTE", "onDataChange: " + cliente);
                    //break;
                    result = true;
                }

                firebaseCallBack.onCallback(result);
                //Log.d("EXIST_CLIENTE", "onDataChange: " + exist[0] + exist[1]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface FirebaseCallBack{
        void onCallback(boolean result);
    }
}
