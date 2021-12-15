package com.example.easy_lib.ui.cadastroEmprestimo;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_lib.Controller.IOnEmprestimoController;
import com.example.easy_lib.Controller.OnEmprestimoController;
import com.example.easy_lib.Model.Cliente;
import com.example.easy_lib.Model.ClienteFirebaseDAO;
import com.example.easy_lib.Model.Emprestimo;
import com.example.easy_lib.Model.IEmprestimo;
import com.example.easy_lib.R;
import com.example.easy_lib.View.IEmprestimoView;
import com.example.easy_lib.databinding.FragmentCadastroEmprestimoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FragmentCadastoEmprestimo extends Fragment implements IEmprestimoView {

    private FragmentCadastoEmprestimoViewModel mViewModel;
    private FragmentCadastroEmprestimoBinding binding;

    TextView txt_nome_cliente, txt_data_emprestimo, txt_data_devolucao;
    MaskedEditText met_cpf;
    TextInputLayout layout_cpf;

    IOnEmprestimoController emprestimoController;

    private View view;

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

    public void setTextBuscando(TextView textView){
        textView.setText("Buscando ....");
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