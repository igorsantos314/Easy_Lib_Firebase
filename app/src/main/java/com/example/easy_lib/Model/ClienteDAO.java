package com.example.easy_lib.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO {


    Context mcontext;

    public ClienteDAO(Context context) {
        mcontext = context;

    }

    @Override
    public void insertCliente(Cliente cliente) {
        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_CPF, cliente.getCpf());
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, cliente.getData_nascimento().toString());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_CIDADE, cliente.getCidade());
        values.put(COLUNA_BAIRRO, cliente.getBairro());
        values.put(COLUNA_RUA, cliente.getRua());
        values.put(COLUNA_NUMERO_COMPLEMENTO, cliente.getNumero_complemento());

        db.insert(TABELA_CLIENTE, null, values);
        db.close();


        // String cpf, String nome, Date data_nascimento, String telefone, String cidade, String bairro, String rua, String numero_complemento
    }

    @Override
    public void updateCliente(Cliente cliente) {

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_CPF, cliente.getCpf());
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, cliente.getData_nascimento().toString());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_CIDADE, cliente.getCidade());
        values.put(COLUNA_BAIRRO, cliente.getBairro());
        values.put(COLUNA_RUA, cliente.getRua());
        values.put(COLUNA_NUMERO_COMPLEMENTO, cliente.getNumero_complemento());

        db.update(
                TABELA_CLIENTE,
                values,
                COLUNA_CPF + " = ?",
                new String[]{String.valueOf(cliente.getCpf())}
        );

        db.close();
    }

    @Override
    public void deleteCliente(String cpf) {

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();
        db.delete(
                TABELA_CLIENTE,
                COLUNA_CPF + " = ?",
                new String[]{String.valueOf(cpf)}
        );

        db.close();

    }

    @Override
    public List<Cliente> getTodosClientes() {
        List<Cliente> clientes = new ArrayList<Cliente>();

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_TODOS_CLIENTES, null);

        if (cursor.moveToFirst()) {
            do {
                clientes.add(
                        new Cliente(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        )
                );
            } while (cursor.moveToNext());

        }
        db.close();

        return clientes;

    }

    @Override
    public Cliente getCliente(String cpf){
        Cliente cliente = null;

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        String QUERY_CLIENTE_CPF = "SELECT * FROM " + TABELA_CLIENTE + " WHERE " + COLUNA_CPF + " = " + cpf + ";";

        Cursor cursor = db.rawQuery(QUERY_CLIENTE_CPF, null);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {

                cliente =
                        new Cliente(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)

                        );

            }
        }

        return cliente;
    }

    @Override
    public List<Cliente> getClientePorNome (String nome){
        List<Cliente> clientes = new ArrayList<Cliente>();

        String QUERY_CONSULTA_CLIENTE_NOME = "SELECT * FROM " + TABELA_CLIENTE + " WHERE " + COLUNA_NOME + " LIKE '%" + nome + "%'";

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_CONSULTA_CLIENTE_NOME, null);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    clientes.add(
                            new Cliente(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(6),
                                    cursor.getString(7)
                            )
                    );
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return clientes;

    }

    public int getQuantidadeClientes(){

        SQLiteDatabase db = Persistencia.getInstance(mcontext).getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_QUANTIDADE_CLIENTES, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        //FECHAR CONEXAO
        db.close();

        return cursor.getInt(0);

    }


}
