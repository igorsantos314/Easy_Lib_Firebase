package com.example.easy_lib.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements ILivroDAO {

    Context mcontext;

    public LivroDAO(Context context){
       mcontext = context;


    }

    @Override
    public void insertLivro(Livro livro) {
        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        ContentValues values = new ContentValues();

        // values.put(COLUNA_CODIGO, livro.getCodigo());
        values.put(COLUNA_NOME, livro.getNome());
        values.put(COLUNA_ANO, livro.getAno());
        values.put(COLUNA_AUTOR, livro.getAutor());
        values.put(COLUNA_EDITORA, livro.getEditora());
        values.put(COLUNA_GENERO, livro.getGenero());
        values.put(COLUNA_QUANTIDADE, livro.getQuantidade());
        values.put(COLUNA_PRATELEIRA, livro.getPrateleira());
        values.put(COLUNA_ESTANTE, livro.getEstante());

        db.insert(TABELA_LIVRO, null, values);
        db.close();



    }

    @Override
    public void updateLivro(Livro livro) {
        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        ContentValues values = new ContentValues();

        // values.put(COLUNA_CODIGO, livro.getCodigo());
        values.put(COLUNA_NOME, livro.getNome());
        values.put(COLUNA_ANO, livro.getAno());
        values.put(COLUNA_AUTOR, livro.getAutor());
        values.put(COLUNA_EDITORA, livro.getEditora());
        values.put(COLUNA_GENERO, livro.getGenero());
        values.put(COLUNA_QUANTIDADE, livro.getQuantidade());
        values.put(COLUNA_PRATELEIRA, livro.getPrateleira());
        values.put(COLUNA_ESTANTE, livro.getEstante());

        db.update(
                TABELA_LIVRO,
                values,
                COLUNA_CODIGO + " = ?",
                new String[]{String.valueOf(livro.getCodigo())}
        );

        db.close();


    }

    @Override
    public void deleteLivro(int codigo) {
        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();
        db.delete(
                TABELA_LIVRO,
                COLUNA_CODIGO + " = ?",
                new String[]{String.valueOf(codigo)}
        );

        db.close();

    }



    @Override
    public Livro getLivro(int codigo) {

        Livro livro = null;

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        String QUERY_LIVRO_NOME = "SELECT * FROM " + TABELA_LIVRO + " WHERE " + COLUNA_CODIGO + " = " + codigo + ";";

        Cursor cursor = db.rawQuery(QUERY_LIVRO_NOME, null);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {

                    livro =
                            new Livro(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getInt(2),
                                    cursor.getString(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(6),
                                    cursor.getInt(7),
                                    cursor.getString(8)

                            );

            }
        }

        return livro;

    }

    @Override
    public List<Livro> getLivroNome(String nome) {

        List<Livro> livros = new ArrayList<Livro>();

        String QUERY_CONSULTA_LIVRO_NOME = "SELECT * FROM " + TABELA_LIVRO + " WHERE " + COLUNA_NOME + " LIKE '%" + nome + "%'";

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_CONSULTA_LIVRO_NOME, null);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    livros.add(
                            new Livro(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getInt(2),
                                    cursor.getString(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(6),
                                    cursor.getInt(7),
                                    cursor.getString(8)
                            )
                    );
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return livros;

    }



    @Override
    public List<Livro> getTodosLivros(){

        List<Livro> livros = new ArrayList<Livro>();

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_TODOS_LIVROS, null);

        if(cursor.moveToFirst()){
            do{
                livros.add(
                        new Livro(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getInt(7),
                                cursor.getString(8)
                        )
                );
            }while (cursor.moveToNext());
        }

        db.close();

        Log.d("ERRO_DETECTADO", "getTodosLivros: " + cursor.getCount());

        return livros;
    }

    @Override
    public int getQuantidadeLivros() {


        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_TOTAL_LIVROS, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        //FECHAR CONEXAO
        db.close();

        return cursor.getInt(0);

    }


}



