package br.pucpr.gss.client.controller;

import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GwtMockitoTestRunner.class)
public class LoginControllerTest {

    private LoginController loginController;

    @Before
    public void setUp() throws Exception {
        loginController = new LoginController();
    }

    /**
     * Ao clicar no botão login, os campos serão validados.
     * - Email e senha são campos obrigatórios.
     * - Email deve possuir o formato exemplo@exemplo.com
     */
    @Test(expected = IllegalArgumentException.class)
    public void processarLoginEmailInvalido() {
        UsuarioLogin login = new UsuarioLogin("", "senha");
        loginController.processarLogin(login);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processarLoginSenhaInvalida() {
        UsuarioLogin login = new UsuarioLogin("e@exemplo.com", "");
        loginController.processarLogin(login);
    }

    @Test
    public void processarLoginCredenciaisInvalidas() {

    }

    @Test
    public void processarLoginCredenciaisValidas() {

    }
}
