package lesson3.tasks;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {

    static final String SUFFIX = "arch_";

    static void zipFiles(String pathToZip, List<String> paths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for (String path :
                    paths) {
                ZipEntry zipEntry = new ZipEntry(SUFFIX + path.substring(OSUtils.getSaveGamesDir().length() + 1));
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

    // обертка для соответсвия заданию
    public static void openZip(String pathToZip, String pathToDestFolder) {
        File absPath = new File(pathToZip);
        File absDestPath = new File(pathToDestFolder);
        unZipFiles(absPath.getAbsolutePath(), absDestPath.getAbsolutePath());
    }

    private static void unZipFiles(String pathToZip, String pathToDestFolder) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToZip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                try (BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(pathToDestFolder + name.replace(SUFFIX, ""))
                )) {
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        bos.write(c);
                    }
                    bos.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
