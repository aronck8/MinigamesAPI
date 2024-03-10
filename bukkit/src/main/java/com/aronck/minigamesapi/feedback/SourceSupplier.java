package com.aronck.minigamesapi.feedback;

import java.util.function.Supplier;

public interface SourceSupplier extends Supplier<String> {

    String getSourceString();

    @Override
    default String get() {
        return getSourceString();
    }
}
