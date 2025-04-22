package org.example.datalib.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SendToken {
    private String[] tokens;

    public SendToken(String[] tokens) {
        this.tokens = tokens;
    }

    public SendToken(){}

    public String[] getTokens() {
        return tokens;
    }

    @JsonIgnore
    public String getFirstToken(){
        return tokens.length > 0 ? tokens[0] : "";
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}
