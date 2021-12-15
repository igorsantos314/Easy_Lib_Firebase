package com.example.easy_lib.ui.consultarLivro;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easy_lib.R;

public class FragmentConsultarLivro extends Fragment {

    private FragmentConsultarLivroViewModel mViewModel;

    public static FragmentConsultarLivro newInstance() {
        return new FragmentConsultarLivro();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_livro, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentConsultarLivroViewModel.class);
        // TODO: Use the ViewModel
    }

}