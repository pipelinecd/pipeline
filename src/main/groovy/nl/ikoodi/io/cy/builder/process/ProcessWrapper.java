package nl.ikoodi.io.cy.builder.process;

import java.io.*;
import java.util.List;

public class ProcessWrapper {
    private StringWriter infos;
    private StringWriter errors;
    private int status;

    public ProcessWrapper(File directory, List command) throws Exception {
        infos = new StringWriter();
        errors = new StringWriter();
        ProcessBuilder pb = new ProcessBuilder(command);
        if (directory != null) {
            pb.directory(directory);
        }
        Process process = pb.start();
        StreamBoozer seInfo = new StreamBoozer(process.getInputStream(), new PrintWriter(infos, true));
        StreamBoozer seError = new StreamBoozer(process.getErrorStream(), new PrintWriter(errors, true));
        seInfo.start();
        seError.start();
        status = process.waitFor();
    }

    public ProcessWrapper(List command) throws Exception {
        this(null, command);
    }

    public String getErrors() {
        return errors.toString();
    }

    public String getInfos() {
        return infos.toString();
    }

    public int getStatus() {
        return status;
    }

    class StreamBoozer extends Thread {
        private InputStream in;
        private PrintWriter pw;

        StreamBoozer(InputStream in, PrintWriter pw) {
            this.in = in;
            this.pw = pw;
        }

        @Override
        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = br.readLine()) != null) {
                    pw.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}