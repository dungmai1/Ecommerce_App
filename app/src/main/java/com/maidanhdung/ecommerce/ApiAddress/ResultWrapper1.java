package com.maidanhdung.ecommerce.ApiAddress;

public class ResultWrapper1 {
    public District[] results;

    public ResultWrapper1(District[] results) {
        this.results = results;
    }

    public District[] getResults() {
        return results;
    }

    public void setResults(District[] results) {
        this.results = results;
    }
}
