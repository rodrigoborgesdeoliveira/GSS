package br.pucpr.gss.server.util;

import br.pucpr.gss.server.model.Notificacao;
import com.sendgrid.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notificador {

    private static Notificador instance = new Notificador();
    private static Logger logger = Logger.getLogger("Notificador");

    public static synchronized Notificador getInstance() {
        return instance;
    }

    public void enviarNotificacao(Notificacao notificacao) {
        Email remetente = new Email(notificacao.getRemetente());
        Email destinatario = new Email(notificacao.getDestinatario());
        Content conteudo = new Content("text/plain", notificacao.getDescricao());

        Mail email = new Mail(remetente, notificacao.getAssunto(), destinatario, conteudo);
        SendGrid sendgrid = new SendGrid(Keys.SENDGRID_API_KEY);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(email.build());
            Response response = sendgrid.api(request);
            logger.log(Level.INFO, "Response: status code: " + response.getStatusCode());
            logger.log(Level.INFO, "Response: body: " + response.getBody());
            logger.log(Level.INFO, "Response: headers: " + response.getHeaders());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Erro ao enviar email", e);
        }
    }

    private Notificador() {
    }
}
