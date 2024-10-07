package acceptance.constants;

import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.util.Arrays;
import java.util.List;

public class Constants {
    // AUTHORIZATION
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic ";
    public static final String BEARER = "Bearer ";

    // REST ASSURED FILTERS
    public static final List<Filter> REST_ASURRED_FILTERS = Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
}