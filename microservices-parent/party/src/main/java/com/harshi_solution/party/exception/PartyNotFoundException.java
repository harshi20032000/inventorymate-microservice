package com.harshi_solution.party.exception;

import com.harshi_solution.auth.exception.NotFoundException;

public class PartyNotFoundException extends NotFoundException {

    public PartyNotFoundException(Long id) {
        super("Party", id);
    }

}
