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
        DateTransaction = dateTransaction;
        Description = description;
        Amount = amount;
        this.transactionType = transactionType;
    }

    public Date getDateTransaction() {
        return DateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        DateTransaction = dateTransaction;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }
}
