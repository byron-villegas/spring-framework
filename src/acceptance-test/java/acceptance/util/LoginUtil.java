package acceptance.util;

import acceptance.base.BaseSteps;
import acceptance.constants.Constants;
import acceptance.dto.LoginRequestDTO;
import acceptance.dto.LoginResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.Properties;

public class LoginUtil {
    private static final String urlLogin = "/oauth/token";

    private static final RequestSpecification restAssuredClient = RestAssured
            .with()
            .filters(Constants.REST_ASURRED_FILTERS)
            .baseUri(BaseSteps.BASE_PATH); // Agrega los filtros para el logeo de request, response, error en consola

    private static String TOKEN;

    public static String getToken() {
        return TOKEN;
    }

    public static void setearToken(final String username, final String password) {
        final LoginRequestDTO loginRequestDTO = LoginRequestDTO
                .builder()
                .username(username)
                .password(password)
                .build();

        Properties properties = PropertiesUtil
                .getPropertiesByFile(BaseSteps.AMBIENTE + "/" + BaseSteps.AMBIENTE + ".properties");

       Response response = restAssuredClient
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header(Constants.AUTHORIZATION, Constants.BASIC + properties.getProperty("login.basic"))
                .and()
                .body(loginRequestDTO)
                .when()
                .post(urlLogin)
                .then()
                .extract()
                .response();

        Assert.assertEquals(200, response.getStatusCode());

        TOKEN = response.as(LoginResponseDTO.class)
                .getAccessToken();
    }
}