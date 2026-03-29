package org.example.cucumber.src.api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.UrlDecoder;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
// import org.example.cucumber.src.api.ScenarioLogPrintStreamFactory;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.TreeSet;

public class RequestLogFilter implements Filter {
    // private static final boolean SHOW_URL_ENCODED_URI = true;

    private final LogDetail requestLogDetail;
    private final boolean shouldPrettyPrint;
    private final boolean showUrlEncodedUri;
    private final Set<String> blacklistedHeaders;

    public RequestLogFilter(LogDetail requestLogDetail, boolean shouldPrettyPrint, boolean showUrlEncodedUri,
            Set<String> blacklistedHeaders) {
        this.requestLogDetail = requestLogDetail;
        this.shouldPrettyPrint = shouldPrettyPrint;
        this.showUrlEncodedUri = showUrlEncodedUri;

        TreeSet<String> caseInsensitive = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (blacklistedHeaders != null) {
            caseInsensitive.addAll(blacklistedHeaders);
        }
        this.blacklistedHeaders = caseInsensitive;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
            FilterContext ctx) {

        PrintStream stream = ScenarioLogPrintStreamFactory.create();

        String uri = requestSpec.getURI();
        if (!showUrlEncodedUri) {
            uri = UrlDecoder.urlDecode(
                    uri,
                    Charset.forName(requestSpec.getConfig().getEncoderConfig().defaultQueryParameterCharset()),
                    true);
        }

        stream.println("===== REST ASSURED REQUEST =====");
        RequestPrinter.print(
                requestSpec,
                requestSpec.getMethod(),
                uri,
                requestLogDetail,
                blacklistedHeaders,
                stream,
                shouldPrettyPrint);

        Response response = ctx.next(requestSpec, responseSpec);

        stream.println("===== REST ASSURED RESPONSE =====");
        stream.println("Status code: " + response.getStatusCode());
        stream.println("Body:");
        try {
            stream.println(response.getBody().asPrettyString().replace("\n", System.lineSeparator()));
        } catch (Exception e) {
            stream.println("[Unable to read response body: " + e.getMessage() + "]");
        }

        return response;
    }
}
