package org.example;


public class Main {
    public static void main(String[] args) {

        showArguments(args);

        try {
            Args arg = new Args("l,p#,d*", args);
            boolean loggin = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');

            executeApplication(loggin, port, directory);

        }
        catch (ArgsException e){
            System.out.printf("Argument error: %s\n", e.errorMessage());
        }
    }

    private static void executeApplication(boolean loggin, int port, String directory) {
        boolean pass = true;
        if(!loggin){
            pass = false;
        }

        if (port != 8080) {
            pass = false;
        }

        if (!directory.equals("/dir")) {
            pass = false;
        }

        if(pass)
            System.out.println("Application passed!");
        else
            System.out.println("Application not pass");
    }

    static void showArguments(String[] args){
        System.out.println("Número de argumentos: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Argumento " + (i + 1) + ": " + args[i]);
        }
    }
}