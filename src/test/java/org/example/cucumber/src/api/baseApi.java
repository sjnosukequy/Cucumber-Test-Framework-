package org.example.cucumber.src.api;

import io.restassured.RestAssured;
import java.io.PrintStream;
import io.qameta.allure.restassured.AllureRestAssured;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import org.example.cucumber.env.*;

public class baseApi {
    static {
        PrintStream log4jStream = IoBuilder.forLogger(RestAssured.class)
                .setLevel(Level.WARN)
                .buildPrintStream();
        // config rest assured to use BaseURI, etc....
        RestAssured.baseURI = envManager.getBaseURI();
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(log4jStream),
                new ErrorLoggingFilter(log4jStream));
        // RestAssured.config().matcherConfig(new
        // MatcherConfig(MatcherConfig.ErrorDescriptionType.HAMCREST));
    }
}
