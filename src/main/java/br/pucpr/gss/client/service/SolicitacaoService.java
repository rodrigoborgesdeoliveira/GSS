package br.pucpr.gss.client.service;

import br.pucpr.gss.shared.model.InformacaoAdicional;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("Solicitacao")
public interface SolicitacaoService extends RemoteService {
    ArrayList<Setor> getListaSetores() throws IllegalStateException;

    /**
     * Retorna a lista de todos os setores exceto aquele pertencente ao funcionário.
     *
     * @return Lista de setores em que o funcionário não faz parte.
     */
    ArrayList<Setor> getListaOutrosSetores(int idFuncionario) throws IllegalStateException;

    Setor getSetorById(int idSetor) throws IllegalStateException;

    void cadastrarSolicitacao(Solicitacao solicitacao, Usuario usuario) throws IllegalStateException;

    ArrayList<Solicitacao> consultarSolicitacoes(int idFuncionario) throws IllegalStateException;

    Usuario getAtendenteById(int idAtendente) throws IllegalStateException, IllegalArgumentException;

    /**
     * Retorna uma lista de todos os atendentes de um dados setor, exceto pelo próprio gestor.
     *
     * @return Lista dos atendentes.
     */
    ArrayList<Usuario> getListaAtendentesByIdSetorExcetoGestor(int idSetor, int idGestor) throws IllegalStateException;

    /**
     * Atualiza a solicitação, analisa as alterações e notifica os envolvidos.
     *
     * @param solicitacao A solicitação a ser atualizada com as alterações feitas.
     * @param usuario     O usuário que realizou as alterações.
     */
    void updateSolicitacao(Solicitacao solicitacao, Usuario usuario) throws IllegalStateException;

    void requisitarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                         Usuario usuario) throws IllegalStateException;

    InformacaoAdicional getInformacaoAdicionalByIdSolicitacao(int idSolicitacao) throws IllegalStateException;

    void registrarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                        Usuario usuario) throws IllegalStateException;

    void removerInformacoesAdicionaisByIdSolicitacao(int idSolicitacao) throws IllegalStateException;

    /**
     * Classe para chamar os métodos de SolicitacaoService.
     * Use SolicitacaoService.RPC.getInstance() para acessar uma instância de SolicitacaoServiceAsync
     */
    class RPC {
        private static final SolicitacaoServiceAsync instance = GWT.create(SolicitacaoService.class);

        public static SolicitacaoServiceAsync getInstance() {
            return instance;
        }
    }
}
