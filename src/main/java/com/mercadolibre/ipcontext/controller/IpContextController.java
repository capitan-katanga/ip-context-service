package com.mercadolibre.ipcontext.controller;

import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import com.mercadolibre.ipcontext.service.IpContextService;
import com.mercadolibre.ipcontext.validator.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
public class IpContextController {

    private final IpContextService ipContextService;

    @Autowired
    public IpContextController(IpContextService ipContextService) {
        this.ipContextService = ipContextService;
    }

    @GetMapping("/info/{ip}")
    public ResponseEntity<IpContextResponseDto> getIpInfo(@PathVariable(value = "ip") @IpAddress(message = "It is not a valid ip address") String ipAddress) {
        return new ResponseEntity<>(ipContextService.getCountryInformation(ipAddress), HttpStatus.OK);
    }

}
