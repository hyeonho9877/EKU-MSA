package com.hyunho9877.auth.service.interfaces;

import com.hyunho9877.auth.dto.AccountDto;

public interface AccountService {
    void register(AccountDto dto);
    void withdraw(String id);
}
