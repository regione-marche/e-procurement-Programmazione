package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.FaseVariazioneAggiudicatariEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseVarianteEntry;

public class ResponseFaseVariazioneAggiudicatari  extends BaseResponse{

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Dettaglio fase variante")
    FaseVariazioneAggiudicatariEntry data;

    public FaseVariazioneAggiudicatariEntry getData() {
        return data;
    }

    public void setData(FaseVariazioneAggiudicatariEntry data) {
        this.data = data;
    }

}
