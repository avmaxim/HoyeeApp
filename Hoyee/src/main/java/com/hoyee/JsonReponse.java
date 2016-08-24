package com.hoyee;

/**
 * Created by andrei on 8/5/2016.
 */
public class JsonReponse<TData>{
    private TData data;
    private Boolean success;
    private String message;
    private Integer status;

    public JsonReponse(){}

    public JsonReponse(TData data, Boolean success, Integer status, String message){
        this.data = data;
        this.success = success;
        this.status = status;
        this.message = message;
    }

    private JsonReponse(TData data){ this.data = data; }

    public static <TData> JsonReponse GetAsJson(TData data){
        JsonReponse response = new JsonReponse();
        try{
            if(data == null)
                throw new NullPointerException();
            response.setData(data);
            response.setSuccess(true);
            response.setMessage(null);
            response.setStatus(200);
        }
        catch (Exception ex){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
            response.setStatus(500);
        }
        return response;
    }

    public TData getData(){return data;}
    public void setData(TData data){this.data = data;}

    public Boolean getSuccess(){return success;}
    public void setSuccess(Boolean success){this.success = success;}

    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}

    public Integer getStatus(){return status;}
    public void setStatus(Integer status){this.status = status;}
}
