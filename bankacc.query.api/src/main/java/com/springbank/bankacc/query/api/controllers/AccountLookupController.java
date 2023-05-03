package com.springbank.bankacc.query.api.controllers;

import com.springbank.bankacc.query.api.dto.AccountLookuResponse;
import com.springbank.bankacc.query.api.enums.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public AccountLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookuResponse> getAllAccounts(){
        try {
            var query = new FindAllAccountsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookuResponse.class)).join();

            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get all accounts request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new AccountLookuResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookuResponse> getAccountById(@PathVariable(value = "id") String id){
        try {
            var query = new FindAccountByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookuResponse.class)).join();

            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get account by id request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new AccountLookuResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolderId/{accountHolderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookuResponse> getAccountByHolderId(@PathVariable(value = "accountHolderId") String accountHolderId){
        try {
            var query = new FindAccountByHolderQuery(accountHolderId);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookuResponse.class)).join();

            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get account by holder id request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new AccountLookuResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookuResponse> getAccountByBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                     @PathVariable(value = "balance") double balance){
        try {
            var query = new FindAccountWithBalanceQuery(equalityType, balance);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookuResponse.class)).join();

            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get accounts with balance request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new AccountLookuResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
