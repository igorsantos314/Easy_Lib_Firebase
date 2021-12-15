package com.example.easy_lib.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {

    Context mContext;

    public UsuarioDAO(@Nullable Context context) {
        mContext = context;
    }

    @Override
    public void insertUsuario(Usuario usuario) {

        SQLiteDatabase database = Persistencia.getInstance(mContext).getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.d("USUARIO_DAO", "insertUsuario: " + usuario);

        values.put(COLUNA_CPF, usuario.getCpf());
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, usuario.getData_nascimento());
        values.put(COLUNA_TELEFONE, usuario.getTelefone());
        values.put(COLUNA_CIDADE, usuario.getCidade());
        values.put(COLUNA_BAIRRO, usuario.getBairro());
        values.put(COLUNA_RUA, usuario.getRua());
        values.put(COLUNA_NUMERO_COMPLETO, usuario.getNumero_completo());
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
        values.put(COLUNA_CARGO, usuario.getCargo());

        //INSERIR USUARIO NO DATABASE
        database.insert(
                TABELA_USUARIO,
                null,
                values
        );

        //FECHA A CONEXÃO
        database.close();
    }

    @Override
    public void deleteUsuario(String cpf) {
        SQLiteDatabase database = Persistencia.getInstance(mContext).getWritableDatabase();

        //EXCLUI O USUARIO
        database.delete(
                TABELA_USUARIO,
                COLUNA_CPF + " = ?",
                new String[]{cpf}
        );

        database.close();
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        SQLiteDatabase database = Persistencia.getInstance(mContext).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, usuario.getData_nascimento());
        values.put(COLUNA_TELEFONE, usuario.getTelefone());
        values.put(COLUNA_CIDADE, usuario.getCidade());
        values.put(COLUNA_BAIRRO, usuario.getBairro());
        values.put(COLUNA_RUA, usuario.getRua());
        values.put(COLUNA_NUMERO_COMPLETO, usuario.getNumero_completo());
        values.put(COLUNA_LOGIN, usuario.getLogin());
        values.put(COLUNA_SENHA, usuario.getSenha());
        values.put(COLUNA_CARGO, usuario.getCargo());

        //INSERIR USUARIO NO DATABASE
        database.update(
                TABELA_USUARIO,
                values,
                COLUNA_CPF + " = ?",
                new String[]{usuario.getCpf()}
        );

        //FECHA A CONEXÃO
        database.close();
    }

    @Override
    public Usuario getUsuario(String cpf) {
        return null;
    }

    @Override
    public int validateLogin(String login, String senha) {

        String QUERY_GET_LOGIN_SENHA = "SELECT " + COLUNA_SENHA + " FROM " + TABELA_USUARIO + " WHERE " + COLUNA_LOGIN + " = '" + login + "';";
        Log.d("TESTE_VALIDATE_LOGIN", "validateLogin: " + QUERY_GET_LOGIN_SENHA);

        SQLiteDatabase database = Persistencia.getInstance(mContext).getWritableDatabase();

        Cursor cursor = database.rawQuery(QUERY_GET_LOGIN_SENHA, null);

        if(cursor.getCount() > 0){

            if(cursor.moveToFirst()){
                do{
                    String senha_db = cursor.getString(0);

                    if(!senha_db.equals(senha))
                        //SENHA INCORRETA
                        return 1;

                    //USUÁRIO E SENHA ESTÃO CORRETOS
                    return 2;

                }while (cursor.moveToNext());
            }
        }

        //RETORNA 0 PARA LOGIN NÃO ENCONTRADO
        return 0;

    }

    @Override
    public List<Usuario> getUsuarioPorNome(String nome) {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        String QUERY_CONSULTA_USUARIO_NOME = "SELECT * FROM " + TABELA_USUARIO + " WHERE " + COLUNA_NOME + " LIKE '%" + nome + "%';";

        Log.d("USUARIO_DAO", "getUsuarioPorNome: " + QUERY_CONSULTA_USUARIO_NOME);

        SQLiteDatabase database = Persistencia.getInstance(mContext).getWritableDatabase();

        //INICIALIZA O CURSOR
        Cursor cursor = database.rawQuery(QUERY_CONSULTA_USUARIO_NOME, null);

        if(cursor.getCount() > 0){

            if(cursor.moveToFirst()){
                do{
                    usuarios.add(
                            new Usuario(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(6),
                                    cursor.getString(7),
                                    cursor.getString(8),
                                    cursor.getString(9),
                                    cursor.getString(10)
                            )
                    );
                }while (cursor.moveToNext());
            }

        }

        Log.d("USUARIO_DAO", "getUsuarioPorNome: " + cursor.getCount());

        //FINALIZAR CONEXAO
        database.close();

        //RETORNA A LISTA DE USUARIOS
        return usuarios;
    }

    @Override
    public boolean existCliente(String cpf) {

        //ABRRIR CONEXAO COM O DATA BASE
        SQLiteDatabase db = Persistencia.getInstance(mContext).getWritableDatabase();

        String QUERY_EXIST_CLIENTE = "SELECT " + COLUNA_CPF + " FROM " + TABELA_USUARIO + " WHERE " + COLUNA_CPF + " = '" + cpf + "';";

        Cursor cursor = db.rawQuery(QUERY_EXIST_CLIENTE, null);

        if(cursor.getCount() > 0)
            //EXISTE UM CLIENTE COM ESTE CPF
            return true;

        //NÃO EXISTE CLIENTE COM ESSE CPF
        return false;
    }

    @Override
    public boolean existLogin(String login) {
        //ABRRIR CONEXAO COM O DATA BASE
        SQLiteDatabase db = Persistencia.getInstance(mContext).getWritableDatabase();

        String QUERY_EXIST_LOGIN = "SELECT " + COLUNA_LOGIN + " FROM " + TABELA_USUARIO + " WHERE " + COLUNA_LOGIN + " = '" + login + "';";

        Cursor cursor = db.rawQuery(QUERY_EXIST_LOGIN, null);

        if(cursor.getCount() > 0)
            //EXISTE UM USUARIO COM ESTE LOGIN
            return true;

        //NÃO EXISTE UM USUARIO COM ESSE LOGIN
        return false;
    }

    public Integer getNumeroUsuarios() {
        //ABRRIR CONEXAO COM O DATA BASE
        SQLiteDatabase db = Persistencia.getInstance(mContext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_QUANTIDADE_CLIENTES, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        //FECHAR CONEXAO
        db.close();

        return cursor.getInt(0);
    }

}
