package acceptance.steps;

import acceptance.base.BaseSteps;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class HttpStatusSteps {
    @Then("Valido que el codigo http status sea {string}")
    public void validoQueElCodigoHttpStatusSea(String httpStatus) {
        Assert.assertEquals(Integer.parseInt(httpStatus), BaseSteps.LAST_RESPONSE.getStatusCode());
    }
}
