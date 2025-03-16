package com.mercadolibre.ipcontext.controller;

import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import com.mercadolibre.ipcontext.service.impl.IpBlacklistServiceImpl;
import com.mercadolibre.ipcontext.service.impl.IpContextServiceImpl;
import com.mercadolibre.ipcontext.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static com.mercadolibre.ipcontext.util.Utils.convertToJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class IpManagerControllerTest {

    @MockitoBean
    private IpContextServiceImpl ipContextService;
    @MockitoBean
    private IpBlacklistServiceImpl ipBlacklistService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getIpInfoSuccess() throws Exception {
        var ipContextResponseDtoMock = ipContextResponseDtoMock();

        when(ipContextService.getIpContext(IP_ADDRESS))
                .thenReturn(ipContextResponseDtoMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/info/" + IP_ADDRESS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(ipContextResponseDtoMock)));
    }

    @Test
    void clientRequestException() throws Exception {
        when(ipContextService.getIpContext(IP_ADDRESS))
                .thenThrow(ClientApiErrorException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/info/" + IP_ADDRESS))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void addIpAddressToBlacklistSuccess() throws Exception {
        var addIpBlacklistDtoMock = addIpBlacklistDtoMock();
        var getIpBlacklistDtoMock = getIpBlacklistDtoMock();

        when(ipBlacklistService.banIpAddress(addIpBlacklistDtoMock))
                .thenReturn(getIpBlacklistDtoMock);

        mockMvc.perform(MockMvcRequestBuilders.post("/banIp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.convertToJson(addIpBlacklistDtoMock)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(Utils.convertToJson(getIpBlacklistDtoMock)));
    }


}
