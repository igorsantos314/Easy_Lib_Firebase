package com.example.easy_lib.ui.exibirConsultaLivro;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easy_lib.R;

public class FragmentExibirConsultaLivro extends Fragment {

    private FragmentExibirConsultaLivroViewModel mViewModel;

    public static FragmentExibirConsultaLivro newInstance() {
        return new FragmentExibirConsultaLivro();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exibir_consulta_livro, container, false);
    }

}