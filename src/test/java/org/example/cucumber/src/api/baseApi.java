package org.example.cucumber.src.api;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
// import java.io.PrintStream;
import io.qameta.allure.restassured.AllureRestAssured;

// import java.util.EnumSet;
import java.util.Set;

// import org.apache.logging.log4j.Level;
// import org.apache.logging.log4j.io.IoBuilder;
// import io.restassured.filter.log.ErrorLoggingFilter;
// import io.restassured.filter.log.RequestLoggingFilter;
import org.example.cucumber.env.*;

public class baseApi {
    static {
        // PrintStream log4jStream = IoBuilder.forLogger(RestAssured.class)
        // .setLevel(Level.WARN)
        // .buildPrintStream();
        // config rest assured to use BaseURI, etc....
        RestAssured.baseURI = envManager.getBaseURI();
        RestAssured.filters(new AllureRestAssured(), new RequestLogFilter(
                LogDetail.ALL,
                true,
                true,
                Set.of("Authorization", "Cookie")));
        // RestAssured.config().matcherConfig(new
        // MatcherConfig(MatcherConfig.ErrorDescriptionType.HAMCREST));
    }
}
