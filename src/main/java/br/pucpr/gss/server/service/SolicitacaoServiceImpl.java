package br.pucpr.gss.server.service;

import br.pucpr.gss.client.service.SolicitacaoService;
import br.pucpr.gss.server.dao.*;
import br.pucpr.gss.server.model.Funcionario;
import br.pucpr.gss.server.util.Util;
import br.pucpr.gss.shared.model.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.Date;
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
    public void cadastrarSolicitacao(Solicitacao solicitacao, Usuario usuario) throws IllegalStateException {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        int idSolicitacao = gssDaoSolicitacao.insertSolicitacao(solicitacao);

        logger.log(Level.INFO, "Solicitação criada com sucesso");

        // id da solicitação pode ser zero se o LAST_INSERT_ID() for realizado em uma conexão diferente ou falhar.
        if (idSolicitacao > 0) {
            Date dataOcorrencia = new Date();
            new GssDaoEventoImpl().insertEvento(new Evento(String.format("%s criou a solicitação", usuario.getNome()),
                    dataOcorrencia, Util.stringDataHoraFromDate(dataOcorrencia), idSolicitacao, usuario.getId()));
        }
    }

    @Override
    public ArrayList<Solicitacao> consultarSolicitacoes(int idFuncionario) {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        return gssDaoSolicitacao.getSolicitacoesByIdFuncionario(idFuncionario);
    }

    @Override
    public Usuario getAtendenteById(int idAtendente) throws IllegalStateException, IllegalArgumentException {
        GssDao.Usuario gssDaoUsuario = new GssDaoUsuarioImpl();

        Usuario atendente = gssDaoUsuario.getUsuarioByIdFuncionario(idAtendente);
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

    @Override
    public void updateSolicitacao(Solicitacao solicitacao, Usuario usuario) throws IllegalStateException {
        GssDao.Solicitacao gssDaoSolicitacao = new GssDaoSolicitacaoImpl();

        // Antes de atualizar a solicitação, salvá-la para comparação com os novos dados e criação de eventos
        Solicitacao solicitacaoAntiga = gssDaoSolicitacao.getSolicitacaoById(solicitacao.getId());

        gssDaoSolicitacao.updateSolicitacao(solicitacao);

        logger.log(Level.INFO, "Solicitação atualizada com sucesso");

        // Criar eventos relativos às alterações realizadas
        GssDao.Evento gssDaoEvento = new GssDaoEventoImpl();
        for (Evento evento : Util.compararAlteracoesSolicitacao(solicitacaoAntiga, solicitacao, usuario)) {
            gssDaoEvento.insertEvento(evento);
        }
    }

    @Override
    public void requisitarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                                Usuario usuario) throws IllegalStateException {
        GssDao.InformacaoAdicional gssDaoInformacaoAdicional = new GssDaoInformacaoAdicionalImpl();

        // Verificar se já existe uma informação adicional para a solicitação. Então criar ou atualizar a já existente.
        InformacaoAdicional info = gssDaoInformacaoAdicional
                .getInformacaoAdicionalByIdSolicitacao(informacaoAdicional.getIdSolicitacao());
        if (info == null) {
            // Criar um novo
            gssDaoInformacaoAdicional.insertInformacaoAdicional(informacaoAdicional);

            logger.log(Level.INFO, "Informação adicional registrada com sucesso");
        } else {
            // Atualizar existente
            info.setDescricao(informacaoAdicional.getDescricao());
            gssDaoInformacaoAdicional.updateInformacaoAdicional(info);

            logger.log(Level.INFO, "Informação adicional atualizada com sucesso");
        }

        Date dataOcorrencia = new Date();
        new GssDaoEventoImpl().insertEvento(new Evento(String.format("%s requisitou informações adicionais",
                usuario.getNome()), dataOcorrencia, Util.stringDataHoraFromDate(dataOcorrencia), solicitacao.getId(),
                usuario.getId()));
    }

    @Override
    public InformacaoAdicional getInformacaoAdicionalByIdSolicitacao(int idSolicitacao) throws IllegalStateException {
        GssDao.InformacaoAdicional gssDaoInformacaoAdicional = new GssDaoInformacaoAdicionalImpl();

        return gssDaoInformacaoAdicional.getInformacaoAdicionalByIdSolicitacao(idSolicitacao);
    }

    @Override
    public void registrarInformacoesAdicionais(InformacaoAdicional informacaoAdicional, Solicitacao solicitacao,
                                               Usuario usuario) throws IllegalStateException {
        GssDao.InformacaoAdicional gssDaoInformacaoAdicional = new GssDaoInformacaoAdicionalImpl();

        gssDaoInformacaoAdicional.updateInformacaoAdicional(informacaoAdicional);

        logger.log(Level.INFO, "Informação adicional atualizada com sucesso");

        Date dataOcorrencia = new Date();
        new GssDaoEventoImpl().insertEvento(new Evento(String.format("%s registrou informações adicionais",
                usuario.getNome()), dataOcorrencia, Util.stringDataHoraFromDate(dataOcorrencia), solicitacao.getId(),
                usuario.getId()));
    }

    @Override
    public void removerInformacoesAdicionaisByIdSolicitacao(int idSolicitacao) throws IllegalStateException {
        GssDao.InformacaoAdicional gssDaoInformacaoAdicional = new GssDaoInformacaoAdicionalImpl();

        gssDaoInformacaoAdicional.deleteInformacaoAdicionalByIdSolicitacao(idSolicitacao);

        logger.log(Level.INFO, "Informação adicional removida com sucesso");
    }

    @Override
    public ArrayList<Evento> getEventosSolicitacao(int idSolicitacao) throws IllegalStateException {
        GssDao.Evento gssDaoEvento = new GssDaoEventoImpl();

        return gssDaoEvento.getEventosByIdSolicitacao(idSolicitacao);
    }
}