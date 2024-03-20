package org.example.marshalers;

import org.example.ArgsException;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.example.ArgsException.ErrorCode.INVALID_DOUBLE;
import static org.example.ArgsException.ErrorCode.MISSING_DOUBLE;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {

    private double doubleValue;
    @Override
    public void set(ListIterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        }catch (NoSuchElementException e){
            throw new ArgsException(MISSING_DOUBLE);
        }catch (NumberFormatException e){
            throw new ArgsException(INVALID_DOUBLE, parameter);
        }
    }

    public Object get() {
        return doubleValue;
    }
}
