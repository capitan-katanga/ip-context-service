package com.mercadolibre.ipcontext.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var patter = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
        if (value.matches(patter)) {
            var octets = value.split("\\.");
            for (String octet : octets) {
                if (Integer.parseInt(octet) > 255) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
