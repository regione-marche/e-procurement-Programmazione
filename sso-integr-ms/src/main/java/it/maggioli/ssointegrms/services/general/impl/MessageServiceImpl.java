package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.domain.WMessageIn;
import it.maggioli.ssointegrms.domain.WMessageOut;
import it.maggioli.ssointegrms.domain.WMessageOutRec;
import it.maggioli.ssointegrms.domain.WMessageOutRecPK;
import it.maggioli.ssointegrms.repositories.WMessageInRepository;
import it.maggioli.ssointegrms.repositories.WMessageOutRecRepository;
import it.maggioli.ssointegrms.repositories.WMessageOutRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@Service
public class MessageServiceImpl extends BaseService implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private WMessageInRepository wMessageInRepository;

    @Autowired
    private WMessageOutRepository wMessageOutRepository;

    @Autowired
    private WMessageOutRecRepository wMessageOutRecRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertMessageToUsersAdministrator(final String subject, final String body, final Long mittenteId) {

        LOGGER.debug(
                "Execution start MessageServiceImpl::insertMessageToUsersAdministrator for subject [ {} ], body [ {} ] and mittenteId [ {} ]",
                subject, body, mittenteId);

        if (mittenteId == null)
            throw new IllegalArgumentException("mittenteId null");

        Long idMessageOut = wMessageOutRepository.getMaxId();

        if (idMessageOut == null || idMessageOut == 0L)
            idMessageOut = 1L;
        else
            idMessageOut++;

        Date now = new Date();

        WMessageOut out = new WMessageOut();
        out.setId(idMessageOut);
        out.setSysconSender(mittenteId);
        out.setSubject(subject);
        out.setBody(body);
        out.setDate(now);

        wMessageOutRepository.save(out);

        List<Long> listaAccountGestoriProfilo = userRepository.getAccountGestoriProfilo();

        if (listaAccountGestoriProfilo != null && listaAccountGestoriProfilo.size() > 0) {
            for (Long sysconDestinatario : listaAccountGestoriProfilo) {

                Long idMessageIn = wMessageInRepository.getMaxId();
                if (idMessageIn == null || idMessageIn == 0L)
                    idMessageIn = 1L;
                else
                    idMessageIn++;

                WMessageIn in = new WMessageIn();
                in.setId(idMessageIn);
                in.setSysconSender(mittenteId);
                in.setSysconRecipient(sysconDestinatario);
                in.setSubject(subject);
                in.setBody(body);
                in.setDate(now);
                in.setRead(0);

                wMessageInRepository.save(in);

                WMessageOutRec rec = new WMessageOutRec();
                rec.setId(new WMessageOutRecPK(idMessageIn, idMessageOut));
                rec.setSysconRecipient(sysconDestinatario);

                wMessageOutRecRepository.save(rec);
            }
        }

    }
}
