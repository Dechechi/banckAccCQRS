package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dto.AccountLookuResponse;
import com.springbank.bankacc.query.api.enums.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookuResponse findAccountById(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        var response = bankAccount.isPresent()
                ? new AccountLookuResponse("Bank Account successfuly returned", bankAccount.get())
                : new AccountLookuResponse("No Bank Account found for id - " + query.getId());

        return response;
    }

    @QueryHandler
    @Override
    public AccountLookuResponse findAccountByHolderId(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolderId(query.getAccountHolderId());
        var response = bankAccount.isPresent()
                ? new AccountLookuResponse("Bank Account successfuly returned", bankAccount.get())
                : new AccountLookuResponse("No Bank Account found for Holder id - " + query.getAccountHolderId());

        return response;
    }

    @QueryHandler
    @Override
    public AccountLookuResponse findAllAccounts(FindAllAccountsQuery query) {
        var bankAccountIterator = accountRepository.findAll();

        if(!bankAccountIterator.iterator().hasNext())
            return new AccountLookuResponse("No Bank Accounts were found");

        var bankAccounts = new ArrayList<BankAccount>();
        bankAccountIterator.forEach(i -> bankAccounts.add(i));
        var count = bankAccounts.size();

        return new AccountLookuResponse("Successfuly returned " + count + " bank account(s)", bankAccounts);
    }

    @QueryHandler
    @Override
    public AccountLookuResponse findAccountsWithBalance(FindAccountWithBalanceQuery query) {
        var bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());

        var response = bankAccounts != null && bankAccounts.size() > 0
                ? new AccountLookuResponse("Succesfully returned " + bankAccounts.size() + " bank account(s)", bankAccounts)
                : new AccountLookuResponse("No Bank Accounts were found");

        return response;
    }
}
