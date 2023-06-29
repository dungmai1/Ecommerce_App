package com.maidanhdung.ecommerce.ModelsApi;

public class Province {
    public int code;
    public String message;
    public DataProvince[] data;

    public Province(int code, String message, DataProvince[] data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataProvince[] getData() {
        return data;
    }

    public void setData(DataProvince[] data) {
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
