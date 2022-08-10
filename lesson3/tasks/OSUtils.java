package lesson3.tasks;

import lesson3.tasks.exceptions.FileCreationException;
import lesson3.tasks.exceptions.WorkDirExistAndPermException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static lesson3.tasks.GameLogger.writeLog;

public class OSUtils {
    static final String OS = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    static final String[] DIRS = {"/src/main", "/src/test", "/res/drawables", "/res/vectors", "/res/icons", "/saveGames", "/temp"};

    static final String[] FILES = {"/src/main/Utils.java", "/src/main/Main.java", "/temp/temp.txt"};

    private static String workDir;

    static void init() throws WorkDirExistAndPermException, FileCreationException {
        if (OS.contains("windows")) {
            workDir = WorkingDirectory.WINDOWS.getValue();
        } else if (OS.contains("linux")) {
            workDir = WorkingDirectory.LINUX.getValue();
        } else if (OS.contains("mac")) {
            workDir = WorkingDirectory.MAC.getValue();
        }

        writeLog("Операционная система " + OS + " определена");
        writeLog("Структура каталогов определена");

        checkExistAndPerm(workDir);
        writeLog("Директория Games существует, достаточно прав для продолжения установки");
        for (String s :
                DIRS) {
            createDirectory(s);
        }
        writeLog("Созданы директории в Games/");
        writeLog("Созданы директории в Games/res");
        writeLog("Созданы директории в Games/src");

        for (String s :
                FILES) {
            createFile(s);
        }
        writeLog("Файл логов создан");
        writeLog("Созданы файлы в Games/main");
    }

    public static String getSaveGamesDir() {
        return workDir + "/saveGames/";
    }

    public static String getTempFile() {
        return workDir + "/temp/temp.txt";
    }

    public static List<String> getFilesInDir(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> res = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            res.add(file.getPath());
        }
        return res;
    }

    static void createFile(String filePath) throws FileCreationException {
        File file = new File(workDir + filePath);
        File subDir = new File(String.valueOf(file.getParentFile()));
        if (!file.exists()) {
            try {
                if (!subDir.exists()) {
                    subDir.mkdirs();
                }
                if (file.createNewFile()) {
                    //ignore
                }
            } catch (IOException ex) {
                throw new FileCreationException("Failed to create file '" + file.getAbsolutePath() + "' for an unknown reason.");
            }
        }
    }

    public static void createDirectory(String directoryPath) {
        File dir = new File(workDir + directoryPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                //ignore
            }
        }
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
}
