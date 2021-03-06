package com.example.easy_lib.ui.cadastroEmprestimo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_lib.Controller.IOnEmprestimoController;
import com.example.easy_lib.Controller.OnEmprestimoController;
import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.IEmprestimo;
import com.example.easy_lib.Model.ItemEmprestimo;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.IEmprestimoView;
import com.example.easy_lib.databinding.FragmentCadastroEmprestimoBinding;
import com.example.easy_lib.ui.exibirConsultaLivro.TelaExibirConsultaLivro;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FragmentCadastoEmprestimo extends Fragment implements IEmprestimoView {

    private FragmentCadastoEmprestimoViewModel mViewModel;
    private FragmentCadastroEmprestimoBinding binding;

    private AlertDialog.Builder dialologBuilder;
    private AlertDialog dialog;

    TextView txt_nome_cliente, txt_data_emprestimo, txt_data_devolucao, txt_contador;
    MaskedEditText met_cpf;
    TextInputLayout layout_cpf;
    Button add, finalizar;
    ListView ltv_livros_emprestimo;

    IOnEmprestimoController emprestimoController;
    ArrayAdapter<ItemEmprestimo> adapter_itens_emprestimo;
    ArrayList<ItemEmprestimo> itens_emprestimo = new ArrayList<>();

    int quantidade_livros_emprestimo = 0;

    private View view;

    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //PEGA A INTE????O DE RETORNO
                    Intent i = result.getData();

                    //Log.d(TAG, "onActivityResult: " + result.getResultCode());

                    if(result.getResultCode() == 15){
                        //PEGA OS DADOS
                        ItemEmprestimo item = new ItemEmprestimo(
                                i.getStringExtra("codigo"),
                                i.getStringExtra("nome"),
                                Integer.parseInt(i.getStringExtra("quantidade"))
                        );

                        //ADICIONA O LIVRO NA LISTA
                        adicionarLivro(item);
                    }
                    else if(result.getResultCode() == 30){
                        //ATUALIZA A QUANTIDADE DO LIVRO
                        atualizarQuantidade(
                                "update",
                                new ItemEmprestimo(
                                        i.getStringExtra("codigo"),
                                        i.getStringExtra("nome"),
                                        Integer.parseInt(i.getStringExtra("quantidade"))
                                )
                        );
                    }
                    else{
                        Toast.makeText(view.getContext(), "NENHUM LIVRO ADICIONADO", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public static FragmentCadastoEmprestimo newInstance() {
        return new FragmentCadastoEmprestimo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCadastroEmprestimoBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        //COMPONENTES
        emprestimoController = new OnEmprestimoController(this);

        met_cpf = view.findViewById(R.id.txt_Cpf);
        layout_cpf = view.findViewById(R.id.textInputClienteLayout);

        txt_nome_cliente = view.findViewById(R.id.txtNomeClienteEmprestimo);
        txt_data_emprestimo = view.findViewById(R.id.txtDataEmprestimo);
        txt_data_devolucao = view.findViewById(R.id.txtDataDevolucao);
        ltv_livros_emprestimo = view.findViewById(R.id.ltvLivrosEmprestimo);

        txt_contador = view.findViewById(R.id.txtContator);
        add = view.findViewById(R.id.btnAddLivro);
        finalizar = view.findViewById(R.id.btnFinalizar);

        //CHAMADAS
        setDatas();

        //EVENTO DE DIGITAR O CPF
        met_cpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 14){
                    //EXIBIR MENSAGEM DE BUSCANDO
                    setTextBuscando(txt_nome_cliente);

                    //BUSCA O CLIENTE PELO CPF E EXIBE
                    emprestimoController.onExibirDadosCliente("" + s);
                }
                else{
                    //LIMPAR CAMPO DE NOME DE CLIENTE
                    txt_nome_cliente.setText("");

                    //LIMPAR SELE????O DE ERROR
                    layout_cpf.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirProcuraLivro("");
            }
        });

        ltv_livros_emprestimo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //POPUP DO ITEM
                editarItem(position, itens_emprestimo.get(position));
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emprestimoController.insertEmprestimo(
                        txt_data_emprestimo.getText().toString(),
                        txt_data_devolucao.getText().toString(),
                        "PENDENTE",
                        met_cpf.getText().toString(),
                        txt_nome_cliente.getText().toString(),
                        itens_emprestimo
                );
            }
        });

        //SETA O ADAPTER NO LISTVIEW
        adapter_itens_emprestimo = new ArrayAdapter<ItemEmprestimo>(getContext(), android.R.layout.simple_list_item_1, itens_emprestimo);
        ltv_livros_emprestimo.setAdapter(adapter_itens_emprestimo);

        //EXIBE A QUANTIDADE DE LIVROS
        atualizarQuantidadeLivros();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDatas(){
        LocalDate dataDeInscricao = LocalDate.now();
        LocalDate dataDevolucao = dataDeInscricao.plusDays(IEmprestimo.DIAS_DEVOLUCAO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String data = dataDeInscricao.format(formatter).toUpperCase();
        String devolucao = dataDevolucao.format(formatter).toUpperCase();

        //SETAR DATA NOS TEXTVIEW
        txt_data_emprestimo.setText(data);
        txt_data_devolucao.setText(devolucao);
    }

    public void abrirProcuraLivro(String codigo){
        //INTEN????O
        Intent intent = new Intent(view.getContext(), TelaExibirConsultaLivro.class);
        intent.putExtra("codigo", codigo);

        //CHAMA O INTENT ESPERANDO O RESULTADO
        activityResultLauncher.launch(
                intent
        );
    }

    public void atualizarQuantidade(String tipo, ItemEmprestimo item){
        //ATUALIZAR A QUANTIDADE DO LIVRO J?? ADICIONADO
        for (int position=0; position < itens_emprestimo.size(); position++) {
            if (itens_emprestimo.get(position).equals(item)) {
                if(tipo.equals("add")) {
                    itens_emprestimo.get(position).updateQuantidade(item.getQuantidade());
                }
                else{
                    itens_emprestimo.get(position).setQuantidade(item.getQuantidade());
                }
                break;
            }
        }

        //ATUALIZA O ADAPTER
        ltv_livros_emprestimo.setAdapter(adapter_itens_emprestimo);
    }

    public void adicionarLivro(ItemEmprestimo item){

        if (!itens_emprestimo.contains(item)) {
            //MENSAGEM
            Toast.makeText(getContext(), "Livro Adicionado", Toast.LENGTH_SHORT).show();

            //ADICIONA ITEM NO ARRAYLIST
            itens_emprestimo.add(item);

        } else {

            //ATUALIZA A QUANTIDADE DO LIVRO
            atualizarQuantidade("add", item);

            //MENSAGEM
            Toast.makeText(getContext(), "Quantidade Atualizada", Toast.LENGTH_SHORT).show();
        }

        //ATUALIZA A QUANTIDADE DE LIVROS
        quantidade_livros_emprestimo += item.getQuantidade();

        //EXIBE A QUANTIDADE DE LIVROS
        atualizarQuantidadeLivros();

    }

    public void removerItem(int position){
        //ATUALIZA A QUANTIDADE
        quantidade_livros_emprestimo -= itens_emprestimo.get(position).getQuantidade();

        //REMOVER O ITEM
        itens_emprestimo.remove(position);

        //ATUALIZA O ADAPTER
        ltv_livros_emprestimo.setAdapter(adapter_itens_emprestimo);

        //EXIBE A QUANTIDADE DE LIVROS
        atualizarQuantidadeLivros();
    }

    public void setTextBuscando(TextView textView){
        textView.setText("Buscando ....");
    }

    public void atualizarQuantidadeLivros(){

        String plural = "";

        if(quantidade_livros_emprestimo != 1)
            plural += "s";

        //ATUALIZA A QUANTIDADE DE LIVROS
        txt_contador.setText(
                "Quantidade: " + quantidade_livros_emprestimo + " Livro" + plural
        );
    }

    @Override
    public void exibirEmprestimos(Query query) {

    }

    @Override
    public void exibirEmprestimo(Query query) {

    }

    @Override
    public void exibirConsultaClienteEmprestimo(Cliente cliente) {
        //EXIBIR O NOME DO CLIENTE
        txt_nome_cliente.setText(cliente.getNome().toUpperCase());
    }

    @Override
    public void showMensagem(String mensagem) {
        Snackbar snackbar = Snackbar.make(view.findViewById(R.id.id_fragment_cadastro_emprestimo), mensagem, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

    @Override
    public void errorClienteNoExist() {
        //EVENTO
        layout_cpf.setError("CPF INV??LIDO");
    }

    @Override
    public void limparCampos() {

    }

    @Override
    public void limparMensagemBuscando() {
        //LIMPA A MENSAGEM
        txt_nome_cliente.setText("");
    }

    public void editarItem(int position, ItemEmprestimo itemEmprestimo){
        //A CAIXA DE DIALOGO
        dialologBuilder = new AlertDialog.Builder(getContext());

        //INSTANCIA A VIEW
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_editar_item_emprestimo, null);

        //COMPONENTES
        TextView titulo = (TextView) contactPopupView.findViewById(R.id.txtTituloLivro);
        TextInputLayout layout_quantidade = (TextInputLayout) contactPopupView.findViewById(R.id.textInputQuantidadeLayout);
        EditText quantidade = (EditText) contactPopupView.findViewById(R.id.etQuantidadeLivro);

        Button editar = (Button) contactPopupView.findViewById(R.id.btnEditar);
        Button remover = (Button) contactPopupView.findViewById(R.id.btnRemover);

        //ATRIBUTO NO COMPONENTES
        titulo.setText(itemEmprestimo.getNome_livro());
        quantidade.setText("" + itemEmprestimo.getQuantidade());

        quantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //GUARDA O VALOR DO USUARIO
                String quant_digitada = String.valueOf(s);

                //VERIFICA SE A QUANTIDADE N??O ESTA VAZIA
                if(!quant_digitada.isEmpty()) {
                    int quant = Integer.parseInt(quant_digitada);
                    int disponivel = 0;

                    if (quant == 0 || quant > disponivel) {
                        layout_quantidade.setError(layout_quantidade.getHelperText());

                        //DESABILITAR BOT??O
                        editar.setClickable(false);
                        editar.setBackgroundColor(getResources().getColor(R.color.disable));

                    }
                    else {
                        //HABILITAR BOTAO
                        layout_quantidade.setError(null);
                        editar.setClickable(true);
                        editar.setBackgroundColor(getResources().getColor(R.color.blue_btn));
                    }
                }
                else{
                    //DESABILITAR BOT??O
                    editar.setClickable(false);
                    editar.setBackgroundColor(getResources().getColor(R.color.disable));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FECHAR DIALOGO
                dialog.dismiss();

                //ABRE A TELA PARA SELECIONAR NOVA QUANTIDADE
                abrirProcuraLivro(itemEmprestimo.getCodigo_livro());
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REMOVER O ITEM DA LISTA
                removerItem(position);

                //FECHAR DIALOGO
                dialog.dismiss();
            }
        });

        //ENVIA A VIEW PARA O CRIADOR DE DIALOG
        dialologBuilder.setView(contactPopupView);

        //INSTANCIA O DIALOG
        dialog = dialologBuilder.create();

        //EXIBE
        dialog.show();
    }
}