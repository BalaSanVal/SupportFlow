package com.supportflow.role.exception;

public class DuplicateRoleCodeException extends RuntimeException {

    public DuplicateRoleCodeException(String code){
        super("A role with code '" + code + "' already exist");
    }
}
