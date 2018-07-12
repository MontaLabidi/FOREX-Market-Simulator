package com.fms.Operation;

import com.fms.Currency.Currency;
import com.fms.user.User;

import javax.persistence.*;

@Entity
@Table
public class Operation {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //the user who performed the operation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private double quote; // USD/EUR quote of .91 means that you’ll receive 0.91 euros for every US dollar you sell

    private String operation; //either S(sell) or B (buy) 

    private double amount;

    private double price;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getQuote() {
        return quote;
    }

    public void setQuote(double quote) {
        this.quote = quote;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
    
    