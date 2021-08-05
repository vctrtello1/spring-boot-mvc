package com.victortello.ws.webservice;

public class OperationStatusModel {

    private String operationResult;
    private String operationName;

    public String getOperationResult() {
        return operationResult;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

}
