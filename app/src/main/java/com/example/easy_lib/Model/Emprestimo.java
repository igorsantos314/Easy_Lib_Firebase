package com.example.easy_lib.Model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class Emprestimo implements IEmprestimo {

    private String uid;
    private String dataEmprestimo;
    private String dataDevolucao;
    private String status;

    private String cpfCliente;
    private String nomeCliente;

    private ArrayList<ItemEmprestimo> livros_emprestimo;

    public Emprestimo() {
    }

    public Emprestimo(String dataEmprestimo, String dataDevolucao, String status, String cpfCliente, String nomeCliente, ArrayList<ItemEmprestimo> livros_emprestimo) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.livros_emprestimo = livros_emprestimo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public ArrayList<ItemEmprestimo> getLivros_emprestimo() {
        return livros_emprestimo;
    }

    public void setLivros_emprestimo(ArrayList<ItemEmprestimo> livros_emprestimo) {
        this.livros_emprestimo = livros_emprestimo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public double calcularMulta() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataDevolucaoDate = LocalDate.parse(dataDevolucao);

        //CALCULA A TAXA CASO SEJA DIFERENTE DA ATUAL
        if(dataAtual.equals(dataDevolucaoDate) || dataDevolucaoDate.isBefore(dataAtual))
            return 0;

        //QUANTIDADE DE DIAS ENTRE AS DATAS
        long noOfDaysBetween = ChronoUnit.DAYS.between(dataAtual, dataDevolucaoDate);

        //RETORNA A MULTA
        return TAXA_ATRASO + (noOfDaysBetween * MULTA_POR_DIA);
    }
}