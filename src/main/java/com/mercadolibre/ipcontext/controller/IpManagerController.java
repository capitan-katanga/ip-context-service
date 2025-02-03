package com.mercadolibre.ipcontext.controller;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import com.mercadolibre.ipcontext.service.IpBlacklistService;
import com.mercadolibre.ipcontext.service.IpContextService;
import com.mercadolibre.ipcontext.validator.IpAddress;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class IpManagerController {

    private final IpBlacklistService ipBlacklistService;
    private final IpContextService ipContextService;

    @Autowired
    public IpManagerController(IpBlacklistService ipBlacklistService, IpContextService ipContextService) {
        this.ipBlacklistService = ipBlacklistService;
        this.ipContextService = ipContextService;
    }

    @GetMapping("/info/{ip}")
    public ResponseEntity<IpContextResponseDto> getIpInfo(@PathVariable(value = "ip") @IpAddress(message = "It is not a valid ip address") String ipAddress) {
        return new ResponseEntity<>(ipContextService.getIpContext(ipAddress), HttpStatus.OK);
    }

    @PostMapping("/banIp")
    public ResponseEntity<GetIpBlacklistDto> addIpAddressToBlacklist(@Valid @RequestBody AddIpBlacklistDto addIpBlacklistDto) {
        return new ResponseEntity<>(ipBlacklistService.banIpAddress(addIpBlacklistDto), HttpStatus.OK);
    }

}
