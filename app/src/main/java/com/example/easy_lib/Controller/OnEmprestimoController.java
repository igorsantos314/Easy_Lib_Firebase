package com.example.easy_lib.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.Model.Emprestimo;
import com.example.easy_lib.Model.EmprestimoFirebaseDAO;
import com.example.easy_lib.Model.ItemEmprestimo;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.View.IClienteView;
import com.example.easy_lib.View.IEmprestimoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnEmprestimoController implements IOnEmprestimoController{

    private IEmprestimoView view;
    private EmprestimoFirebaseDAO emprestimoFirebaseDAO;

    public OnEmprestimoController(IEmprestimoView view) {
        this.view = view;
        emprestimoFirebaseDAO = new EmprestimoFirebaseDAO();
    }

    @Override
    public void insertEmprestimo(String dataEmprestimo, String dataDevolucao, String status, String cpfCliente, String nomeCliente, ArrayList<ItemEmprestimo> itens_emprestimo) {

        emprestimoFirebaseDAO.insertEmprestimo(
                new Emprestimo(dataEmprestimo, dataDevolucao, status, cpfCliente, nomeCliente, itens_emprestimo)
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //MENSAGEM DE SUCESSO
                view.showMensagem("EMPRESTIMO FINALIZADO");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //MENSAGEM DE ERROR
                view.showMensagem("ERROR AO FINALIZAR EMPRESTIMO");
            }
        });
    }

    @Override
    public void updateEmprestimo(String uid, String dataEmprestimo, String dataDevolucao, String status, String cpfCliente, String nomeCliente, ArrayList<ItemEmprestimo> itens_emprestimo) {

    }

    @Override
    public void deleteEmprestimo(String uid) {

    }

    @Override
    public void onExibirTodosEmprestimos() {

    }

    @Override
    public void onExibirEmprestimo(String cpf) {

    }

    @Override
    public void onExibirQuantidadeEmprestimos() {

    }

    @Override
    public void onExibirDadosCliente(String cpf) {
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

                //EXIBIR INFORMAÇÕES DO CLIENTE
                if(cliente != null){
                    //EXIBE AS INFORMAÇÕES NA VIEW
                    view.exibirConsultaClienteEmprestimo(cliente);
                }
                else{
                    //LIMPA O BUSCANDO
                    view.limparMensagemBuscando();

                    //MARCA O CAMPO DE CPF
                    view.errorClienteNoExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onValidarCampos(Emprestimo emprestimo) {
        return false;
    }
}
