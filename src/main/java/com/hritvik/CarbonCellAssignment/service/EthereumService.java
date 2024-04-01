package com.hritvik.CarbonCellAssignment.service;

import com.hritvik.CarbonCellAssignment.utils.Response;
import com.hritvik.CarbonCellAssignment.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;

@Service
@Slf4j
public class EthereumService {

    @Autowired
    Web3j web3j;
    @Autowired
    ResponseUtil responseUtil;
    public Response getAccountBalance(String accountAddress) {
        try {
            log.info("Fetching balance for account: " + accountAddress);
            EthGetBalance balanceResponse = web3j.ethGetBalance(accountAddress, DefaultBlockParameter.valueOf("latest")).send();
            if (balanceResponse.hasError()) {
                return responseUtil.internalServerErrorResponse("Error occurred while fetching balance: " + balanceResponse.getError().getMessage());
            }
            BigInteger balanceWei = balanceResponse.getBalance();
            // Convert balance from Wei to Ether
            double balanceEther = balanceWei.divide(BigInteger.TEN.pow(18)).doubleValue();
            log.info("balance fetched successfully: " + balanceEther );
            return responseUtil.successResponse("Success","Balance of account " + accountAddress + " is: " + balanceEther + " Ether");
        } catch (Exception e) {
            log.error("Exception occurred while fetching balance: " + e.getMessage());
            return responseUtil.internalServerErrorResponse( "Exception occurred: " + e.getMessage());
        }
    }
}
