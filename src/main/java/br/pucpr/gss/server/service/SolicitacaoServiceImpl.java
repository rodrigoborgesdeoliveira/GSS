package br.pucpr.gss.server.service;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.server.dao.GssDao;
import br.pucpr.gss.server.dao.GssDaoSolicitacaoImpl;
import br.pucpr.gss.server.dao.RhDao;
import br.pucpr.gss.server.dao.RhDaoSetorImpl;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitacaoServiceImpl extends RemoteServiceServlet implements SolicitacaoService {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public ArrayList<Setor> getListaSetores() throws IllegalStateException {
        RhDao.Setor rhDaoSetor = new RhDaoSetorImpl();
        ArrayList<Setor> setores = rhDaoSetor.getSetores();

        logger.log(Level.INFO, "Lista de setores: " + setores);

        return setores;
    }

    @Override
    public void cadastrarSolicitacao(Solicitacao solicitacao) throws IllegalStateException {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        gssDaoSolicitacao.insertSolicitacao(solicitacao);

        logger.log(Level.INFO, "Solicitação criada com sucesso");
    }

    @Override
    public ArrayList<Solicitacao> consultarSolicitacoes(int idUsuario) {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        return gssDaoSolicitacao.getSolicitacoesByIdUsuario(idUsuario);
    }
}