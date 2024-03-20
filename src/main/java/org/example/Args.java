package org.example;

import org.example.marshalers.*;

import java.util.*;

import static org.example.ArgsException.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static org.example.ArgsException.ErrorCode.INVALID_ARGUMENT_NAME;
import static org.example.ArgsException.ErrorCode.UNEXPECTED_ARGUMENT;

public class Args {

    private String schema;
    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<Character, ArgumentMarshaler>();
    private Set<Character> argsFound = new HashSet<Character>();;
    private ListIterator<String> currentArgument;
    private List<String> argsList;

    public Args(String schema, String[] args) throws ArgsException {

        this.schema = schema;
        argsList = Arrays.asList(args);

        parse();

    }

    private void parse() throws ArgsException{
        parseSchema(schema);
        parseArguments();
    }

    private void parseSchema(String schema) throws ArgsException{
        for(String element : schema.split(",")){
            if(!element.isEmpty())
                parseSchemaElement(element.trim());
        }
    }

    private void parseSchemaElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);

        if(elementTail.isEmpty())
            marshalers.put(elementId, new BooleanArgumentMashaler());
        else if (elementTail.equals("*"))
            marshalers.put(elementId, new StringArgumentMashaler());
        else if (elementTail.equals("#"))
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        else if (elementTail.equals("##"))
            marshalers.put(elementId, new DoubleArgumentMarshaler());
        else if (elementTail.equals("[*]"))
            marshalers.put(elementId, new StringArrayArgumentMarshaler());
        else
            throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);

    }

    private void validateSchemaElementId(char elementId) throws ArgsException{
        if(!Character.isLetter(elementId)){
            throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
        }
    }

    private void parseArguments() throws ArgsException {
        for(currentArgument = argsList.listIterator(); currentArgument.hasNext();){
            String arg = currentArgument.next();
            parseArgument(arg);
        }
    }

    private void parseArgument(String arg) throws ArgsException{
        if(arg.startsWith("-"))
            parseElements(arg);
    }

    private void parseElements(String arg) throws ArgsException{
        for (int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }

    private void parseElement(char argChar) throws ArgsException{
        if(setArgument(argChar)){
            argsFound.add(argChar);
        }
        else{
            throw new ArgsException(UNEXPECTED_ARGUMENT,
                    argChar, null);
        }
    }


    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshalers.get(argChar);
        if(m == null)
            return false;
        try{
            m.set(currentArgument);
            return true;
        }catch (ArgsException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
    }

    public boolean has(char arg){
        return argsFound.contains(arg);
    }

    public int cardinality(){
        return argsFound.size();
    }

    public boolean getBoolean(char arg){
        return getValue(arg, Boolean.class);
    }
    public String getString(char arg) {
        return getValue(arg, String.class);
    }

    public int getInt(char arg) {
        return getValue(arg, Integer.class);
    }

    public double getDouble(char arg) {
        return getValue(arg, Double.class);
    }

    private <T> T getValue(char arg, Class<T> type) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            if (am == null)
                return defaultValue(type);
            return type.cast(am.get());
        } catch (Exception e) {
            return defaultValue(type);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T defaultValue(Class<T> type) {
        if (type == Boolean.class)
            return (T) Boolean.FALSE;
        if (type == String.class)
            return (T) "";
        if (type == Integer.class)
            return (T) Integer.valueOf(0);
        if (type == Double.class)
            return (T) Double.valueOf(0.0);
        return null;
    }
}
