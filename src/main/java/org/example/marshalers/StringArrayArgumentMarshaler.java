package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
    @Override
    public void set(ListIterator<String> currentArgument) throws ArgsException {

    }

    public Object get() {
        return null;
    }
}
