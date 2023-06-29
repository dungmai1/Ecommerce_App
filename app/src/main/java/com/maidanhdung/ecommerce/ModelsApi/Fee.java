package com.maidanhdung.ecommerce.ModelsApi;

public class Fee {
    public int code;
    public String message;
    public DataFee data;

    public Fee(int code, String message, DataFee data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataFee getData() {
        return data;
    }

    public void setData(DataFee data) {
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
