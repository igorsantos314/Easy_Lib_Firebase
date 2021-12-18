package com.example.easy_lib.Controller;

import androidx.annotation.NonNull;

import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.Model.LivroFirebaseDAO;
import com.example.easy_lib.View.IClienteView;
import com.example.easy_lib.View.ILivroView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnLivroController implements IOnLivroController{

    private ILivroView view;
    private LivroFirebaseDAO livroFirebaseDAO;

    public OnLivroController(ILivroView view) {
        this.view = view;
        livroFirebaseDAO = new LivroFirebaseDAO();
    }

    @Override
    public void insertLivro(String codigo, String prateleira, String ano, String nome, String estante, String autor, String editora, String quantidade, String genero) {
        //INSTANCIA DO LIVRO
        Livro livro = new Livro(codigo, prateleira, ano, nome.toUpperCase(), estante, autor, editora, quantidade, genero);

        //INSERE E AGUARDA O RETORNO SUCESSO OU FALHA
        livroFirebaseDAO.insertLivro(
                livro
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //SUCESSO AO REALIZAR O INSERT
                view.limparCampos();

                //MENSAGEM DE SUCESSO
                view.showMensagem("Livro salvo");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //CASO OCORRA ALGUM ERROR
                view.showMensagem("Error ao salvar o Livro");
            }
        });
    }

    @Override
    public void updateLivro(String codigo, String prateleira, String ano, String nome, String estante, String autor, String editora, String quantidade, String genero) {

    }

    @Override
    public void deleteLivro(String codigo) {

    }

    @Override
    public void updateQuantidadeLivro(int codigo, int quantidade_atual, int retirada) {
        livroFirebaseDAO.updateQuantidade(codigo, quantidade_atual + retirada);
    }

    @Override
    public void onExistLivro(String codigo) {
        Query query = livroFirebaseDAO.getLivro(codigo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //INSTANCIA DO LIVRO
                Livro livro = null;

                for(DataSnapshot obj: snapshot.getChildren()){
                    livro = obj.getValue(Livro.class);
                    break;
                }

                //VERIFICA SE O LIVRO EXISTE
                if(livro != null){
                    //EXIBE ERROR DE LIVRO EXISTE NA VIEW
                    view.errorLivroExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onExibirTodosLivros() {

    }

    @Override
    public void onExibirLivro(String codigo) {
        Query query = livroFirebaseDAO.getLivro(codigo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //INSTANCIA DO LIVRO
                Livro livro = null;

                for(DataSnapshot obj: snapshot.getChildren()){
                    livro = obj.getValue(Livro.class);
                    break;
                }

                //VERIFICA SE O LIVRO EXISTE
                if(livro != null){
                    //EXIBE ERROR DE LIVRO EXISTE NA VIEW
                    view.errorLivroExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onExibirLivrosPorNome(String nome) {

        Query query = livroFirebaseDAO.getLivroPorNome("" + nome);

        query.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Livro> livros = new ArrayList<>();

                for(DataSnapshot obj: dataSnapshot.getChildren()){

                    //CRIA UMA INSTANCIA DE PERSON COM OS DADOS
                    Livro livro = obj.getValue(Livro.class);

                    //SETA UID DO OBJETO
                    //livro.setUid(obj.getKey());

                    //ADICIONA AS PERSONS NA LISTA
                    livros.add(livro);
                }

                //POVOA A LISTA DE LIVROS
                view.exibirLivros(livros);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onExibirQuantidadeLivros() {

    }

    @Override
    public void onExibirLivroConsulta(String codigo) {
        Query query = livroFirebaseDAO.getLivro(codigo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //INSTANCIA DO LIVRO
                Livro livro = null;

                for(DataSnapshot obj: snapshot.getChildren()){
                    livro = obj.getValue(Livro.class);
                    break;
                }

                //VERIFICA SE O LIVRO EXISTE
                if(livro != null){
                    //ABRE A TELA DE ESCOLHER A QUANTIDADE DO LIVRO
                    view.adicionarLivro(livro);
                }
                else{
                    //EXIBE ERROR DE LIVRO NÃO EXISTE NA VIEW
                    view.errorLivroExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onValidarCampos(Livro livro) {
        return false;
    }



}
