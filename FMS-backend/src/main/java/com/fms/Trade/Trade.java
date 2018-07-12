package com.fms.Trade;

import com.fms.Currency.Currency;
import com.fms.Wallet.Wallet;
import com.fms.Wallet.WalletRepository;
import com.fms.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Trade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //the user who performed the operation

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();

    private double price; // USD/EUR quote of .91 means that you’ll receive 0.91 euros for every US dollar you sell

    private String type; //either S(sell) or B (buy) 

    private double amount;

    private double margin;

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    private double onePipValue;

    private double profit;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit, WalletRepository walletRepository) {
        System.out.println("*************************" + profit);
        Wallet wallet = this.user.getWallet();
        double profitDiference = profit - this.profit;
        wallet.setProfit(wallet.getProfit() + profitDiference);
        wallet.setEquity(wallet.getEquity() + profitDiference);
        wallet.setFreeMargin(wallet.getEquity() - wallet.getMargin());
        walletRepository.save(wallet);
        this.profit = profit;
    }

    public double getOnePipValue() {
        return onePipValue;
    }

    public void setOnePipValue(double onePipValue) {
        this.onePipValue = onePipValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

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

    public void setProfit(double profit) {
        this.profit = profit;
    }


}
    
    