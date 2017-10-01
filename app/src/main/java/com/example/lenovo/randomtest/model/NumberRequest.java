package com.example.lenovo.randomtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumberRequest {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("params")
    @Expose
    private Params params;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public static class Params {

        @SerializedName("apiKey")
        @Expose
        private String apiKey;
        @SerializedName("n")
        @Expose
        private Integer n;
        @SerializedName("min")
        @Expose
        private Integer min;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("replacement")
        @Expose
        private Boolean replacement;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public Integer getN() {
            return n;
        }

        public void setN(Integer n) {
            this.n = n;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Boolean getReplacement() {
            return replacement;
        }

        public void setReplacement(Boolean replacement) {
            this.replacement = replacement;
        }

    }

}


