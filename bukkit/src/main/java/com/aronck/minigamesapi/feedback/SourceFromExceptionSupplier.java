package com.aronck.minigamesapi.feedback;

public class SourceFromExceptionSupplier implements SourceSupplier{

    private Exception exception;

    public SourceFromExceptionSupplier(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getSourceString() {
        return null;
    }

}
