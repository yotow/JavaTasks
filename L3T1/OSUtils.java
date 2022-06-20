package L3T1;

import L3T1.exceptions.FileCreationException;
import L3T1.exceptions.FolderCreationException;
import L3T1.exceptions.WorkDirExistAndPermException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static L3T1.GameLogger.writeLog;

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

    static void init() {
        if (OS.contains("windows")) {
            replaceSlash(DIRS_IN_GAMES);
            replaceSlash(DIRS_IN_GAMES_RES);
            replaceSlash(DIRS_IN_GAMES_SRC);
            replaceSlash(FILES_IN_GAMES_SRC_MAIN);
            replaceSlash(FILES_IN_GAMES_TEMP);
            delim = '\\';
        }

        if (OS.contains("windows")) {
            workDir = WorkingDirectory.WINDOWS.toString();
        } else if (OS.contains("linux")) {
            workDir = WorkingDirectory.LINUX.toString();
        } else if (OS.contains("mac")) {
            workDir = WorkingDirectory.MAC.toString();
        }

        tempDir = workDir + Arrays.stream(DIRS_IN_GAMES).filter(s -> s.contains("temp")).findFirst().get();
        saveGamesDir = workDir + Arrays.stream(DIRS_IN_GAMES).filter(s -> s.contains("saveGames")).findFirst().get();
        saveGamesZip = saveGamesDir + delim + "saved.zip";
        tempFile = tempDir + Arrays.stream(FILES_IN_GAMES_TEMP).filter(s -> s.contains("temp")).findFirst().get();
        try {
            createFile(tempFile);
        } catch (FileCreationException e) {
            throw new RuntimeException(e);
        }
        writeLog("Операционная система " + OS + " определена\n");
        writeLog("Структура каталогов определена\n");
        writeLog("Файл логов создан\n");
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
            File file = new File(s);
            file.delete();
        }
    }

    static void checkExistAndPerm(String workDir) throws WorkDirExistAndPermException {
        File workDirFile = new File(workDir);
        if (!workDirFile.exists() || !workDirFile.canWrite()
                || !workDirFile.canRead() || !workDirFile.canExecute()) {
            throw new WorkDirExistAndPermException("Директория Games не существует или недостачно прав");
        }
    }
}