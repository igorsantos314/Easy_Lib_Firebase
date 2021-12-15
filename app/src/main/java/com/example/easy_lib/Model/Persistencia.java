package com.example.easy_lib.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Persistencia extends SQLiteOpenHelper {

    static final int VERSAO_BANCO = 1;
    static final String NOME_BANCO = "db_easy_lib";

    public static Persistencia getInstance(Context context){
        return new Persistencia(context);
    }

    public Persistencia(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_TABLE_USUARIO = "CREATE TABLE " + IUsuarioDAO.TABELA_USUARIO + "( "
                + IUsuarioDAO.COLUNA_CPF + " STRING PRIMARY KEY, "
                + IUsuarioDAO.COLUNA_NOME + " STRING, "
                + IUsuarioDAO.COLUNA_DATA_NASCIMENTO + " STRING, "
                + IUsuarioDAO.COLUNA_TELEFONE + " STRING, "
                + IUsuarioDAO.COLUNA_CIDADE + " STRING, "
                + IUsuarioDAO.COLUNA_BAIRRO + " STRING, "
                + IUsuarioDAO.COLUNA_RUA + " STRING, "
                + IUsuarioDAO.COLUNA_NUMERO_COMPLETO + " STRING, "
                + IUsuarioDAO.COLUNA_LOGIN + " STRING, "
                + IUsuarioDAO.COLUNA_SENHA + " STRING, "
                + IUsuarioDAO.COLUNA_CARGO + " STRING );";

        Log.d("USUARIO_DAO", "onCreate: " + QUERY_CREATE_TABLE_USUARIO);

        //CRIAR TABELA USUARIO
        db.execSQL(QUERY_CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
