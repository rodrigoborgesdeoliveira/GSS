package br.pucpr.gss.client.view.uibinder;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GwtMockitoTestRunner.class)
public class CadastroViewImplTest {

    private CadastroViewImpl cadastroView;

    @Before
    public void setUp() throws Exception {
        cadastroView = new CadastroViewImpl();
    }

    @Test
    public void testLabelErroOcultoPorPadrao() {
//        assertFalse(cadastroView.labelErro.isVisible());
    }

    @Test
    public void testSetEmailInvalido() {
        cadastroView.setEmailInvalido();
//        Mockito.verify(cadastroView.labelErro).setVisible(true);
//        Mockito.verify(cadastroView.labelErro).setText("Email inválido");
    }

    @Test
    public void testSetSenhaInvalida() {
        cadastroView.setSenhaInvalida();
//        Mockito.verify(cadastroView.labelErro).setVisible(true);
//        Mockito.verify(cadastroView.labelErro).setText("Senha inválida");
    }

    @Test
    public void testSetConfirmarSenhaInvalida() {
        cadastroView.setConfirmarSenhaInvalida();
//        Mockito.verify(cadastroView.labelErro).setVisible(true);
//        Mockito.verify(cadastroView.labelErro).setText("Confirmação de senha inválida");
    }

    @Test
    public void testOcultarLabelErro() {
        cadastroView.ocultarLabelErro();

//        assertFalse(cadastroView.labelErro.isVisible());
//        assertTrue(cadastroView.labelErro.getText().isEmpty());
    }

    /**
     * Testar se ao inserir dados válidos após ter inserido dados inválidos, a mensagem de erro se torna oculta.
     */
    @Test
    public void testOcultarLabelErroOnClickCadastrarCamposValidos() {
//        Mockito.when(cadastroView.email.getText()).thenReturn("invalido");
//        Mockito.when(cadastroView.senha.getText()).thenReturn("senhavalida");
//        Mockito.when(cadastroView.confirmarSenha.getText()).thenReturn("senhavalida");
//
//        ClickEvent event = Mockito.mock(ClickEvent.class);
//        HandlerManager eventBus = Mockito.mock(HandlerManager.class);
//        CadastroPresenter presenter = new CadastroPresenter(eventBus, cadastroView, rpc);
//        presenter.onCadastrarButtonClicked("", cadastroView.senha.getText(), cadastroView.confirmarSenha.getText());
        // Email inválido
        // TODO: 26/01/2019 isVisible() só funciona com GWTTestCase, com Mockito sempre retorna falso.
        //  Encontrar uma maneira de testar com Mockito.
//        assertTrue(cadastroView.labelErro.isVisible());
//        assertFalse(cadastroView.labelErro.getText().isEmpty());
//        Mockito.when(cadastroView.email.getText()).thenReturn("email@exemplo.com");
//        cadastroView.onClickCadastrar(event);
        // Email válido
//        assertFalse(cadastroView.labelErro.isVisible());
//        assertTrue(cadastroView.labelErro.getText().isEmpty());
//
//        Mockito.when(cadastroView.senha.getText()).thenReturn("senhavalida");
//        Mockito.when(cadastroView.confirmarSenha.getText()).thenReturn("senhavalida");
    }
}