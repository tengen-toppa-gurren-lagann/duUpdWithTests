package du;
import org.kohsuke.args4j.*;

import java.util.ArrayList;
import java.util.List;
//import java.io.IOException;


public class DuLauncher {

    @Option(name = "-h", required=false, usage = "readable format")
    private boolean readable = false;

    @Option(name = "-c", required=false, usage = "summed size")
    private boolean summed = false;

    @Option(name = "--si", required=false, usage = "all units size comes in 1000 format instead of 1024")
    private boolean si = false;

    @Argument
    private List<String> filesList = new ArrayList<>();

    public static int result = 0; // 0 - успех, 1 - ошибка

    public static void main(String[] args) {
        new DuLauncher().launch(args);
        System.exit(DuLauncher.result);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (filesList.isEmpty()) {
//                System.err.println("Error entering arguments (for correct input, see the example)");
//                System.err.println("\nExample: du [-h] [-c] [--si] file1 file2 file3 ...");
//                parser.printUsage(System.err);
                System.out.println("Error entering arguments (for correct input, see the example)");
                System.out.println("\nExample: du [-h] [-c] [--si] file1 file2 file3 ...");
                parser.printUsage(System.out);
                throw new IllegalArgumentException("");
            }
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            System.out.println("\nExample: du [-h] [-c] [--si] file1 file2 file3 ...");
            parser.printUsage(System.out);
            return;
        }
        catch (IllegalArgumentException e) {
            System.exit(0);
        }

        DiskUsage du = new DiskUsage(filesList);

        try {
            result = du.calcSizeAndWriteResult(readable, summed, si);
//            System.out.println("result=" + result); // для контроля
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}