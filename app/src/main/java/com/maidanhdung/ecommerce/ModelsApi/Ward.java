package com.maidanhdung.ecommerce.ModelsApi;

public class Ward {
    public int code;
    public String message;
    public DataWard[] data;

    public Ward(int code, String message, DataWard[] data) {
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

    public DataWard[] getData() {
        return data;
    }

    public void setData(DataWard[] data) {
        this.data = data;
    }
}
