package br.pucpr.gss.server.util;

import br.pucpr.gss.server.dao.*;
import br.pucpr.gss.server.model.Notificacao;
import br.pucpr.gss.shared.model.Evento;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Classe com métodos para diversos usos. Para usar, utilize-a de forma estática (<code>Util.nomeDoMetodo()</code>).
 */
public class Util {

    private static Logger logger = Logger.getLogger("Util");

    /**
     * Não permitir a instanciação dessa classe. Utilizar sempre como Util.metodo.
     */
    private Util() {
    }

    /**
     * Compara as alterações ocorridas em uma solicitação e retorna-as em forma de eventos.
     *
     * @param solicitacaoAntiga Solicitação antes das alterações.
     * @param solicitacaoNova   Solicitação após as alterações.
     * @param usuario           Usuário que realizou as alterações na solicitação.
     * @return Lista de eventos ocorridos.
     */
    public static ArrayList<Evento> compararAlteracoesSolicitacao(Solicitacao solicitacaoAntiga,
                                                                  Solicitacao solicitacaoNova,
                                                                  Usuario usuario) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Date dataOcorrencia = new Date();

        // Comparar prioridade
        if (solicitacaoAntiga.getPrioridade().getIndice() != solicitacaoNova.getPrioridade().getIndice()) {
            String nomeEvento = String.format("%s alterou a prioridade de %s para %s", usuario.getNome(),
                    solicitacaoAntiga.getPrioridade().getNome(), solicitacaoNova.getPrioridade().getNome());
            eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia), solicitacaoNova.getId(),
                    usuario.getId()));
        }

        // Comparar estado
        if (solicitacaoAntiga.getEstado().getIndice() != solicitacaoNova.getEstado().getIndice()) {
            String nomeEvento = String.format("%s alterou o estado de %s para %s", usuario.getNome(),
                    solicitacaoAntiga.getEstado().getNome(), solicitacaoNova.getEstado().getNome());
            eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia), solicitacaoNova.getId(),
                    usuario.getId()));
        }

        // Comparar prazo
        if (solicitacaoAntiga.getPrazo() != null) {
            if (solicitacaoNova.getPrazo() != null) {
                if (!solicitacaoAntiga.getPrazo().equals(solicitacaoNova.getPrazo())) {
                    // Prazos diferentes
                    String nomeEvento = String.format("%s alterou o prazo de %s para %s", usuario.getNome(),
                            stringDataFromDate(solicitacaoAntiga.getPrazo()), stringDataFromDate(solicitacaoNova.getPrazo()));
                    eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia),
                            solicitacaoNova.getId(), usuario.getId()));
                }
            } else {
                // Prazos diferentes
                String nomeEvento = String.format("%s removeu o prazo", usuario.getNome());
                eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia),
                        solicitacaoNova.getId(), usuario.getId()));
            }
        } else {
            if (solicitacaoNova.getPrazo() != null) {
                // Prazos diferentes
                String nomeEvento = String.format("%s alterou o prazo para %s", usuario.getNome(),
                        stringDataFromDate(solicitacaoNova.getPrazo()));
                eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia),
                        solicitacaoNova.getId(), usuario.getId()));
            }
        }

        // Comparar solução
        boolean isSolucaoDiferente = false;
        if (solicitacaoAntiga.getDescricaoSolucao() != null) {
            if (solicitacaoNova.getDescricaoSolucao() != null) {
                if (!solicitacaoAntiga.getDescricaoSolucao().equals(solicitacaoNova.getDescricaoSolucao())) {
                    // Soluções diferentes
                    isSolucaoDiferente = true;
                }
            } else {
                // Soluções diferentes
                isSolucaoDiferente = true;
            }
        } else {
            if (solicitacaoNova.getDescricaoSolucao() != null) {
                // Soluções diferentes
                isSolucaoDiferente = true;
            }
        }
        if (isSolucaoDiferente) {
            String nomeEvento = String.format("%s alterou a solução", usuario.getNome());
            eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia), solicitacaoNova.getId(),
                    usuario.getId()));
        }

        // Comparar setor
        if (solicitacaoAntiga.getIdSetor() != solicitacaoNova.getIdSetor()) {
            RhDaoSetorImpl rhDaoSetor = new RhDaoSetorImpl();
            Setor setorAntigo = rhDaoSetor.getSetorById(solicitacaoAntiga.getIdSetor());
            Setor setorNovo = rhDaoSetor.getSetorById(solicitacaoNova.getIdSetor());

            String setorAntigoEvento = setorAntigo != null ? " de " + setorAntigo.getNome() : "";
            String setorNovoEvento = setorNovo != null ? " para " + setorNovo.getNome() : "";
            String nomeEvento = usuario.getNome() + " alterou o setor" + setorAntigoEvento + setorNovoEvento;
            eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia), solicitacaoNova.getId(),
                    usuario.getId()));
        }

        // Comparar atendente
        if (solicitacaoAntiga.getIdAtendente() != solicitacaoNova.getIdAtendente()) {
            GssDao.Usuario gssDaoUsuario = new GssDaoUsuarioImpl();
            Usuario atendenteNovo = gssDaoUsuario.getUsuarioByIdFuncionario(solicitacaoNova.getIdAtendente());

            String nomeEvento;
            if (solicitacaoAntiga.getIdAtendente() <= 0) {
                nomeEvento = String.format("%s delegou a solicitação", usuario.getNome());
                if (atendenteNovo != null) {
                    nomeEvento += " para " + atendenteNovo.getNome();
                }
            } else {
                Usuario atendenteAntigo = gssDaoUsuario.getUsuarioByIdFuncionario(solicitacaoAntiga.getIdAtendente());

                nomeEvento = String.format("%s alterou o atendente", usuario.getNome());
                if (atendenteAntigo != null) {
                    nomeEvento += " de " + atendenteAntigo.getNome();
                }
                if (atendenteNovo != null) {
                    nomeEvento += " para " + atendenteNovo.getNome();
                }
            }

            eventos.add(new Evento(nomeEvento, dataOcorrencia, stringDataHoraFromDate(dataOcorrencia), solicitacaoNova.getId(),
                    usuario.getId()));
        }

        return eventos;
    }

    /**
     * Converte uma data em uma equivalente string já formatada em dia/mês/ano HH:mm.
     *
     * @param date A data para ser convertida.
     * @return {@link String} convertida da data.
     */
    public static String stringDataHoraFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return format.format(date);
    }

    /**
     * Converte uma data em uma equivalente string já formatada em dia/mês/ano.
     *
     * @param date A data para ser convertida.
     * @return {@link String} convertida da data.
     */
    public static String stringDataFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return format.format(date);
    }

    /**
     * Gera as notificações para que sejam enviadas pelo {@link Notificador}.
     *
     * @param solicitacao Solicitação da notificação.
     * @param usuario     Usuário que realizou os eventos da notificação. Este usuário não será notificado.
     * @param eventos     Eventos ocorridos.
     * @return Lista de notificações.
     */
    public static ArrayList<Notificacao> gerarNotificacoes(Solicitacao solicitacao, Usuario usuario,
                                                           ArrayList<Evento> eventos) throws IllegalStateException {
        RhDao.Funcionario rhDaoFuncionario = new RhDaoFuncionarioImpl();
        ArrayList<Notificacao> notificacoes = new ArrayList<>();

        String assunto = "Atualização em uma solicitação que você está envolvido";
        StringBuilder conteudo = new StringBuilder("Ocorreram os seguintes eventos na solicitação \"" +
                solicitacao.getTitulo() + "\".\n");

        for (Evento evento : eventos) {
            conteudo.append("\n\t").append(evento.getNome());
        }

        String emailUsuario = rhDaoFuncionario.getEmailByIdFuncionario(usuario.getIdFuncionario());

        if (solicitacao.getIdAtendente() > 0 && usuario.getIdFuncionario() != solicitacao.getIdAtendente()) {
            String emailAtendente = rhDaoFuncionario.getEmailByIdFuncionario(solicitacao.getIdAtendente());
            notificacoes.add(new Notificacao(emailUsuario, emailAtendente, assunto, conteudo.toString()));
        }
        if (usuario.getIdFuncionario() != solicitacao.getIdSolicitante()) {
            String emailSolicitante = rhDaoFuncionario.getEmailByIdFuncionario(solicitacao.getIdSolicitante());
            notificacoes.add(new Notificacao(emailUsuario, emailSolicitante, assunto, conteudo.toString()));
        }
        if (usuario.getIdFuncionario() != solicitacao.getIdGestor()) {
            String emailGestor = rhDaoFuncionario.getEmailByIdFuncionario(solicitacao.getIdGestor());
            notificacoes.add(new Notificacao(emailUsuario, emailGestor, assunto, conteudo.toString()));
        }

        return notificacoes;
    }
}
