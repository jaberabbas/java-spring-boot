package com.task.model;


public enum ErrorCodes {
    FUNC001("FUNC001", "functional error in task api"),
    TEC001("TEC001", "technical error in task api");
    private  String code;
    private  String desc;
    ErrorCodes(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
