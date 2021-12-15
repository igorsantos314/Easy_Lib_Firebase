package com.example.easy_lib.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.View.IClienteView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OnClienteController implements IOnClienteController{

    private IClienteView view;
    private ClienteFirebaseDAO clienteFirebaseDAO;

    public OnClienteController(IClienteView view) {
        this.view = view;
        clienteFirebaseDAO = new ClienteFirebaseDAO();
    }

    @Override
    public void insertCliente(String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade) {

        //INSTANCIA DO CLIENTE
        Cliente cliente = new Cliente(cpf, nome.toUpperCase(), data_nascimento, telefone, cidade, bairro, rua, numero);

        //VALIDA OS CAMPOS
        if(onValidarCampos(cliente)) {
            clienteFirebaseDAO.insertCliente(
                    cliente
            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    //SUCESSO AO REALIZAR O INSERT
                    view.limparCampos();

                    //MENSAGEM DE SUCESSO
                    view.showMensagem("CLIENTE SALVO");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //CASO OCORRA ALGUM ERROR
                    view.showMensagem("ERROR AO SALVAR CLIENTE");
                }
            });
        }
        else{
            //CAMPOS FAZIOS
            view.showMensagem("PREENCHA OS CAMPOS OBRIGATÓRIOS");
        }
    }

    @Override
    public void updateCliente(String nome, String cpf, String data_nascimento, String telefone, String rua, String numero, String bairro, String cidade) {

    }

    @Override
    public void deleteCliente(String cpf) {

    }

    @Override
    public void onExibirTodosClientes() {

    }

    @Override
    public void onExibirCliente(String cpf) {

    }

    @Override
    public void onExibirClientesPorNome(String nome) {

    }

    @Override
    public void onExibirQuantidadeClientes() {

    }

    @Override
    public void onExistCliente(String cpf) {
        ClienteFirebaseDAO clienteFirebaseDAO = new ClienteFirebaseDAO();

        Query query = clienteFirebaseDAO.getCliente(cpf);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //INSTANCIA DO CLIENTE
                Cliente cliente = null;

                for(DataSnapshot obj: snapshot.getChildren()){
                    cliente = obj.getValue(Cliente.class);
                    break;
                }

                //VERIFICA SE O CLIENTE EXISTE
                if(cliente != null){
                    //EXIBE ERROR DE CLIENTE EXISTE NA VIEW
                    view.errorClienteExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onValidarCampos(Cliente cliente) {
        if(cliente.getCpf().isEmpty() || cliente.getNome().isEmpty() || cliente.getData_nascimento().isEmpty()
                || cliente.getTelefone().isEmpty() || cliente.getCidade().isEmpty() || cliente.getBairro().isEmpty()
                || cliente.getRua().isEmpty() || cliente.getNumero_complemento().isEmpty())
            //ALGUM CAMPO VAZIO
            return false;

        //CAMPOS NÃO VAZIOS
        return true;
    }
}
