package lesson3.task1_2_3;

import lesson3.task1_2_3.exceptions.FileCreationException;
import lesson3.task1_2_3.exceptions.FolderCreationException;
import lesson3.task1_2_3.exceptions.WorkDirExistAndPermException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static lesson3.task1_2_3.GameLogger.writeLog;

public class OSUtils {
    static final String OS = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    static final String[] DIRS_IN_GAMES = {"/src", "/res", "/saveGames", "/temp"};
    static final String[] DIRS_IN_GAMES_RES = {"/res/drawables", "/res/vectors", "/res/icons"};
    static final String[] DIRS_IN_GAMES_SRC = {"/src/main", "/src/test"};
    static final String[] FILES_IN_GAMES_SRC_MAIN = {"/src/main/Utils.java", "/src/main/Main.java"};
    static final String[] FILES_IN_GAMES_TEMP = {"/temp.txt"};

    private static char delim = '/';
    private static String workDir;
    private static String saveGamesDir;
    private static String saveGamesZip;
    private static String tempDir;
    private static String tempFile;

    static void init() throws FolderCreationException {
        if (OS.contains("windows")) {
            delim = '\\';
            workDir = WorkingDirectory.WINDOWS.getValue();
            replaceSlash(DIRS_IN_GAMES);
            replaceSlash(DIRS_IN_GAMES_RES);
            replaceSlash(DIRS_IN_GAMES_SRC);
            replaceSlash(FILES_IN_GAMES_SRC_MAIN);
            replaceSlash(FILES_IN_GAMES_TEMP);
        } else if (OS.contains("linux")) {
            workDir = WorkingDirectory.LINUX.getValue();
        } else if (OS.contains("mac")) {
            workDir = WorkingDirectory.MAC.getValue();
        }

        tempDir = pathConstructor(workDir, DIRS_IN_GAMES, "temp");
        saveGamesDir = pathConstructor(workDir, DIRS_IN_GAMES, "saveGames");
        saveGamesZip = saveGamesDir + delim + "saved.zip";
        tempFile = pathConstructor(getTempDir(), FILES_IN_GAMES_TEMP, "temp");
        try {
            createFile(tempFile);

            writeLog("Операционная система " + OS + " определена");
            writeLog("Структура каталогов определена");
            writeLog("Файл логов создан");

            checkExistAndPerm(workDir);
            writeLog("Директория Games существует, достаточно прав для продолжения установки");
            createDirectories(workDir, DIRS_IN_GAMES);
            writeLog("Созданы директории в Games/");
            createDirectories(workDir, DIRS_IN_GAMES_RES);
            writeLog("Созданы директории в Games/res");
            createDirectories(workDir, DIRS_IN_GAMES_SRC);
            writeLog("Созданы директории в Games/src");
            createFiles(workDir, FILES_IN_GAMES_SRC_MAIN);
            writeLog("Созданы файлы в Games/main");
        } catch (FolderCreationException e) {
            writeLog("Не удалось создать директорию или файл" + e);
            throw e;
        } catch (WorkDirExistAndPermException | FileCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTempDir() {
        return tempDir;
    }

    public static String getWorkDir() {
        return workDir;
    }

    public static String getSaveGamesDir() {
        return saveGamesDir + delim;
    }

    public static String getSaveGamesZip() {
        return saveGamesZip;
    }

    public static String getTempFile() {
        return tempFile;
    }

    public static List<String> getFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> res = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            res.add(file.getPath());
        }
        return res;
    }

    private static void replaceSlash(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace('/', '\\');
        }
    }

    static File[] createFiles(String tempDir, String[] paths) throws FileCreationException {
        File[] files = new File[paths.length];
        String absPath;
        for (int i = 0; i < paths.length; i++) {
            absPath = tempDir + paths[i];
            files[i] = createFile(absPath);
        }
        return files;
    }

    static File createFile(String filePath) throws FileCreationException {
        File file = new File(filePath);
        File subDir = new File(filePath.substring(0, filePath.lastIndexOf(delim)));
        if (file.exists()) {
            return file;
        }
        try {
            if (subDir.exists()) {
                if (file.createNewFile()) {
                    return file;
                }
            } else {
                subDir.mkdirs();
                file.createNewFile();
            }
        } catch (IOException ex) {
            throw new FileCreationException("Failed to create file '" + file.getAbsolutePath() + "' for an unknown reason.");
        }
        return file;
    }

    static void createDirectories(String workDir, String[] dirs) throws FolderCreationException {
        for (String path :
                dirs) {
            try {
                createDirectory(workDir + path);
            } catch (IOException e) {
                throw new FolderCreationException("Не удалось создать директорию " + path);
            }
        }
    }

    public static File createDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }

    static void deleteFiles(List<String> paths) {
        for (String s :
                paths) {
            deleteFile(s);
        }
    }

    public static void deleteFile(String s) {
        File file = new File(s);
        file.delete();
    }

    static void checkExistAndPerm(String workDir) throws WorkDirExistAndPermException {
        File workDirFile = new File(workDir);
        if (!workDirFile.exists() || !workDirFile.canWrite()
                || !workDirFile.canRead() || !workDirFile.canExecute()) {
            throw new WorkDirExistAndPermException("Директория Games не существует или недостачно прав");
        }
    }

    private static String pathConstructor(String dir, String[] strings, String string) {
        return dir + Arrays.stream(strings).filter(s -> s.contains(string)).findFirst().get();
    }
}
