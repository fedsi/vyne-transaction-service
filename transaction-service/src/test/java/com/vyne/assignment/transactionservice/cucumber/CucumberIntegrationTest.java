package com.vyne.assignment.transactionservice.cucumber;

import com.vyne.assignment.transactionservice.TransactionServiceApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources/")
@SpringBootTest(classes = TransactionServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberIntegrationTest {

}

