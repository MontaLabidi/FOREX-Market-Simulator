package com.fms.Currency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

    @Query("SELECT c FROM Currency c WHERE c.baseCurrency = :baseCurrency and c.quoteCurrency = :quoteCurrency")
    public Optional<Currency> searchCurrency(@Param("baseCurrency") String baseCurrency, @Param("quoteCurrency") String quoteCurrency);

}
