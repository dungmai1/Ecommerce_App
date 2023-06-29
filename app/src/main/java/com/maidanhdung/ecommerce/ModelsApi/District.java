package com.maidanhdung.ecommerce.ModelsApi;

public class District {
    public int code;
    public String message;
    public DataDistrict[] data;

    public District(int code, String message, DataDistrict[] data) {
        this.code = code;
        this.message = message;
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

    public DataDistrict[] getData() {
        return data;
    }

    public void setData(DataDistrict[] data) {
        this.data = data;
    }
}
