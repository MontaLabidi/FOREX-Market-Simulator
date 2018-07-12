package com.fms.Operation;

import com.fms.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

@EnableAutoConfiguration
@RestController
@RequestMapping({"/api"})
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserService userService;


    @PostMapping(path = {"/user={user_id}/currency={currency_id}/operation"})
    public Operation create(@RequestBody Operation operation, @PathVariable("user_id") int user_id, @PathVariable("currency_id") int currency_id) {

        return operationService.create(operation, user_id, currency_id);
    }

    @GetMapping(path = {"/operation={id}"})
    public Operation findById(@PathVariable("id") int id, @PathVariable("user_id") int user_id) throws Exception {

        return operationService.findById(id).orElseThrow(() -> new EntityNotFoundException("Operation Not Found !"));


    }

    @GetMapping(path = {"/user={user_id}/operations"})
    public List<Operation> findByUserId(@PathVariable("user_id") int user_id) {
        return operationService.findByUserId(user_id);
    }

    @DeleteMapping(path = {"/operation={id}"})
    public Operation delete(@PathVariable("id") int id, @PathVariable("user_id") int user_id) {
        Operation operation = operationService.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Operation Not Found !"));
        if (operation.getUser().getId() != user_id)
            throw new BadRequestException("Bad request !");

        return operationService.delete(id);
    }

    @DeleteMapping(path = {"/user={user_id}/operations"})
    public ResponseEntity<?> deleteAllByUser(@PathVariable("user_id") int user_id) {
        List<Operation> operations = operationService.findByUserId(user_id);
        operationService.deleteAllByUser(operations);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @GetMapping(path = {"/operations"})
    public List<Operation> findAll() {
        return operationService.findAll();

    }

    @DeleteMapping(path = {"/operation"})
    public void DeleteAll() {
        operationService.deleteAll();
        return;
    }
}
