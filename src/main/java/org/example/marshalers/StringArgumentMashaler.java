package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.example.ArgsException.ErrorCode.MISSING_STRING;

public class StringArgumentMashaler implements ArgumentMarshaler {

    private String stringValue;

    @Override
    public void set(ListIterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        }catch (NoSuchElementException e){
            throw new ArgsException(MISSING_STRING);
        }
    }

    public Object get() {
        return stringValue;
    }

}
