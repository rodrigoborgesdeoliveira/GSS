package br.pucpr.gss.shared.model;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

@RunWith(GwtMockitoTestRunner.class)
public class UsuarioLoginTest {

    private UsuarioLogin usuarioLogin;

    @Before
    public void setUp() throws Exception {
        usuarioLogin = new UsuarioLogin("exemplo@exemplo.com", "senha");
    }

    @Test
    public void testGetEmail() {
        assertNotNull(usuarioLogin);
        assertEquals("exemplo@exemplo.com", usuarioLogin.getEmail());
    }

    @Test
    public void testGetSenha() {
        assertNotNull(usuarioLogin);
        assertEquals("senha", usuarioLogin.getSenha());
    }

    @Test
    public void testIsEmailValido() {
        // Validar com email nulo
        usuarioLogin.setEmail(null);
        assertFalse(usuarioLogin.isEmailValido());

        // Validar com email vazio
        usuarioLogin.setEmail("");
        assertFalse(usuarioLogin.isEmailValido());

        // Validar com formato de email inválido
        usuarioLogin.setEmail("exemplo");
        assertFalse(usuarioLogin.isEmailValido());
        usuarioLogin.setEmail("exemplo@");
        assertFalse(usuarioLogin.isEmailValido());
        usuarioLogin.setEmail("exemplo@e");
        assertFalse(usuarioLogin.isEmailValido());
        usuarioLogin.setEmail("exemplo@e.");
        assertFalse(usuarioLogin.isEmailValido());
        usuarioLogin.setEmail("exemplo@e.c");
        assertFalse(usuarioLogin.isEmailValido());

        // Validar com email válido
        usuarioLogin.setEmail("exemplo@e.com");
        assertTrue(usuarioLogin.isEmailValido());
    }

    @Test
    public void testIsSenhaValida() {
        // Validar com senha nula
        usuarioLogin.setSenha(null);
        assertFalse(usuarioLogin.isSenhaValida());

        // Validar com senha vazia
        usuarioLogin.setSenha("");
        assertFalse(usuarioLogin.isSenhaValida());

        // Validar senha válida
        usuarioLogin.setSenha("senha");
        assertTrue(usuarioLogin.isSenhaValida());
    }
}