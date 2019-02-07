package br.pucpr.gss.server.service;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.server.dao.*;
import br.pucpr.gss.server.model.Funcionario;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;
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
    public ArrayList<Setor> getListaOutrosSetores(int idFuncionario) throws IllegalStateException {
        RhDao.Setor rhDaoSetor = new RhDaoSetorImpl();
        ArrayList<Setor> setores = rhDaoSetor.getSetoresExcluindoFuncionario(idFuncionario);

        logger.log(Level.INFO, "Lista de setores: " + setores);

        return setores;
    }

    @Override
    public Setor getSetorById(int idSetor) throws IllegalStateException {
        RhDao.Setor rhDaoSetor = new RhDaoSetorImpl();
        Setor setor = rhDaoSetor.getSetorById(idSetor);

        logger.log(Level.INFO, "Setor encontrado: " + setor);

        return setor;
    }

    @Override
    public void cadastrarSolicitacao(Solicitacao solicitacao) throws IllegalStateException {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        gssDaoSolicitacao.insertSolicitacao(solicitacao);

        logger.log(Level.INFO, "Solicitação criada com sucesso");
    }

    @Override
    public ArrayList<Solicitacao> consultarSolicitacoes(int idFuncionario) {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        return gssDaoSolicitacao.getSolicitacoesByIdFuncionario(idFuncionario);
    }

    @Override
    public Usuario getAtendenteById(int idAtendente) throws IllegalStateException, IllegalArgumentException {
        GssDao.Usuario gssDaoUsuario = new GssDaoUsuarioImpl();

        Usuario atendente = gssDaoUsuario.getUsuarioById(idAtendente);
        if (atendente == null) {
            throw new IllegalArgumentException("Atendente não existe");
        }

        return atendente;
    }

    @Override
    public ArrayList<Usuario> getListaAtendentesByIdSetorExcetoGestor(int idSetor, int idGestor) throws IllegalStateException {
        RhDao.Funcionario rhDaoFuncionario = new RhDaoFuncionarioImpl();
        ArrayList<Funcionario> funcionarios = rhDaoFuncionario.getFuncionariosByIdSetor(idSetor);

        ArrayList<Usuario> atendentes = new ArrayList<>();

        for (Funcionario f : funcionarios) {
            if (f.getId() != idGestor) {
                atendentes.add(new Usuario(f.getNome(), f.getId()));
            }
        }

        return atendentes;
    }
}