package it.apkappa.services;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

public class AuthHeaderSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private final String username;
    private final String password;

    public AuthHeaderSOAPHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) {
            try {
                SOAPMessage message = context.getMessage();
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                if (header == null) {
                    header = envelope.addHeader();
                }

                // Namespace
                String namespace = "http://services.apkappa.it/";
                String prefix = "ser";

                // <ser:AuthenticationDetails>
                Name authHeaderName = envelope.createName("AuthenticationDetails", prefix, namespace);
                SOAPHeaderElement authHeader = header.addHeaderElement(authHeaderName);

                // <ser:UserName>
                SOAPElement userElem = authHeader.addChildElement("UserName", prefix);
                userElem.addTextNode(username);

                // <ser:Password>
                SOAPElement passElem = authHeader.addChildElement("Password", prefix);
                passElem.addTextNode(password);

                message.saveChanges();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) { return true; }

    @Override
    public void close(MessageContext context) {}

    @Override
    public Set<QName> getHeaders() { return null; }
}
