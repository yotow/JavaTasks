package lesson3.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class GameLogger {

    private static final int BUFFER = 40;
    private static final File tempFile = new File(OSUtils.getTempFile());
    private static final StringBuilder sb = new StringBuilder();

    static void writeLog(String log) {
        sb.append(getTime()).append(": ").append(log).append("\n");
        if (sb.length() > BUFFER && tempFile.exists()) {
            write();
        }
    }

    private static void write() {
        try (FileWriter writer = new FileWriter(tempFile, true)) {
            writer.write(sb.toString());
            sb.setLength(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTime() {
        GregorianCalendar cannes = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("_dd_MM_yyyy_hh.mm.ss");
        return df.format(cannes.getTime());
    }
}