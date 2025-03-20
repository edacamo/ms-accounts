package com.edacamo.msaccounts.infrastructure.controllers;

import com.edacamo.msaccounts.domain.entities.Account;
import com.edacamo.msaccounts.infrastructure.exception.ResponseCode;
import com.edacamo.msaccounts.interfaces.dto.AccountRegisterRequest;
import com.edacamo.msaccounts.interfaces.dto.ResponseGeneric;
import com.edacamo.msaccounts.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("cuentas")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseGeneric<Account>> createAccount(@RequestBody AccountRegisterRequest account) {
        if(account != null) {
            try{
                Account register = this.accountService.createCuenta(account);
                ResponseGeneric<Account> response = ResponseGeneric.success(
                        HttpStatus.OK.value(),
                        ResponseCode.DATA_CREATED,
                        register
                );

                return ResponseEntity.ok(response);

            } catch (Exception ex) {
                // En caso de error, lanzamos una excepción personalizada para que sea capturada por el GlobalExceptionHandler
                log.error("Error al registrar la cuenta: ", ex);
                throw new RuntimeException(ex.getMessage().isEmpty() ? "Error al registrar la cuenta" : ex.getMessage());  // Aquí puedes lanzar una excepción personalizada si lo prefieres
            }
        } else {
            // Si la cuenta es nulo, lanzamos una excepción de validación
            log.error("Cliente nulo recibido");
            throw new IllegalArgumentException("Los datos de la cuenta no puede ser nulo");
        }
    }
}
