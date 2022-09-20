package org.practice.dev.msglmelipracticetherevenge.client;

import org.junit.jupiter.api.Test;
import org.practice.dev.msglmelipracticetherevenge.dto.ipapi.IpapiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IpapiClientTest {

    @Autowired
    IpapiClient ipapiClient;

    @Test
    void getInfoIp() {
        IpapiDto ipapiDto = ipapiClient.getIpInformationByIpAddress("181.165.139.14");
        System.out.println(ipapiDto.toString());
    }

}