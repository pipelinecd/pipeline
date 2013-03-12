package nl.ikoodi.io.cy.builder.process;

import java.io.*;
import java.util.List;

public class ProcessWrapper {
    private StringWriter stdout;
    private StringWriter errOut;
    private int exitStatus;

    public ProcessWrapper(final File directory, final List<String> command)
            throws IOException, InterruptedException {
        stdout = new StringWriter();
        errOut = new StringWriter();
        ProcessBuilder pb = new ProcessBuilder(command);
        if (directory != null) {
            pb.directory(directory);
        }
        Process process = pb.start();
        StreamBoozer sbStdOut = new StreamBoozer(process.getInputStream(), new PrintWriter(stdout, true));
        StreamBoozer sbErrOut = new StreamBoozer(process.getErrorStream(), new PrintWriter(errOut, true));
        sbStdOut.start();
        sbErrOut.start();
        exitStatus = process.waitFor();
    }

    public ProcessWrapper(final List<String> command) throws IOException, InterruptedException {
        this(null, command);
    }

    public String getErrOut() {
        return errOut.toString();
    }

    public String getStdout() {
        return stdout.toString();
    }

    public int getExitStatus() {
        return exitStatus;
    }

    private class StreamBoozer extends Thread {
        private InputStream in;
        private PrintWriter out;

        public StreamBoozer(final InputStream in, final PrintWriter out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = br.readLine()) != null) {
                    out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
