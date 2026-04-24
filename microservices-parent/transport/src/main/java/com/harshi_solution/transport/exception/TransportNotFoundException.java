package com.harshi_solution.transport.exception;

import com.harshi_solution.auth.exception.NotFoundException;;

public class TransportNotFoundException extends NotFoundException{
        public TransportNotFoundException(Long id){
            super("Transport", id);
        }
}
