package com.springbank.bankacc.cmd.api.dto;

import com.springbank.bankacc.core.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String message) {
        super(message);
    }

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
