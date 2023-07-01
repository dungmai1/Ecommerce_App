package com.maidanhdung.ecommerce.ApiAddress;

public class ResultWrapper {
    public Province[] results;
    public ResultWrapper(Province[] results) {
        this.results = results;
    }
    public Province[] getResults() {
        return results;
    }

    public void setResults(Province[] results) {
        this.results = results;
    }


}