package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.Usuario;
import br.pucpr.gss.shared.model.UsuarioLogin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("Cadastro")
public interface CadastroService extends RemoteService {
    Usuario cadastrar(UsuarioLogin usuarioLogin) throws IllegalStateException, IllegalArgumentException;

    /**
     * Retorna uma lista de todos os usuários cadastrados no sistema, desde que o usuário requisitando seja um
     * administrador.
     *
     * @param idUsuario O id do usuário realizando a requisição. Precisa ser um administrador.
     * @return Lista de usuários cadastrados no sistema.
     * @throws IllegalArgumentException Se o usuário realizando a requisição não for um administrador.
     */
    List<Usuario> getCadastros(int idUsuario) throws IllegalStateException, IllegalArgumentException;

    /**
     * Marca um usuário como administrador ou não do sistema.
     *
     * @param idUsuarioAtual    Id do usuário realizandoa a requisição. Precisa ser um administrador.
     * @param idUsuarioCadastro Id do usuário para marcar ou não como administrador.
     * @param asAdmin           true, se for para marcar como administrador, false, caso contrário.
     * @throws IllegalArgumentException Se o usuário realizando a requisição não for um administrador.
     */
    void setAdmin(int idUsuarioAtual, int idUsuarioCadastro, boolean asAdmin) throws IllegalStateException,
            IllegalArgumentException;

    /**
     * Classe para chamar os métodos de CadastroService.
     * Use CadastroService.RPC.getInstance() para acessar uma instância de CadastroServiceAsync
     */
    class RPC {
        private static CadastroServiceAsync instance = GWT.create(CadastroService.class);

        public static synchronized CadastroServiceAsync getInstance() {
            return instance;
        }
    }
}
