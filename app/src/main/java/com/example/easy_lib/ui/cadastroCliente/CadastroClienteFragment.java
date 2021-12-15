package com.example.easy_lib.ui.cadastroCliente;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easy_lib.Controller.IOnClienteController;
import com.example.easy_lib.Controller.OnClienteController;
import com.example.easy_lib.R;
import com.example.easy_lib.View.IClienteView;
import com.example.easy_lib.databinding.FragmentCadastroClienteBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.Query;
import com.vicmikhailau.maskededittext.MaskedEditText;

public class CadastroClienteFragment extends Fragment implements IClienteView {

    private CadastroClienteViewModel mViewModel;
    private FragmentCadastroClienteBinding binding;

    MaskedEditText txt_cpf, txt_data_nascimento, txt_telefone;
    EditText txt_nome,txt_rua, txt_numero, txt_bairro, txt_cidade;
    Button btn_salvar;

    TextInputLayout layout_cpf;

    private IOnClienteController clienteController;
    private View view;

    private String TAG = "CADASTRO_CLIENTE";

    public static CadastroClienteFragment newInstance() {
        return new CadastroClienteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //COMPONENTES
        binding = FragmentCadastroClienteBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        clienteController = new OnClienteController(this);

        txt_cpf = view.findViewById(R.id.txt_Cpf);
        layout_cpf = view.findViewById(R.id.textInputCpfLayout);

        txt_nome = view.findViewById(R.id.txt_Nome);
        txt_data_nascimento = view.findViewById(R.id.txt_Data_Nascimento);
        txt_telefone = view.findViewById(R.id.txt_Telefone);
        txt_rua = view.findViewById(R.id.txt_Rua);
        txt_numero = view.findViewById(R.id.txt_Numero);
        txt_bairro = view.findViewById(R.id.txt_Bairro);
        txt_cidade = view.findViewById(R.id.txt_Cidade);

        btn_salvar = view.findViewById(R.id.btnSalvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(layout_cpf.getError() != MSG_CLIENTE_EXIST) {
                    clienteController.insertCliente(
                            txt_nome.getText().toString(),
                            txt_cpf.getText().toString(),
                            txt_data_nascimento.getText().toString(),
                            txt_telefone.getText().toString(),
                            txt_rua.getText().toString(),
                            txt_numero.getText().toString(),
                            txt_bairro.getText().toString(),
                            txt_cidade.getText().toString()
                    );
                }
                else{
                    showMensagem(MSG_CPF_INVALIDO);
                }
            }
        });

        txt_cpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 14){
                    //VERIFICA SE O CLIENTE EXISTE E ATIAVA O ERROR DO LAYOUT
                    clienteController.onExistCliente("" + s);
                }
                else{
                    layout_cpf.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //RETORNO PADRÃO
        return view;
    }

    @Override
    public void exibirClientes(Query query) {

    }

    @Override
    public void exibirCliente(Query query) {

    }

    @Override
    public void errorClienteExist() {
        //EVENTO
        layout_cpf.setError(MSG_CLIENTE_EXIST);
    }

    @Override
    public void showMensagem(String mensagem) {
        Snackbar snackbar = Snackbar.make(view.findViewById(R.id.id_fragment_cadastro_cliente), mensagem, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

    @Override
    public void limparCampos() {
        txt_nome.setText("");
        txt_cpf.setText("");
        txt_data_nascimento.setText("");
        txt_telefone.setText("");
        txt_rua.setText("");
        txt_numero.setText("");
        txt_bairro.setText("");
        txt_cidade.setText("");
    }

}