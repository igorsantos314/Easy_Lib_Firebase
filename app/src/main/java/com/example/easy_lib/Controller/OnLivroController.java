package com.example.easy_lib.Controller;

import androidx.annotation.NonNull;

import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.Model.LivroFirebaseDAO;
import com.example.easy_lib.View.IClienteView;
import com.example.easy_lib.View.ILivroView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class OnLivroController implements IOnLivroController{

    private ILivroView view;
    private LivroFirebaseDAO livroFirebaseDAO;

    public OnLivroController(ILivroView view) {
        this.view = view;
        livroFirebaseDAO = new LivroFirebaseDAO();
    }

    @Override
    public void insertLivro(int codigo, String prateleira, int ano, String nome, String estante, String autor, String editora, int quantidade, String genero) {
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
    public void updateLivro(int codigo, String prateleira, int ano, String nome, String estante, String autor, String editora, int quantidade, String genero) {

    }

    @Override
    public void deleteLivro(int codigo) {

    }

    @Override
    public void updateQuantidadeLivro(int codigo, int quantidade_atual, int retirada) {
        livroFirebaseDAO.updateQuantidade(codigo, quantidade_atual + retirada);
    }

    @Override
    public void onExibirTodosLivros() {

    }

    @Override
    public void onExibirLivro(int codigo) {

    }

    @Override
    public void onExibirLivrosPorNome(String nome) {

    }

    @Override
    public void onExibirQuantidadeLivros() {

    }

    @Override
    public boolean onValidarCampos(Livro livro) {
        return false;
    }
}
