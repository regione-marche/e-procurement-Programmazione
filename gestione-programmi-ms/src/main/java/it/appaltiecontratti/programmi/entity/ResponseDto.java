package it.appaltiecontratti.programmi.entity;

public class ResponseDto extends BaseResponse{

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
