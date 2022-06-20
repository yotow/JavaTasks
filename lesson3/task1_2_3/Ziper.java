package lesson3.task1_2_3;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ziper {

    static void zipFiles(String pathToZip, List<String> paths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for (String path :
                    paths) {
                ZipEntry zipEntry = new ZipEntry("arch_" + path.substring(OSUtils.getSaveGamesDir().length() + 1));
                zos.putNextEntry(zipEntry);
                try (FileInputStream fis = new FileInputStream(path)) {
                    byte[] buffer = new byte[fis.available()];
                    while (fis.read(buffer) != -1) {
                        zos.write(buffer);
                    }
                    zos.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}