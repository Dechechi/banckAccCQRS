package com.springbank.bankacc.query.api.queries;

import com.springbank.bankacc.query.api.enums.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery {

    private EqualityType equalityType;
    private double balance;

}
