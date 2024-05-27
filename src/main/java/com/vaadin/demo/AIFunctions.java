package com.vaadin.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AIFunctions {

    private final RestTemplate restTemplate;

    @Autowired
    public AIFunctions(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String API_BASE_URL = "https://personal-fch30kxw.outsystemscloud.com/TechDay2024/rest/info/";

    //Hint: create your AI functions here, and rest assured that all the imports you'll need to use are already there. :D

    //In the Vinci Energies group, what was the number of employees of Axians in 2020?
    @Tool("Returns the number of employees of a company from Vinci Group for a given year")
    public Integer getNumberOfEmployeeByCompanyNameAndYear(String company, String year){

        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/GetCompanyEmployees?Company=" + company + "&Year=" + year, String.class);

        Integer responseNum = null;

        try {
            if(response != null){
                responseNum = Integer.parseInt(response.getBody());
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + responseNum);
        }

        return responseNum;

        //return 200;

    }

    //And what was the turnover?
    @Tool("Returns the turnover of the company from Vinci Group for a given year")
    public Integer getTurnoverOfCompanyByCompanyNameAndYear(String company, String year){

        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/GetCompanyTurnover?Company=" + company + "&Year=" + year, String.class);

        Integer responseNum = null;

        try {
            if(response != null){
                responseNum = Integer.parseInt(response.getBody());
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + responseNum);
        }

        return responseNum;

        //return 60;

    }

    //What was the ratio of turnover to the number of employees in the same year?
    @Tool("Returns the ratio of turnover value by a number of employees for a given year")
    public BigDecimal getRatioOfCompanyByCompanyNameAndYear(String company, String year){

        Integer numEmp = getNumberOfEmployeeByCompanyNameAndYear(company,year);
        Integer turnover = getTurnoverOfCompanyByCompanyNameAndYear(company, year);

        return BigDecimal.valueOf(numEmp).divide(
                BigDecimal.valueOf(turnover), 2000, BigDecimal.ROUND_DOWN);

    }
    
}
