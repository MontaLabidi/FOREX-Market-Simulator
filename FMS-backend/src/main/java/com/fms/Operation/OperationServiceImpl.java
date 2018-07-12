package com.fms.Operation;

import com.fms.Currency.Currency;
import com.fms.Currency.CurrencyRepository;
import com.fms.Trade.Trade;
import com.fms.Trade.TradeRepository;
import com.fms.Wallet.Wallet;
import com.fms.Wallet.WalletRepository;
import com.fms.user.User;
import com.fms.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Operation create(Operation operation, int user_id, int currency_id) {

        User user = userRepository.findById(user_id).orElseThrow(()
                -> new EntityNotFoundException("User Not Found !"));
        Currency currency = currencyRepository.findById(currency_id).orElseThrow(()
                -> new EntityNotFoundException("Currency Not Found !"));
        System.out.println("*************************" + user.getId());
        Wallet wallet = walletRepository.findByuser(user).orElseThrow(()
                -> new EntityNotFoundException("Wallet Not Found !"));

        operation.setCurrency(currency);
        operation.setUser(user);
        operation.setQuote(currency.getQuote());


        double amountUSD;
        double onePointValue;
        if (operation.getOperation().equals("BUY")) {
            if (currency.getQuoteCurrency().equals("USD"))
                amountUSD = operation.getAmount() * currency.getQuote();
            else if (currency.getBaseCurrency().equals("USD"))
                amountUSD = operation.getAmount();
            else {
                if (currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").isPresent()) {
                    Currency currencyRate = currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").get();
                    amountUSD = operation.getAmount() * currencyRate.getQuote();
                } else {
                    Currency currencyRate = currencyRepository.searchCurrency("USD", currency.getQuoteCurrency()).get();
                    amountUSD = operation.getAmount() / currencyRate.getQuote();
                }
            }
        } else {
            if (currency.getBaseCurrency().equals("USD"))
                amountUSD = operation.getAmount() / currency.getQuote();
            else if (currency.getQuoteCurrency().equals("USD"))
                amountUSD = operation.getAmount();
            else {
                if (currencyRepository.searchCurrency(currency.getBaseCurrency(), "USD").isPresent()) {
                    Currency currencyRate = currencyRepository.searchCurrency(currency.getQuoteCurrency(), "USD").get();
                    amountUSD = operation.getAmount() * currencyRate.getQuote();
                } else {
                    Currency currencyRate = currencyRepository.searchCurrency("USD", currency.getBaseCurrency()).get();
                    amountUSD = operation.getAmount() / currencyRate.getQuote();
                }
            }

        }

        if (wallet.getFreeMargin() < amountUSD)
            throw new BadRequestException("Operation cannot be done: Not enough Balance");
        else {
            wallet.setMargin(wallet.getMargin() + amountUSD / 2);

            if (currency.getBaseCurrency().equals("JPY") || currency.getQuoteCurrency().equals("JPY"))
                onePointValue = operation.getAmount() * 0.01;
            else
                onePointValue = operation.getAmount() * 0.0001;


        }
        Trade trade = new Trade();
        trade.setUser(user);
        trade.setCurrency(currency);
        trade.setAmount(operation.getAmount());
        trade.setMargin(amountUSD / 2);
        trade.setPrice(currency.getQuote());
        trade.setType(operation.getOperation());

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
            if (operation.getOperation().equals("SELL"))
                currency.setQuote(currency.getQuote() + 0.01, tradeRepository, walletRepository);
            else
                currency.setQuote(currency.getQuote() - 0.01, tradeRepository, walletRepository);
        } else {
            if (operation.getOperation().equals("SELL"))
                currency.setQuote(currency.getQuote() + 0.00010, tradeRepository, walletRepository);
            else
                currency.setQuote(currency.getQuote() - 0.00010, tradeRepository, walletRepository);
        }
        return operationRepository.save(operation);

    }

    @Override
    public Operation delete(int id) {

        Operation Operation = operationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Operation Not Found !"));
        operationRepository.deleteById(id);
        return Operation;
    }

    @Override
    public List<Operation> findByUserId(int userid) {
        return operationRepository.findByUserId(userid);
    }

    @Override
    public List<Operation> findAll() {
        List<Operation> operations = new ArrayList<>();
        operationRepository.findAll().forEach(operations::add);
        return operations;
    }

    @Override
    public void deleteAll() {
        operationRepository.deleteAll();
        return;
    }

    @Override
    public void deleteAllByUser(List<Operation> entities) {
        operationRepository.deleteAll(entities);
        return;
    }

    @Override
    public Optional<Operation> findById(int id) {
        return operationRepository.findById(id);

    }


}