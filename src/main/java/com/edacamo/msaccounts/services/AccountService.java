package com.edacamo.msaccounts.services;

import com.edacamo.msaccounts.domain.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public Account createCuenta(Account cuenta);

    public List<Account> getAllCuentas();

    Optional<Account> getCuentaById(Long id);

    public Account updateCuenta(Long id, Account cuentaDetails);

    public void deleteCuenta(Long id);
}
