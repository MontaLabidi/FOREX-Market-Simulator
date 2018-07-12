package com.fms.Currency;

import com.fms.Trade.TradeRepository;
import com.fms.Wallet.WalletRepository;

import javax.persistence.*;

@Entity
@Table
public class Currency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String baseCurrency; //example EUR

    private String quoteCurrency;//example USD

    private double quote; // USD/EUR quote of .91 means that you’ll receive 0.91 euros for every US dollar you sell

    private double volume;

    private double dayClose;

    private double dayHigh;

    private double dayLow;

    private double dayOpen;


    public double getDayClose() {
        return dayClose;
    }

    public void setDayClose(double dayClose) {
        this.dayClose = dayClose;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(double dayOpen) {
        this.dayOpen = dayOpen;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double day_low) {
        this.dayLow = day_low;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuote() {
        return quote;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String base_currency) {
        this.baseCurrency = base_currency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quote_currency) {
        this.quoteCurrency = quote_currency;
    }

    public void setQuote(double quote, TradeRepository tradeRepository, WalletRepository walletRepository) {

        double change;
        if (this.quote - quote > 0)
            change = 1;
        else
            change = -1;
        tradeRepository.findAll().forEach((trade) -> {
            System.out.println("in for each");
            if (trade.getType().equals("BUY"))
                trade.setProfit(trade.getProfit() - change * trade.getOnePipValue(), walletRepository);
            else {
                trade.setProfit(trade.getProfit() + change * trade.getOnePipValue(), walletRepository);
                System.out.println("in SELL" + change);
            }
            tradeRepository.save(trade);

        });
        this.quote = quote;
    }


}
