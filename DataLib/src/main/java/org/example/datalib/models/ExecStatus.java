package org.example.datalib.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ExecStatus implements Serializable {

    private boolean isOk = true;
    private boolean isGritting = false;
    private String message = "";
    private boolean isNext = false;
    private boolean isStopServer = false;

    public ExecStatus(boolean isOk, String message) {
        this.isOk = isOk;
        this.message = message;
    }

    public ExecStatus(String message) {
        this.message = message;
    }

    public ExecStatus(){}


    public boolean isGritting() {
        return isGritting;
    }

    public void setGritting(boolean gritting) {
        isGritting = gritting;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isStopServer() {
        return isStopServer;
    }

    public void setStopServer(boolean stopServer) {
        isStopServer = stopServer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
