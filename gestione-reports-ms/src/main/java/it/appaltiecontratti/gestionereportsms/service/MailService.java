package it.appaltiecontratti.gestionereportsms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author andrea.chinellato
 * */

@Component(value = "mailService")
public class MailService {

    @Autowired
    private TemplateEngine templateEngine;

    public String buildMailSuccess(String codiceApplicativo, String msgSuccess) {
        Context context = new Context();
        context.setVariable("testoMail", msgSuccess);
        context.setVariable("codiceApplicativo", codiceApplicativo);

        return templateEngine.process("mailTemplateSuccess", context);
    }

    public String buildMailError(String codiceApplicativo, String msgError) {
        Context context = new Context();
        context.setVariable("testoMail", msgError);
        context.setVariable("codiceApplicativo", codiceApplicativo);

        return templateEngine.process("mailTemplateError", context);
    }
}
