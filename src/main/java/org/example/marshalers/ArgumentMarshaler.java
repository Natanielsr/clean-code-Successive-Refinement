package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;

public interface ArgumentMarshaler {

    void set(ListIterator<String> currentArgument) throws ArgsException;
    public Object get();
}
