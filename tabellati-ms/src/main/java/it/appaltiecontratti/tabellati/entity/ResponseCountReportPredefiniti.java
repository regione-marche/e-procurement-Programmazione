package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseCountReportPredefiniti implements Serializable {

    private static final long serialVersionUID = -7027950263853844056L;

    private String done;
    private Object response;
    private List<String> messages = new ArrayList<>();
    private String detailedErrorQuery;

    public String getDone() { return done; }
    public void setDone(String done) { this.done = done; }

    public Object getResponse() { return response; }
    public void setResponse(Object response) { this.response = response; }

    public List<String> getMessages() { return messages; }
    public void setMessages(List<String> messages) { this.messages = messages; }

    public String getDetailedErrorQuery() { return detailedErrorQuery; }
    public void setDetailedErrorQuery(String detailedErrorQuery) { this.detailedErrorQuery = detailedErrorQuery; }
}
