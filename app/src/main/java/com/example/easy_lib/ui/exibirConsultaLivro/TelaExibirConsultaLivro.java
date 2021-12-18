package com.example.easy_lib.ui.exibirConsultaLivro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_lib.Code.Capture;
import com.example.easy_lib.Controller.IOnLivroController;
import com.example.easy_lib.Controller.OnLivroController;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.ILivroView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class TelaExibirConsultaLivro extends AppCompatActivity implements ILivroView {

    private AlertDialog.Builder dialologBuilder;
    private AlertDialog dialog;

    Button cancelar, scanner;
    TextView txt_titulo;

    ListView ltv_titulos_disponiveis;

    Intent i;
    IOnLivroController livroController;
    consultaLivroListAdapter adapter;

    //PEGA O CÓDIGO QUE VEIO DA OUTRA TELA
    String codigo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //RESULTADO DA CHAMADA DO INTEGRATOR
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        //TRATAR OS RESULTADOS
        if(intentResult.getContents() != null){
            //Toast.makeText(TelaExibirConsultaLivro.this, "" + intentResult.getContents(), Toast.LENGTH_SHORT).show();

            //FAZ A BUSCA E EXIBE A TELA DE ADICIONAR A QUANTIDADE
            livroController.onExibirLivroConsulta(
                    intentResult.getContents()
            );

        }else{
            Toast.makeText(TelaExibirConsultaLivro.this, "Não foi possível ler o código", Toast.LENGTH_SHORT).show();
        }
    }

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
        codigo = i.getStringExtra("codigo");

        livroController = new OnLivroController(this);

        txt_titulo = findViewById(R.id.txt_NomeLivro);
        ltv_titulos_disponiveis = findViewById(R.id.ltvTitulosDisponiveis);
        scanner = findViewById(R.id.btn_scan);

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

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INICIALIZA A INSTANCIA
                IntentIntegrator intentIntegrator = new IntentIntegrator(TelaExibirConsultaLivro.this);

                //EXIBIR MENSAGEM AO USUARIO
                intentIntegrator.setPrompt("Volume UP para ligar o Flash");

                //BEEP POS LEITURA
                intentIntegrator.setBeepEnabled(true);

                //BLOCKEAR ORIENTAÇÃO
                intentIntegrator.setOrientationLocked(true);

                //SET CAPTURE
                intentIntegrator.setCaptureActivity(Capture.class);

                //INICIA A CAPTURA
                intentIntegrator.initiateScan();
            }
        });

        //LISTAR TODOS OS LIVROS DE INICIO
        //livroController.onExibirLivrosPorNome("");

        //VERIFICAR SE A CHAMADA FOI DE EDIÇÃO
        editarLivroLista();
    }

    @Override
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
        Button cancelar_fechar = (Button) contactPopupView.findViewById(R.id.btnCancelarFechar);

        //USAR O BOTÃO PARA CANCELAR
        dialologBuilder.setCancelable(false);

        //VERIFICA SE O BOTAO É CANCELAR OU FECHAR
        if(!codigo.equals("")){
            layout_quantidade.setHint("INFORME A NOVA QUANTIDADE");
            adicionar.setText("ATUALIZAR");
            cancelar_fechar.setText("CANCELAR");
        }

        //ATRIBUTO NO COMPONENTES
        titulo.setText(livro.getNome());
        layout_quantidade.setHelperText("Diponíveis: " + livro.getQuantidade());

        //DESBILITAR BOTAO INICIALMENTE
        adicionar.setClickable(false);
        adicionar.setBackgroundColor(getResources().getColor(R.color.disable));

        //FOCAR NO CAMPO DE QUANTIDADE E ABRIR O TECLADO
        quantidade.requestFocus();
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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
                int resultCode = 15;

                if(!codigo.equals(""))
                    //ENVIA O LIVRO PARA ATUALIZAR A QUANTIDADE
                    resultCode = 30;

                setResult(resultCode, intentItemEmprestimo);

                finish();
            }
        });

        cancelar_fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OCULTAR TECLADO
                imm.hideSoftInputFromWindow(quantidade.getWindowToken(), 0);

                if(!codigo.equals(""))
                    finish();
                else
                    dialog.dismiss();
            }
        });

        //imm.showSoftInput(quantidade, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        //ENVIA A VIEW PARA O CRIADOR DE DIALOG
        dialologBuilder.setView(contactPopupView);

        //INSTANCIA O DIALOG
        dialog = dialologBuilder.create();

        //EXIBE
        dialog.show();
    }

    public void editarLivroLista(){

        if(!codigo.equals("")){
            //PEGA O LIVRO ATUALIZADO DO FIREBASE E ABRE O ESCOLHER QUANTIDADE
            livroController.onExibirLivroConsulta(codigo);
        }
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
        Toast.makeText(this, "Livro não encontrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMensagem(String mensagem) {

    }

    @Override
    public void limparCampos() {

    }
}