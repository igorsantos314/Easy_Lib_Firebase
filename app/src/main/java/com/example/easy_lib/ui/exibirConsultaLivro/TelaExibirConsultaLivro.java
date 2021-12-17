package com.example.easy_lib.ui.exibirConsultaLivro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easy_lib.Controller.IOnLivroController;
import com.example.easy_lib.Controller.OnLivroController;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.ILivroView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class TelaExibirConsultaLivro extends AppCompatActivity implements ILivroView {

    private AlertDialog.Builder dialologBuilder;
    private AlertDialog dialog;

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

                //PEGA O LIVRO
                Livro livro = (Livro) ltv_titulos_disponiveis.getItemAtPosition(position);

                adicionarLivro(livro);
            }
        });

        //LISTAR TODOS OS LIVROS DE INICIO
        livroController.onExibirLivrosPorNome("");
    }

    public void adicionarLivro(Livro livro){
        //A CAIXA DE DIALOGO
        dialologBuilder = new AlertDialog.Builder(this);

        //INSTANCIA A VIEW
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_adicionar_livro, null);

        //COMPONENTES
        TextView titulo = (TextView) contactPopupView.findViewById(R.id.txtTituloLivro);
        TextInputLayout layout_quantidade = (TextInputLayout) contactPopupView.findViewById(R.id.textInputQuantidadeLayout);
        EditText quantidade = (EditText) contactPopupView.findViewById(R.id.etQuantidadeLivro);
        Button adicionar = (Button) contactPopupView.findViewById(R.id.btnAdicionar);

        //ATRIBUTO NO COMPONENTES
        titulo.setText(livro.getNome());
        layout_quantidade.setHelperText("Diponíveis: " + livro.getQuantidade());

        //DESBILITAR BOTAO INICIALMENTE
        adicionar.setClickable(false);
        adicionar.setBackgroundColor(getResources().getColor(R.color.disable));

        quantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //GUARDA O VALOR DO USUARIO
                String quant_digitada = String.valueOf(s);

                //VERIFICA SE A QUANTIDADE NÃO ESTA VAZIA
                if(!quant_digitada.isEmpty()) {
                    int quant = Integer.parseInt(quant_digitada);
                    int disponivel = Integer.parseInt(livro.getQuantidade());

                    if (quant == 0 || quant > disponivel) {
                        layout_quantidade.setError(layout_quantidade.getHelperText());

                        //DESABILITAR BOTÃO
                        adicionar.setClickable(false);
                        adicionar.setBackgroundColor(getResources().getColor(R.color.disable));

                    }
                    else {
                        //HABILITAR BOTAO
                        layout_quantidade.setError(null);
                        adicionar.setClickable(true);
                        adicionar.setBackgroundColor(getResources().getColor(R.color.blue_btn));
                    }
                }
                else{
                    //DESABILITAR BOTÃO
                    adicionar.setClickable(false);
                    adicionar.setBackgroundColor(getResources().getColor(R.color.disable));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INTENCAO
                Intent intentItemEmprestimo = new Intent();

                //ATRIBUI OS DADOS AO INTENT
                intentItemEmprestimo.putExtra("codigo", livro.getCodigo());
                intentItemEmprestimo.putExtra("nome", livro.getNome());
                intentItemEmprestimo.putExtra("quantidade", quantidade.getText().toString());

                //ENVIA A INTENÇÃO
                setResult(15, intentItemEmprestimo);

                finish();
            }
        });

        //ENVIA A VIEW PARA O CRIADOR DE DIALOG
        dialologBuilder.setView(contactPopupView);

        //INSTANCIA O DIALOG
        dialog = dialologBuilder.create();

        //EXIBE
        dialog.show();
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