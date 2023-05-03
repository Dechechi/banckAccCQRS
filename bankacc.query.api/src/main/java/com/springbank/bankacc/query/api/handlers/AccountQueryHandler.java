package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.query.api.dto.AccountLookuResponse;
import com.springbank.bankacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {

    AccountLookuResponse findAccountById(FindAccountByIdQuery query);
    AccountLookuResponse findAccountByHolderId(FindAccountByHolderQuery query);
    AccountLookuResponse findAllAccounts(FindAllAccountsQuery query);
    AccountLookuResponse findAccountsWithBalance(FindAccountWithBalanceQuery query);

}
