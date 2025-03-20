package com.edacamo.msaccounts.services;

import com.edacamo.msaccounts.domain.entities.Account;
import com.edacamo.msaccounts.domain.repositories.AccountRepository;
import com.edacamo.msaccounts.infrastructure.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Account createCuenta(Account cuenta) {
        if (accountRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            log.error("Cuenta con número {} ya existe", cuenta.getNumeroCuenta());
            throw new ConflictException("Cuenta con número " + cuenta.getNumeroCuenta() + " ya existe");
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllCuentas() {
        return (List<Account>) this.accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getCuentaById(Long id) {
        return this.accountRepository.findById(id);
    }

    @Override
    @Transactional()
    public Account updateCuenta(Long id, Account cuentaDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Cuenta no encontrada", 1));

        account.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
        account.setTipo(cuentaDetails.getTipo());
        account.setSaldoInicial(cuentaDetails.getSaldoInicial());
        account.setEstado(cuentaDetails.getEstado());
        account.setClient(cuentaDetails.getClient());

        return accountRepository.save(account);
    }

    @Override
    @Transactional()
    public void deleteCuenta(Long id) {

    }
}
