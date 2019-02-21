package br.pucpr.gss.server.util;

import br.pucpr.gss.server.dao.GssDao;
import br.pucpr.gss.server.dao.GssDaoUsuarioImpl;
import br.pucpr.gss.server.dao.RhDaoSetorImpl;
import br.pucpr.gss.shared.model.Evento;
import br.pucpr.gss.shared.model.Setor;
import br.pucpr.gss.shared.model.Solicitacao;
import br.pucpr.gss.shared.model.Usuario;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe com métodos para diversos usos. Para usar, utilize-a de forma estática (<code>Util.nomeDoMetodo()</code>).
 */
public class Util {

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

        if (solicitacaoAntiga.getPrioridade().getIndice() != solicitacaoNova.getPrioridade().getIndice()) {
            String nomeEvento = String.format("%s alterou a prioridade de %s para %s", usuario.getNome(),
                    solicitacaoAntiga.getPrioridade().getNome(), solicitacaoNova.getPrioridade().getNome());
            eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
        }

        if (solicitacaoAntiga.getEstado().getIndice() != solicitacaoNova.getEstado().getIndice()) {
            String nomeEvento = String.format("%s alterou o estado de %s para %s", usuario.getNome(),
                    solicitacaoAntiga.getEstado().getNome(), solicitacaoNova.getEstado().getNome());
            eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
        }

        if (solicitacaoAntiga.getPrazo() != null) {
            if (solicitacaoNova.getPrazo() != null) {
                if (!solicitacaoAntiga.getPrazo().equals(solicitacaoNova.getPrazo())) {
                    // Prazos diferentes
                    String nomeEvento = String.format("%s alterou o prazo de %s para %s", usuario.getNome(),
                            solicitacaoAntiga.getPrazo().toString(), solicitacaoNova.getPrazo().toString());
                    eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
                }
            } else {
                // Prazos diferentes
                String nomeEvento = String.format("%s removeu o prazo", usuario.getNome());
                eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
            }
        } else {
            if (solicitacaoNova.getPrazo() != null) {
                // Prazos diferentes
                String nomeEvento = String.format("%s alterou o prazo para %s", usuario.getNome(),
                        solicitacaoNova.getPrazo().toString());
                eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
            }
        }

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
        }else {
            if (solicitacaoNova.getDescricaoSolucao() != null) {
                // Soluções diferentes
                isSolucaoDiferente = true;
            }
        }
        if (isSolucaoDiferente) {
            String nomeEvento = String.format("%s alterou a solução", usuario.getNome());
            eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
        }

        if (solicitacaoAntiga.getIdSetor() != solicitacaoNova.getIdSetor()) {
            RhDaoSetorImpl rhDaoSetor = new RhDaoSetorImpl();
            Setor setorAntigo = rhDaoSetor.getSetorById(solicitacaoAntiga.getIdSetor());
            Setor setorNovo = rhDaoSetor.getSetorById(solicitacaoNova.getIdSetor());

            String setorAntigoEvento = setorAntigo != null ? " de " + setorAntigo.getNome() : "";
            String setorNovoEvento = setorNovo != null ? " para " + setorNovo.getNome() : "";
            String nomeEvento = usuario.getNome() + " alterou o setor" + setorAntigoEvento + setorNovoEvento;
            eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
        }

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

            eventos.add(new Evento(nomeEvento, new Date(), solicitacaoNova.getId(), usuario.getId()));
        }

        return eventos;
    }
}