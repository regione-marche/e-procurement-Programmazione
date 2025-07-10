package it.appaltiecontratti.autenticazione.bl;

import it.appaltiecontratti.autenticazione.entity.RegistrationAdminMailModel;
import it.appaltiecontratti.autenticazione.entity.RegistrationMailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component(value = "mailService")
public class MailService {

    @Autowired
    private TemplateEngine templateEngine;

    public String buildMail(RegistrationMailModel registrationMail) {
        Context context = new Context();
        context.setVariable("nominativoTecnico", registrationMail.getNominativoTecnico());
        context.setVariable("cognomeTecnico", registrationMail.getCognomeTecnico());
        context.setVariable("cfEnte", registrationMail.getCfEnte());
        context.setVariable("nomeEnte", registrationMail.getNomeEnte());
        context.setVariable("tipologia", registrationMail.getTipologia());
        context.setVariable("indirizzoEnte", registrationMail.getIndirizzoEnte());
        context.setVariable("telefono", registrationMail.getTelefono());
        context.setVariable("fax", registrationMail.getFax());
        context.setVariable("emailEnte", registrationMail.getEmailEnte());
        context.setVariable("cfUtente", registrationMail.getCfUtente());
        context.setVariable("password", registrationMail.getPassword());
        context.setVariable("emailUtente", registrationMail.getEmailUtente());
        context.setVariable("telefonoUtente", registrationMail.getTelefonoUtente());
        context.setVariable("listaApplicativi", registrationMail.getListaApplicativi());
        context.setVariable("messaggioMail", registrationMail.getMessaggioMail());
        context.setVariable("statoRegistrazioneUtente", registrationMail.getStatoRegistrazioneUtente());
        return templateEngine.process("mailTemplate", context);
    }

    public String buildAdminMail(RegistrationAdminMailModel registrationAdminMail) {
        Context context = new Context();

        context.setVariable("nomeUtente", registrationAdminMail.getNomeUtente());
        context.setVariable("listaApplicativi", registrationAdminMail.getListaApplicativi());
        context.setVariable("cognomeUtente", registrationAdminMail.getCognomeUtente());
        context.setVariable("telefonoUtente", registrationAdminMail.getTelefonoUtente());
        context.setVariable("emailUtente", registrationAdminMail.getEmailUtente());
        context.setVariable("messaggioMail", registrationAdminMail.getMessaggioMail());
        context.setVariable("statoRegistrazioneUtente", registrationAdminMail.getStatoRegistrazioneUtente());
        context.setVariable("nomeEnte", registrationAdminMail.getNomeEnte());
        return templateEngine.process("mailAdminTemplate", context);
    }
}
