package com.aronck.minigamesapi;

import java.util.Arrays;

public final class InternalDataBridgeKey {

    private InternalDataBridgeKey(){}

    InternalDataBridgeKey(Main main) throws IllegalAccessException {
        if(main==null)throw new IllegalAccessException("Not allowed to create data bridge Key externally!");
    }

    private final boolean validate(){
        Exception e = new Exception("");
        if(Arrays.stream(e.getStackTrace()).map(StackTraceElement::getClassName).anyMatch(s -> s.contains("com.aronck.minigamesapi.devtools"))){
            return true;
        }
        return false;
    }

    public boolean isValidated() {
        return validate();
    }
}
