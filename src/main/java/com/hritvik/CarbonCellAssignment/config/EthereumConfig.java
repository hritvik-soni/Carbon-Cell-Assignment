package com.hritvik.CarbonCellAssignment.config;

import com.hritvik.CarbonCellAssignment.utils.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class EthereumConfig {

    @Value("${infura.api.key}")
    private String infuraKey;
    @Value("${infura.base.url}")
    private String baseUrl;

    @Bean
    public Web3j web3j() {
        // Connect to Infura Ethereum client
        String infuraUrl = baseUrl + Base64Util.decodeFromBase64(infuraKey);
        return Web3j.build(new HttpService(infuraUrl));
    }
}
