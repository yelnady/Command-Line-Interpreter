import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.lang.System.*;
/*
* s >> a.txt   help >> a.txt
* // cat C:\Users\Yusuf\Desktop\Terminal.txt C:\Users\Yusuf\Desktop\parser.txt C:\Users\Yusuf\Desktop\joe.txt
 * */
// TODO: how to append to a file ... if no args sent
public class Terminal {
    static public String current,File;
    static public boolean flag;
    static public int toFile;

    static {
        File = "";
        current = "F:\\";
        flag = false;
        toFile = 0;
    }

    static public void printToFile(String text) throws IOException {
        try {
            text+= lineSeparator();
            if (toFile == 1) {
                Path filePath = Paths.get(File);
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }
                Files.writeString(filePath, text, StandardOpenOption.CREATE,StandardOpenOption.APPEND);

            } else if (toFile == 2) {
                var br = new PrintWriter(new File(File));
                br.print(text);
                br.close();
            }

            toFile = 0;

        } catch (IOException e) {
            out.println("Da5l 7aga kwysa ya ebny");
        }

    }

    static void dashes() {
        out.println("-".repeat(30));
    }

    static void print(Vector <String> Lines) {

        if (!flag) {
            for (String line : Lines) {
                out.println(line);
            }
        } else {//do the "more" operations
            for (int i = 0; i < Lines.size(); i++) {
                if (i < 5) {
                    out.println(Lines.elementAt(i));
                } else {
                    try {
                        in.read();
                        in.skip(in.available());
                        out.print(Lines.elementAt(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        flag = false;
    }

    static void ls() {
        try {
            var f = new File(current).listFiles();
            var a = new Vector <String>();
            for (int i = 0; i < f.length; i++) {
                File file = f[i];
                a.add(file.toString());
            }
            print(a);
            if (toFile > 0) {
                printToFile(a.toString());
            }
        } catch (Exception e) {
            out.println("No such Directory");
        }
    }

    public static void cp(String sourcePath, String destinationPath) { //Doesn't copy Directories
        if (sourcePath == "" || destinationPath == "") {
            out.println("command cp should have two parameters.");
            return;
        }
        try {
            Files.copy(new File(sourcePath).toPath(), new File(destinationPath).toPath());
        } catch (IOException e) {
            out.println("No Such File or Directory");
        }
    }

    static public void clear() {
        String a = "\n";
        a = a.repeat(100);
        out.println(a);
    }

    static public void mv(String sourcePath, String destinationPath) {
        //Need to know how to test it
        if (sourcePath == "" || destinationPath == "") {
            out.println("command mv should have two parameters.");
            return;
        }
        try {
            out.println("moving");
            Files.move(new File(sourcePath).toPath(), new File(destinationPath).toPath());
        } catch (IOException e) {
            out.println("No Such File or Directory");
        }
    }

    static public void cd(String a) {
        if (a == "" || a==null) {
            current= "F:\\";
            return;
        }
        current = a; //we can try "C:\\"

    }

    static public void rm(String a) { // rm F:\joe.txt
        File x = new File(a);
        if (a == "") {
            out.println("command rm should have one parameter.");
            return;
        }
        if (x.delete()) out.println("Deletion is done successfuly");
        else out.println("No such Directory");

    }

    static public void cat(Vector <String> args) throws IOException {
        if (args.size()==0){
            out.println("command cat should have at least one parameter.");return;}
        List<String>  text =new Vector <>();var a=new String();
        try{
            for (var file:args) {
                text.addAll(Files.readAllLines(new File(file).toPath()));
        }
        // cat C:\Users\Yusuf\Desktop\Terminal.txt C:\Users\Yusuf\Desktop\parser.txt C:\Users\Yusuf\Desktop\joe.txt
        print(new Vector(text));
            if (toFile > 0) {
                printToFile(text.toString());
            }
        }
        catch (IOException e){
            out.println("keda brdo tgyb file msh mwgod");
        }
    }

    static public void mkdir(String a) {
        if (a == "") {
            out.println("command mkdir should have one parameter.");
            return;
        }
        File f = new File(a);
        f.mkdirs();
    }

    static public void rmdir(String a) {
        if (a == "") {
            out.println("command rmdir should have one parameter.");
            return;
        }
        File f = new File(a);
        f.delete();
    }

    static public void pwd() throws IOException {
        out.println("Current Directory is " + current);
        if (toFile>0){printToFile("Current Directory is " + current);}
    }

    static public void date() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").
                format(Calendar.getInstance().getTime());
        out.println(timeStamp);
    }

    public static void args(String a){   int idx = 0;
        try{idx = Parser.commands.valueOf(a).ordinal();
            switch (idx) {
                case 1:
                    out.println("Number of args is 1: Source Path");break;
                case 3:
                    out.println("Number of args is 2: Source Path, Destination Path");break;

                case 4:
                    out.println("Number of args is 2: Source Path, Destination Path");break;
                case 5:
                    out.println("Number of args is 1: The Directory to be removed");break;
                case 6:
                    out.println("Number of args is 1: The new Directory Path");break;
                case 7:
                    out.println("Number of args is 1: The Directory to be removed");break;
                case 8:
                    out.println("Many Number of args: The File Directories");break;
                case 11:
                    out.println("Number of args is 1: Command Instruction");break;
                case 12:case 13:case 0 :case 9 :case 2 :case 10:
                case 14: out.println("No Arguments for this command");break;
            }

        }
        catch (IllegalArgumentException e){
            System.out.println("eh el esm al3gyb da");return;}
    }

    public static void help() throws IOException {
        var output = new Vector <String>();
        output.add(String.format("%-7s %s -- %15s", "clear:", "Display Information about built in commands", "Takes no Arguments"));
        output.add(String.format("%-7s %s -- %15s", "cd:", "Changes the current Directory to NEW_DIR", "1st Argument:The new Directory Path"));
        output.add(String.format("%-7s %s -- %15s", "ls:", "List each given file or directory name", "Takes no Arguments"));
        output.add(String.format("%-7s %s -- %15s", "cp:", "If the last argument names an existing directory, cp copies each other given file into a file with " +
                "the same name in that directory.", "1st Argument: The Source path / 2nd Argument: The Destination Path"));
        output.add(String.format("%-7s %s -- %15s", "mv:", "If the last argument names an existing directory, mv moves each other given file into a file with the" +
                " same name in that directory.", "1st Argument: The Source path / 2nd Argument: THe Destination Path"));
        output.add(String.format("%-7s %-43s -- %15s", "rm", "removes each specified File or Directory", "1st Argument: The File or Directory path to be removed"));
        output.add(String.format("%-7s %-43s -- %15s", "mkdir:", "Creates a directory with the given name", "1st Argument: The New Directory Path even if parent doesn't exist"));
        output.add(String.format("%-7s %-43s -- %15s", "rmdir:", "Removes each given empty directory", "1st Argument: The Path to be removed"));
        output.add(String.format("%-7s %-43s -- %15s", "cat:", "Concatenate files and print on the standard output", "Arguments: The Files to be concatenated"));
        output.add(String.format("%-7s %-43s -- %15s", "more:", "display and scroll down the output in one direction only", "Takes no Arguments"));
        output.add(String.format("%-7s %-43s -- %15s", "pwd:", "Display current user directory", "Takes no Arguments"));
        output.add(String.format("%-7s %-43s -- %15s", "args:", "Display The arguments of the following cmd", "1st Argument: cmd-The command instruction"));
        output.add(String.format("%-7s %-43s -- %15s", "date:", "Display The Current Date and Time", "Takes no Arguments"));
        output.add(String.format("%-7s %-43s -- %15s", "help:", "Display Information about built in commands", "Takes no Arguments"));
        output.add(String.format("%-7s %-43s -- %15s", "exit:", "Exit the Shell", "Takes no Arguments"));
        out.println("Display Information about built in commands".length());
        print(output);
        if (toFile > 0) printToFile(output.toString());
    }
}
