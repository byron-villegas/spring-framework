package acceptance.steps;

import acceptance.util.LoginUtil;
import io.cucumber.java.en.Given;

public class LoginSteps {
    @Given("Soy usuario autenticado con username {string} y password {string}")
    public void soyUsuarioAutenticadoConUsernameYPassword(String username, String password) {
        LoginUtil.setearToken(username, password);
    }
}