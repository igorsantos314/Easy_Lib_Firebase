package com.example.easy_lib.ui.cadastroLivro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.easy_lib.Controller.IOnLivroController;
import com.example.easy_lib.Controller.OnLivroController;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.ILivroView;
import com.example.easy_lib.databinding.FragmentCadastroLivroBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CadastroLivroFragment extends Fragment implements ILivroView {

    private CadastroLivroViewModel mViewModel;
    private FragmentCadastroLivroBinding binding;

    EditText txt_codigo, txt_nome, txt_autor, txt_editora, txt_ano_lancamento, txt_quantidade, txt_genero, txt_prateleira, txt_estante;
    Button btn_salvar;

    TextInputLayout layout_codigo_livro;

    private View view;
    private IOnLivroController livroController;

    public static CadastroLivroFragment newInstance() {
        return new CadastroLivroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_livro, container, false);

        //COMPONENTES
        binding = FragmentCadastroLivroBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        livroController = new OnLivroController(this);

        txt_codigo = view.findViewById(R.id.etQuantidadeLivro);
        layout_codigo_livro = view.findViewById(R.id.textInputCodLivroLayout);

        txt_nome = view.findViewById(R.id.txt_NomeLivro);
        txt_autor = view.findViewById(R.id.txt_Autor);
        txt_editora = view.findViewById(R.id.txt_Editora);
        txt_ano_lancamento =view.findViewById(R.id.txt_Ano);
        txt_quantidade = view.findViewById(R.id.txt_Quantidade);
        txt_genero = view.findViewById(R.id.txt_Genero);
        txt_prateleira = view.findViewById(R.id.txt_Prateleira);
        txt_estante = view.findViewById(R.id.txt_Estante);

        btn_salvar = view.findViewById(R.id.btnSalvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livroController.insertLivro(
                        txt_codigo.getText().toString(),
                        txt_prateleira.getText().toString(),
                        txt_ano_lancamento.getText().toString(),
                        txt_nome.getText().toString(),
                        txt_estante.getText().toString(),
                        txt_autor.getText().toString(),
                        txt_editora.getText().toString(),
                        txt_quantidade.getText().toString(),
                        txt_genero.getText().toString()
                );
            }
        });

        txt_codigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 13){
                    //VERIFICA SE O LIVRO EXISTE
                    livroController.onExistLivro("" + s);
                }
                else{
                    //LIMPAR ERROR
                    layout_codigo_livro.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void adicionarLivro(Livro livro) {

    }

    @Override
    public void exibirLivros(ArrayList<Livro> livros) {

    }

    @Override
    public void exibirLivro(Query query) {

    }

    @Override
    public void errorLivroExist() {
        //SETA O ERROR NO LAYOUT
        layout_codigo_livro.setError("LIVRO JÁ ESTÁ CADASTRADO");
    }

    @Override
    public void showMensagem(String mensagem) {
        Snackbar snackbar = Snackbar.make(view.findViewById(R.id.id_fragment_cadastro_livro), mensagem, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

    @Override
    public void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        txt_autor.setText("");
        txt_editora.setText("");
        txt_ano_lancamento.setText("");
        txt_quantidade.setText("");
        txt_genero.setText("");
        txt_prateleira.setText("");
        txt_estante.setText("");
    }
}