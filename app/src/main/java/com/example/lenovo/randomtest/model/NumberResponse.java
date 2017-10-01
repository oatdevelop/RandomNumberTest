package com.example.lenovo.randomtest.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumberResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("id")
    @Expose
    private Integer id;

    public boolean checkEndGame(int randomNumber){
        if(getRandomNumber() == randomNumber){
            return true;
        }else{
            return false;
        }
    }

    public int getRandomNumber(){
        return result.getRandom().getData().get(0);
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public class Random {

        @SerializedName("data")
        @Expose
        private List<Integer> data = null;
        @SerializedName("completionTime")
        @Expose
        private String completionTime;

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        public String getCompletionTime() {
            return completionTime;
        }

        public void setCompletionTime(String completionTime) {
            this.completionTime = completionTime;
        }

    }


    public class Result {

        @SerializedName("random")
        @Expose
        private Random random;
        @SerializedName("bitsUsed")
        @Expose
        private Integer bitsUsed;
        @SerializedName("bitsLeft")
        @Expose
        private Integer bitsLeft;
        @SerializedName("requestsLeft")
        @Expose
        private Integer requestsLeft;
        @SerializedName("advisoryDelay")
        @Expose
        private Integer advisoryDelay;

        public Random getRandom() {
            return random;
        }

        public void setRandom(Random random) {
            this.random = random;
        }

        public Integer getBitsUsed() {
            return bitsUsed;
        }

        public void setBitsUsed(Integer bitsUsed) {
            this.bitsUsed = bitsUsed;
        }

        public Integer getBitsLeft() {
            return bitsLeft;
        }

        public void setBitsLeft(Integer bitsLeft) {
            this.bitsLeft = bitsLeft;
        }

        public Integer getRequestsLeft() {
            return requestsLeft;
        }

        public void setRequestsLeft(Integer requestsLeft) {
            this.requestsLeft = requestsLeft;
        }

        public Integer getAdvisoryDelay() {
            return advisoryDelay;
        }

        public void setAdvisoryDelay(Integer advisoryDelay) {
            this.advisoryDelay = advisoryDelay;
        }

    }

}



