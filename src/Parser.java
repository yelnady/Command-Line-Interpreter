import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Parser {
    static enum commands {clear,cd,ls,cp,mv,rm,mkdir,rmdir,cat,more,pwd,args,date,help,exit}
    private static Vector <String> args;
    private static String cmd;

    static {
        args = new Vector <>();
    }

    public static void run(int i) throws IOException {
        switch (i) {
            case -1:
                final var setPlainText = "\033[0;0m";
                final var setBoldText = "\033[0;1m";
                System.out.println(setBoldText + "There is no such Command" + setPlainText);
                break;
            case 0:
                Terminal.clear();
                break;
            case 1:
                Terminal.cd(args.size() > 0 ? args.elementAt(0) : "");
                break;
            case 2:
                Terminal.ls();
                break;
            case 3:
                Terminal.cp(args.size() > 0 ? args.elementAt(0) : "", args.size() > 1 ? args.elementAt(1) : "");
                break;
            case 4:
                Terminal.mv(args.size() > 0 ? args.elementAt(0) : "", args.size() > 1 ? args.elementAt(1) : "");
                break;
            case 5:
                Terminal.rm(args.size() > 0 ? args.elementAt(0) : "");
                break;
            case 6:
                Terminal.mkdir(args.size() > 0 ? args.elementAt(0) : "");
                break;
            case 7:
                Terminal.rmdir(args.size() > 0 ? args.elementAt(0) : "");
                break;
            case 8:
                Terminal.cat(args);
                break;
            case 9:
                Terminal.flag = true;
                break;
            case 10:
                Terminal.pwd();
                break;
            case 11:
                Terminal.args(args.size() > 0 ? args.elementAt(0) : "");
                break;
            case 12:
                Terminal.date();
                break;
            case 13:
                Terminal.help();
                break;
        }
    }

    private static int Parse(String command) throws IOException {
        //to parse first to split using "|" then split using spaces and we will not accept the spaces
        command = command.strip();
        command = command.replace('\'', '"');
        command = command.replace('|', '#');
        if (command.length() == 0) {
            return -1;
        }
        if ((command.charAt(0) == '"') && (command.charAt(command.length() - 1) != '"'))
            System.out.println("Error Parsing the Command");
        else if ((command.charAt(0) == '"') && (command.charAt(command.length() - 1) == '"')) {
            command = command.substring(1, command.length() - 1); //delete the quotes begin and end
            command = command.strip();
        }
        if (command.indexOf("more") > 0) {
            Terminal.flag = true;
        } //will be checked in the other commands
        int idx = -1; //idx is set to -1 to be returned if we didn't find the command
        String[] Instructions = command.split("#");//replaced with # so we can split
        for (int i = 0; i < Instructions.length; i++) {
            args = new Vector <>();
            Instructions[i] = Instructions[i].strip();
            if (command.indexOf(">>") > 0) {
                Terminal.toFile = 1;
            } else if (command.indexOf(">") > 0) {
                {
                    Terminal.toFile = 2;
                }
            }
            //to remove white spaces of entire commands
            var Values = Instructions[i].split(" ");
            for (int j = 0; j < Values.length; j++) {
                if (j == 0) {
                    cmd = Values[0]; //first word will be the command
                }
                else {
                    args.add(Values[j]);
                }
                try {
                    idx = commands.valueOf(cmd).ordinal();//get the number of command to be executed from the enum
                }
                catch (IllegalArgumentException e) {//Catch if the command isn't in our list
                    run(-1);
                    return -1;
                }
                if (idx == 14) System.exit(1);
            }
            if (Terminal.toFile > 0) Terminal.File = args.lastElement();
            run(idx);
        }
        return idx;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n;
        while (true) {
            System.out.print(Terminal.current + " ");
            var a = input.nextLine();
            Parse(a);
        }
    }
}
