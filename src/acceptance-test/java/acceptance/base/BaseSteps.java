package acceptance.base;

import acceptance.constants.Constants;
import acceptance.util.LoginUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseSteps {
    public static final String AMBIENTE = System.getenv("AMBIENTE");
    private static final String HOST = System.getenv("HOST");
    private static final String PORT = System.getenv("PORT");
    private static final String CONTEXT_PATH = System.getenv("CONTEXT_PATH");
    public static final String BASE_PATH = HOST + ":" + PORT + "/" + CONTEXT_PATH;
    public static Response LAST_RESPONSE;

    private static final RequestSpecification restAssuredClient = RestAssured
            .with()
            .baseUri(BaseSteps.BASE_PATH)
            .filters(Constants.REST_ASURRED_FILTERS); // Agrega los filtros para el logeo de request, response, error en consola

    public static void executeGet(String url) {
        LAST_RESPONSE = restAssuredClient
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(Constants.AUTHORIZATION, Constants.BEARER + LoginUtil.getToken())
                .when()
                .get(url)
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    public static void executePost(String url, Object body) {
        LAST_RESPONSE = restAssuredClient
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(Constants.AUTHORIZATION, Constants.BEARER + LoginUtil.getToken())
                .and()
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
}