package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.example.ArgsException.ErrorCode.INVALID_INTEGER;
import static org.example.ArgsException.ErrorCode.MISSING_INTEGER;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {
    private int intValue = 0;
    @Override
    public void set(ListIterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        }catch (NoSuchElementException e){
            throw new ArgsException(MISSING_INTEGER);
        }catch (NumberFormatException e){
            throw new ArgsException(INVALID_INTEGER, parameter);
        }
    }

    public Object get() {
        return intValue;
    }
}
