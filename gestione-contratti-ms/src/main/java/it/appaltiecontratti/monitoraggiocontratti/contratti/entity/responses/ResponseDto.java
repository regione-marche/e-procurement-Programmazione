package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

public class ResponseDto extends BaseResponse{

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
