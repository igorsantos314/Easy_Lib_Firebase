package com.example.easy_lib.ui.exibirConsultaLivro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easy_lib.Controller.IOnLivroController;
import com.example.easy_lib.Controller.OnLivroController;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.ILivroView;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class TelaExibirConsultaLivro extends AppCompatActivity implements ILivroView {

    Button cancelar;
    TextView txt_titulo;

    ListView ltv_titulos_disponiveis;

    Intent i;
    IOnLivroController livroController;
    consultaLivroListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exibir_consulta_livro);

        //SETAR TELA CHEIA
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //OCULTAR ACTION BAR
        getSupportActionBar().hide();

        //COMPONENTES
        i = getIntent();

        livroController = new OnLivroController(this);

        txt_titulo = findViewById(R.id.txt_NomeLivro);
        ltv_titulos_disponiveis = findViewById(R.id.ltvTitulosDisponiveis);
        cancelar = findViewById(R.id.btn_cancelar_retorno);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ENVIAR INFORMAÇÕES
                //i.putExtra("cliente", lista_clientes.getSelectedItem().toString());

                //ENVIA A INTENÇÃO
                setResult(0, i);

                finish();
            }
        });

        txt_titulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EXIBIR LIVROS PELO NOME
                livroController.onExibirLivrosPorNome(
                        String.valueOf(s).toUpperCase()
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ltv_titulos_disponiveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //INTENCAO
                Intent intentLivro = new Intent();

                //PEGA O LIVRO
                Livro livro = (Livro) ltv_titulos_disponiveis.getItemAtPosition(position);

                intentLivro.putExtra("codigo", livro.getCodigo());
                intentLivro.putExtra("nome", livro.getNome());

                //ENVIA A INTENÇÃO
                setResult(15, intentLivro);

                finish();
            }
        });

        //LISTAR TODOS OS LIVROS DE INICIO
        livroController.onExibirLivrosPorNome("");

    }

    @Override
    public void exibirLivros(ArrayList<Livro> livros) {
        //CRIA O ADAPTER DE LIVROS
        adapter = new consultaLivroListAdapter(this, R.layout.item_list, livros);

        //SETA O ADAPTER
        ltv_titulos_disponiveis.setAdapter(adapter);
    }

    @Override
    public void exibirLivro(Query query) {

    }

    @Override
    public void errorLivroExist() {

    }

    @Override
    public void showMensagem(String mensagem) {

    }

    @Override
    public void limparCampos() {

    }
}