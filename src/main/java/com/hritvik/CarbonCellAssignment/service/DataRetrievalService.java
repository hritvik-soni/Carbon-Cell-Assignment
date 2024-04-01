package com.hritvik.CarbonCellAssignment.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hritvik.CarbonCellAssignment.dto.ExternalAPIRequestDto;
import com.hritvik.CarbonCellAssignment.utils.Response;
import com.hritvik.CarbonCellAssignment.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataRetrievalService {

    @Value("${data.retrieval.url}")
    private String dataRetrievalUrl;
    @Autowired
    ResponseUtil responseUtil;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public Response getDataByCategory(String category, int limit) {
        try {
            log.info("Data Retrieval Started");

            log.info("CallingExternalAPI");
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(dataRetrievalUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("Data Retrieved from external API Successfully");
                String responseBody = responseEntity.getBody(); // Assuming response is JSON

                // Object Mapping using ObjectMapper
                log.info("Mapping ExternalAPIRequestDto");
                ExternalAPIRequestDto allEntries = objectMapper.readValue(responseBody, ExternalAPIRequestDto.class);
                if (allEntries == null) {
                    log.info("Record Not Found");
                    return responseUtil.internalServerErrorResponse("Record Not Found");
                }
                log.info("All Data Retrieved from external API\n" + allEntries);
                // Filter entries based on category and limit
                List<ExternalAPIRequestDto.Entities> filteredEntries;
                if (category.equalsIgnoreCase("NA")) {
                    log.info("category is NA");
                    filteredEntries = allEntries.getEntries();
                } else {
                    log.info("category is not NA");
                    filteredEntries = allEntries.getEntries().stream()
                            .filter(entry -> entry.getCategory().equalsIgnoreCase(category))
                            .collect(Collectors.toList());
                }

                // Apply limit if specified
                if (limit > 0 && limit < filteredEntries.size()) {
                    log.info("limit is provided");
                    filteredEntries = filteredEntries.subList(0, limit);
                }
                if (filteredEntries.isEmpty()) {
                    log.info("NO Record Found");
                    return responseUtil.internalServerErrorResponse("NO Record Found");
                }
                log.info("Data Retrieved Successfully");
                return responseUtil.successResponse("Data Retrieved Successfully", filteredEntries);
            } else {
                log.error("Data Retrieval Failed");
                return responseUtil.internalServerErrorResponse("Data Retrieval Failed");
            }
        } catch (Exception e) {
            log.error("Exception occurred while Data Retrieval", e);
            return responseUtil.internalServerErrorResponse("Exception occurred while Data Retrieval");
        }
    }
}
