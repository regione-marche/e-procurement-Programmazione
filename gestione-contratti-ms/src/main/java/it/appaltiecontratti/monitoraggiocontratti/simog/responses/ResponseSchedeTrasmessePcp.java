package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RicercaSchedeTrasmessePcpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;

import java.util.List;

public class ResponseSchedeTrasmessePcp extends BaseResponsePcp {

    private static final long serialVersionUID = -8718095408944463381L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Lista schede trasmesse Pcp")
    List<RicercaSchedeTrasmessePcpEntry> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value="Numero totale delle schede estraibili trasmesse a Pcp")
    private int totalCount;

    public void setData(List<RicercaSchedeTrasmessePcpEntry> data) {
        this.data = data;
    }
    public List<RicercaSchedeTrasmessePcpEntry> getData() {
        return data;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getTotalCount() {
        return totalCount;
    }
}
