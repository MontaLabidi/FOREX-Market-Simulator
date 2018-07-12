package com.fms.Trade;

import com.fms.Currency.Currency;
import com.fms.Currency.CurrencyRepository;
import com.fms.Wallet.Wallet;
import com.fms.Wallet.WalletRepository;
import com.fms.user.User;
import com.fms.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TradeRepository tradeRepository;


    @Override
    public ResponseEntity<?> create(Trade trade, int user_id, int currency_id) {
        User user = userRepository.findById(user_id).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));
        Currency currency = currencyRepository.findById(currency_id).orElseThrow(()
                -> new EntityNotFoundException("Currency Not Found !"));
        //System.out.println("*************************"+user.getId());
        Wallet wallet = walletRepository.findByuser(user).orElseThrow(()
                -> new EntityNotFoundException("Wallet Not Found !"));

        trade.setCurrency(currency);
        trade.setUser(user);
        trade.setPrice(currency.getQuote());


        double amountUSD;
        double onePointValue;
        if (trade.getType().equals("BUY")) {
            if (currency.getQuoteCurrency().equals("USD"))
                amountUSD = trade.getAmount() * currency.getQuote();
            else if (currency.getBaseCurrency().equals("USD"))
                amountUSD = trade.getAmount();
            else {
                if (currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").isPresent()) {
                    Currency currencyRate = currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").get();
                    amountUSD = trade.getAmount() * currencyRate.getQuote();
                } else {
                    Currency currencyRate = currencyRepository.searchCurrency("USD", currency.getQuoteCurrency()).get();
                    amountUSD = trade.getAmount() / currencyRate.getQuote();
                }
            }
        } else {
            if (currency.getBaseCurrency().equals("USD"))
                amountUSD = trade.getAmount() / currency.getQuote();
            else if (currency.getQuoteCurrency().equals("USD"))
                amountUSD = trade.getAmount();
            else {
                if (currencyRepository.searchCurrency(currency.getBaseCurrency(), "USD").isPresent()) {
                    Currency currencyRate = currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").get();
                    amountUSD = trade.getAmount() * currencyRate.getQuote();
                } else {
                    Currency currencyRate = currencyRepository.searchCurrency("USD", currency.getBaseCurrency()).get();
                    amountUSD = trade.getAmount() / currencyRate.getQuote();
                }
            }

        }

        if (wallet.getFreeMargin() < amountUSD)
            throw new BadRequestException("Operation cannot be done: Not enough Balance");
        else {
            wallet.setMargin(wallet.getMargin() + amountUSD / 2);

            if (currency.getBaseCurrency().equals("JPY") || currency.getQuoteCurrency().equals("JPY"))
                onePointValue = trade.getAmount() * 0.01;
            else
                onePointValue = trade.getAmount() * 0.0001;


        }

        trade.setMargin(amountUSD / 2);


        if (currency.getQuoteCurrency().equals("USD"))
            trade.setOnePipValue(onePointValue);
        else if (currency.getBaseCurrency().equals("USD"))
            trade.setOnePipValue(onePointValue / currency.getQuote());
        else {
            if (currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").isPresent()) {
                Currency currencyRate = currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").get();
                trade.setOnePipValue(onePointValue * currencyRate.getQuote());
            } else if (currencyRepository.searchCurrency("USD", currency.getQuoteCurrency()).isPresent()) {
                Currency currencyRate = currencyRepository.searchCurrency("USD", currency.getQuoteCurrency()).get();
                trade.setOnePipValue(onePointValue / currencyRate.getQuote());
            }
        }

        tradeRepository.save(trade);

        if (currency.getBaseCurrency().equals("JPY") || currency.getQuoteCurrency().equals("JPY")) {
            if (trade.getType().equals("SELL"))
                currency.setQuote(currency.getQuote() + 0.01, tradeRepository, walletRepository);
            else
                currency.setQuote(currency.getQuote() - 0.01, tradeRepository, walletRepository);
        } else {
            if (trade.getType().equals("SELL"))
                currency.setQuote(currency.getQuote() + 0.00010, tradeRepository, walletRepository);
            else
                currency.setQuote(currency.getQuote() - 0.00010, tradeRepository, walletRepository);
        }
        currencyRepository.save(currency);
        return new ResponseEntity<>("{\"message\": \"Trade made !\"}", HttpStatus.OK);

    }


    @Override
    public List<Trade> closeTrade(int id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Trade Not Found !"));
        User user = trade.getUser();
        Wallet wallet = user.getWallet();
        System.out.println("*****************************" + wallet.getBalance());
        wallet.setBalance(wallet.getBalance() + trade.getProfit());
        wallet.setProfit(wallet.getProfit() - trade.getProfit());
        wallet.setFreeMargin(wallet.getFreeMargin() + trade.getMargin());
        wallet.setMargin(wallet.getMargin() - trade.getMargin());
        System.out.println("*****************************bfro walletsave");
        walletRepository.save(wallet);
        System.out.println("*****************************after walletsave");
        tradeRepository.deleteById(id);
        return user.getTrades();
    }

    @Override
    public Trade delete(int id) {

        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trade Not Found !"));
        tradeRepository.deleteById(id);
        return trade;
    }

    @Override
    public List<Trade> findAll() {
        List<Trade> trades = new ArrayList<>();
        tradeRepository.findAll().forEach(trades::add);
        return trades;
    }

    @Override
    public void deleteAll() {
        tradeRepository.deleteAll();
        return;
    }

    @Override
    public Optional<Trade> findById(int id) {
        return tradeRepository.findById(id);

    }


}