package com.example.easy_lib.ui.cadastroEmprestimo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_lib.Controller.IOnEmprestimoController;
import com.example.easy_lib.Controller.OnEmprestimoController;
import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.Model.Emprestimo;
import com.example.easy_lib.Model.IEmprestimo;
import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;
import com.example.easy_lib.View.IEmprestimoView;
import com.example.easy_lib.databinding.FragmentCadastroEmprestimoBinding;
import com.example.easy_lib.ui.exibirConsultaLivro.TelaExibirConsultaLivro;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FragmentCadastoEmprestimo extends Fragment implements IEmprestimoView {

    private FragmentCadastoEmprestimoViewModel mViewModel;
    private FragmentCadastroEmprestimoBinding binding;

    TextView txt_nome_cliente, txt_data_emprestimo, txt_data_devolucao, txt_contador;
    MaskedEditText met_cpf;
    TextInputLayout layout_cpf;
    Button add, finalizar;
    ListView ltv_livros_emprestimo;

    IOnEmprestimoController emprestimoController;
    ArrayAdapter<Livro> adapter_livro;
    ArrayList<Livro> livros_emprestimo = new ArrayList<>();

    private View view;

    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 15){

                        //PEGA A INTEÇÃO DE RETORNO
                        Intent i = result.getData();

                        //PEGA OS DADOS
                        Livro livro = new Livro();
                        livro.setCodigo(i.getStringExtra("codigo"));
                        livro.setNome(i.getStringExtra("nome"));

                        //ADICIONA O LIVRO NA LISTA
                        adicionarLivro(livro);
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

                    //LIMPAR SELEÇÃO DE ERROR
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
                //INTENÇÃO
                Intent intent = new Intent(view.getContext(), TelaExibirConsultaLivro.class);

                //CHAMA O INTENT ESPERANDO O RESULTADO
                activityResultLauncher.launch(
                        intent
                );
            }
        });

        ltv_livros_emprestimo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //REMOVER ITEM
                removerItem(position);
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
                        livros_emprestimo
                );
            }
        });

        //SETA O ADAPTER NO LISTVIEW
        adapter_livro = new ArrayAdapter<Livro>(getContext(), android.R.layout.simple_list_item_1, livros_emprestimo);
        ltv_livros_emprestimo.setAdapter(adapter_livro);

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

    public void adicionarLivro(Livro livro){

        if (!livros_emprestimo.contains(livro)) {
            //MENSAGEM
            showMensagem("LIVRO ADICIONADO COM SUCESSO");

            //ADICIONA O ARRAYLIST NO ADAPTER
            livros_emprestimo.add(livro);

            //VERIFICA QUANTIDADE
            verificaQuantidade();

        } else {
            //MENSAGEM
            showMensagem("LIVRO JÁ ADICIONADO");
        }

    }

    public void verificaQuantidade(){
        if(livros_emprestimo.size() < 3){
            //HABILITA O BOTAO
            add.setEnabled(true);
        }
        else{
            //DESABILITA O BOTAO
            add.setEnabled(false);
        }

        //EXIBE A QUANTIDADE DE LIVROS
        atualizarQuantidadeLivros();
    }

    public void removerItem(int position){
        //CRIAR O DIALOGO DE PERGUNTA
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("REMOVER");
        builder.setMessage("Retirar " + livros_emprestimo.get(position) + " do Empréstimo?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                //REMOVER O ITEM
                livros_emprestimo.remove(position);

                //ATUALIZA O ADAPTER
                ltv_livros_emprestimo.setAdapter(adapter_livro);

                //VERIFICAR OS CAMPOS
                verificaQuantidade();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setTextBuscando(TextView textView){
        textView.setText("Buscando ....");
    }

    public void atualizarQuantidadeLivros(){

        //ATUALIZA A QUANTIDADE DE LIVROS
        txt_contador.setText(
                livros_emprestimo.size() + "/3 Livros"
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
        layout_cpf.setError("CPF INVÁLIDO");
    }

    @Override
    public void limparCampos() {

    }

    @Override
    public void limparMensagemBuscando() {
        //LIMPA A MENSAGEM
        txt_nome_cliente.setText("");
    }
}