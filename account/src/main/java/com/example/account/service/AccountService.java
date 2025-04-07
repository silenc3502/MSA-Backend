package com.example.account.service;

import com.example.account.controller.request.AccountRequest;
import com.example.account.controller.response.AccountResponse;

public interface AccountService {
    AccountResponse createIfNotExists(AccountRequest request);
}
