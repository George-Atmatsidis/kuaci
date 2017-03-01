package com.rubahapi.kuaci.pojo;

import java.util.Date;

/**
 * Created by junifar on 2/27/2017.
 */

public class KasBook {
    private Date DateTransaction;
    private String Description;
    private Float Amount;
    private int transactionType;

    public KasBook() {
    }

    public KasBook(Date dateTransaction, String description, float amount, int transactionType) {
        this.DateTransaction = dateTransaction;
        this.Description = description;
        this.Amount = amount;
        this.transactionType = transactionType;
    }

    public Date getDateTransaction() {
        return DateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.DateTransaction = dateTransaction;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        this.Amount = amount;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }
}
