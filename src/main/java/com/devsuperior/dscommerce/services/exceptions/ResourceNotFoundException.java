package com.devsuperior.dscommerce.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{ //RunTimeException não exige o try catch

    public ResourceNotFoundException(String msg){
        super(msg);
    }

}
