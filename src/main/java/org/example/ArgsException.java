package org.example;
import java.lang.Exception;

import static org.example.ArgsException.ErrorCode.OK;

public class ArgsException extends Exception{

    private char errorArgumentId = '\0';
    private String errorParameter = null;
    private ErrorCode errorCode = OK;

    public ArgsException(){}
    public ArgsException(String message){
        super(message);
    }


    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
        this.errorArgumentId = errorArgumentId;
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public String errorMessage() {
        switch (errorCode){
            case OK:
                return "TILT: Should not get here";
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected.", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c.", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -%c expects an integer but was '%s'.",
                        errorArgumentId, errorParameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c.", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format("Argument -%c expects an double but was '%s'.",
                        errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%c.", errorArgumentId);
            case INVALID_ARGUMENT_NAME:
                return String.format("'-%c' is not a valid argument name.", errorArgumentId);
            case INVALID_ARGUMENT_FORMAT:
                return String.format("'-%c' is not a valid argument format.", errorArgumentId);
        }
        return "";
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }

    public char getErrorArgumentId() {
        return this.errorArgumentId;
    }

    public String getErrorParameter() {
        return this.errorParameter;
    }

    public enum ErrorCode{
        OK,
        INVALID_ARGUMENT_FORMAT,
        INVALID_ARGUMENT_NAME,
        UNEXPECTED_ARGUMENT,
        MISSING_STRING,
        MISSING_INTEGER,
        INVALID_INTEGER,
        MISSING_DOUBLE,
        INVALID_DOUBLE
    }
}
