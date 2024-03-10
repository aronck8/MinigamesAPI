package com.aronck.minigamesapi.utils;

public class FeedbackUtils {

    public static StackTraceElement getFirstExternalStackTraceElement(StackTraceElement[] elements){
        for (int i = 0;i<elements.length;i++){
            if(!elements[i].getClassName().contains("com.aronck.minigamesapi")){
                return elements[i];
            }
        }
        return elements[1];
    }
}
