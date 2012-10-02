package command.external;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Command {

    public int run() throws Exception {

        List<String> commands = new ArrayList<String>();
        commands.add("echo");
        commands.add("yeah");

        ProcessBuilder builder = new ProcessBuilder(commands);

        Map<String, String> environment = builder.environment();
        System.out.println(environment.toString());
        System.out.println("Cleaning environment...");
        environment.clear();
        System.out.println(environment.toString());

        Process process = builder.start();
        writeProcessOutput(process);
        return process.waitFor();
    }

    static void writeProcessOutput(Process process) throws Exception {
        InputStreamReader tempReader = new InputStreamReader(
                new BufferedInputStream(process.getInputStream()));
        BufferedReader reader = new BufferedReader(tempReader);
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            System.out.println(line);
        }
    }

}
