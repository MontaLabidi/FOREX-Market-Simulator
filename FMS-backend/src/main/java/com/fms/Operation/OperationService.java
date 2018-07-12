package com.fms.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    Operation create(Operation Operation, int user_id, int currency_id);

    Operation delete(int id);

    List<Operation> findAll();

    Optional<Operation> findById(int id);

    List<Operation> findByUserId(int userid);

    void deleteAll();

    void deleteAllByUser(List<Operation> entities);


}
