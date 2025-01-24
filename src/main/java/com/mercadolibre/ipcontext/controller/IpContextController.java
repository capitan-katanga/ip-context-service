package com.mercadolibre.ipcontext.controller;

import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import com.mercadolibre.ipcontext.dto.ipcontext.IpContextRequestDto;
import com.mercadolibre.ipcontext.service.IpContextService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IpContextController {

    private final IpContextService ipContextService;

    @Autowired
    public IpContextController(IpContextService ipContextService) {
        this.ipContextService = ipContextService;
    }

    @GetMapping("/ipinfo/{ip}")
    public ResponseEntity<IpContextResponseDto> getIpInfo(@PathVariable(value = "ip") String ip) {
        var ipContextRequestDto = IpContextRequestDto.builder()
                .ipAddress(ip)
                .build();
        return new ResponseEntity<>(ipContextService.getCountryInformation(ipContextRequestDto.ipAddress()), HttpStatus.OK);
    }

}
