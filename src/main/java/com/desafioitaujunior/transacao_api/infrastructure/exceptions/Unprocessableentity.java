package com.desafioitaujunior.transacao_api.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class Unprocessableentity extends RuntimeException {
    public Unprocessableentity(String message) {
        super(message);
    }
}
