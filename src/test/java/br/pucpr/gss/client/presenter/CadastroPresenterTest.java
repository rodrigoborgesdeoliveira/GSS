package br.pucpr.gss.client.presenter;

import br.pucpr.gss.client.view.uibinder.CadastroViewImpl;
import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

@RunWith(GwtMockitoTestRunner.class)
public class CadastroPresenterTest {

    @InjectMocks
    private CadastroPresenter cadastroPresenter;
    @GwtMock
    private CadastroViewImpl cadastroView;

    @Test
    public void testOnCadastrarButtonClickedEmailInvalido() {
        String email = "invalido";
        String senha;
        String confirmarSenha = senha = "senha"; // Senha também é inválida aqui, mas não deve chegar a ser validada

        cadastroPresenter.onCadastrarButtonClicked(email, senha, confirmarSenha);
        Mockito.verify(cadastroView).setEmailInvalido();

        Mockito.verify(cadastroView, Mockito.never()).setSenhaInvalida();
        Mockito.verify(cadastroView, Mockito.never()).setConfirmarSenhaInvalida();
    }

    @Test
    public void testOnCadastrarButtonClickedSenhaInvalida() {
        String email = "email@exemplo.com";
        String senha = "senha";
        String confirmarSenha = "senhadiferente"; // Confirmação de senha é errada aqui, mas a validação deve parar na senha

        cadastroPresenter.onCadastrarButtonClicked(email, senha, confirmarSenha);
        Mockito.verify(cadastroView).setSenhaInvalida();

        Mockito.verify(cadastroView, Mockito.never()).setEmailInvalido();
        Mockito.verify(cadastroView, Mockito.never()).setConfirmarSenhaInvalida();
    }

    @Test
    public void testOnCadastrarButtonClickedConfirmarSenhaInvalida() {
        String email = "email@exemplo.com";
        String senha = "senhavalida";
        String confirmarSenha = "outrasenha";

        cadastroPresenter.onCadastrarButtonClicked(email, senha, confirmarSenha);
        Mockito.verify(cadastroView).setConfirmarSenhaInvalida();

        Mockito.verify(cadastroView, Mockito.never()).setEmailInvalido();
        Mockito.verify(cadastroView, Mockito.never()).setSenhaInvalida();

        // TODO: 26/01/2019 Verificar o cadastro não continua
    }
}