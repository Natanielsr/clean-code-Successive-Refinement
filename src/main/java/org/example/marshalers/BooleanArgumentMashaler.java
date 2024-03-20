package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;

public class BooleanArgumentMashaler implements ArgumentMarshaler {
    private boolean boleanValue = false;

    @Override
    public void set(ListIterator<String> currentArgument) throws ArgsException {
        boleanValue = true;
    }

    public Object get() {
        return boleanValue;
    }
}
