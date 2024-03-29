package br.pucpr.gss.server.dao;

import java.util.ArrayList;
import java.util.List;

public interface GssDao {

    interface Usuario {
        br.pucpr.gss.shared.model.Usuario getUsuarioById(int id) throws IllegalStateException;

        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionario(int idFuncionario) throws IllegalStateException;

        br.pucpr.gss.shared.model.Usuario getUsuarioByIdFuncionarioESenha(int idFuncionario, String senha) throws IllegalStateException;

        int getQuantidadeUsuarios() throws IllegalStateException;

        void insertUsuario(br.pucpr.gss.shared.model.Usuario usuario) throws IllegalStateException;

        List<br.pucpr.gss.shared.model.Usuario> getUsuarios() throws IllegalStateException;

        void setAdmin(int idUsuarioCadastro, boolean asAdmin) throws IllegalStateException;
    }

    interface Solicitacao {
        /**
         * Insere uma solicitação no banco de dados.
         *
         * @param solicitacao Solicitação a ser inserida.
         * @return ID da solicitação que foi inserida ou 0 se não foi possível obter o ID.
         */
        int insertSolicitacao(br.pucpr.gss.shared.model.Solicitacao solicitacao) throws IllegalStateException;

        br.pucpr.gss.shared.model.Solicitacao getSolicitacaoById(int idSolicitacao) throws IllegalStateException;

        ArrayList<br.pucpr.gss.shared.model.Solicitacao> getSolicitacoesByIdFuncionario(int idFuncionario) throws IllegalStateException;

        void updateSolicitacao(br.pucpr.gss.shared.model.Solicitacao solicitacao) throws IllegalStateException;
    }

    interface InformacaoAdicional {
        void insertInformacaoAdicional(br.pucpr.gss.shared.model.InformacaoAdicional informacaoAdicional) throws IllegalStateException;

        void updateInformacaoAdicional(br.pucpr.gss.shared.model.InformacaoAdicional informacaoAdicional) throws IllegalStateException;

        br.pucpr.gss.shared.model.InformacaoAdicional getInformacaoAdicionalByIdSolicitacao(int idSolicitacao) throws IllegalStateException;

        void deleteInformacaoAdicionalByIdSolicitacao(int idSolicitacao) throws IllegalStateException;
    }

    interface Evento {
        void insertEvento(br.pucpr.gss.shared.model.Evento evento) throws IllegalStateException;

        ArrayList<br.pucpr.gss.shared.model.Evento> getEventosByIdSolicitacao(int idSolicitacao) throws IllegalStateException;
    }
}
