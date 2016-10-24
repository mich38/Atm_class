package com.tom.atm;

/**
 * Created by Administrator on 2016/10/20.
 */

public class Transaction {
    String account;
    String date;
    int amount;
    int type;

    public Transaction(){

    }

    public Transaction(String date, int amount, int type) {
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return date + "/" + amount + "/" + type;
    }
}
