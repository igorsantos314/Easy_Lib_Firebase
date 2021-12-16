package com.example.easy_lib.ui.exibirConsultaLivro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easy_lib.Model.Livro;
import com.example.easy_lib.R;

import java.util.List;

public class consultaLivroListAdapter extends ArrayAdapter<Livro> {

    Context mContext;
    int mResource;

    public consultaLivroListAdapter(@NonNull Context context, int resource, @NonNull List<Livro> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LIVRO
        Livro livro = getItem(position);

        //FAZ A CONVERSÃO DE LAYOUT
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //COMPONENTES
        TextView txt_nome = (TextView) convertView.findViewById(R.id.titulo);
        TextView txt_codigo = (TextView) convertView.findViewById(R.id.codigo);
        TextView txt_autor = (TextView) convertView.findViewById(R.id.autor);

        txt_nome.setText(livro.getNome().toUpperCase());
        txt_codigo.setText(livro.getCodigo());
        txt_autor.setText(livro.getAutor());

        return convertView;
    }

}
