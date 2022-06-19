package L3T1;

import L3T1.exceptions.FileCreationException;
import L3T1.exceptions.FolderCreationException;
import L3T1.exceptions.UnknownOsException;
import L3T1.exceptions.WorkDirExistAndPermException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameInstaller {
    private static final String OS = System.getProperty("os.name").toLowerCase(Locale.ROOT);
    private static final String[] DIRS_IN_GAMES = {"/src", "/res", "/savegames"};
    private static final String[] DIRS_IN_GAMES_RES = {"/res/drawables", "/res/vectors", "/res/icons"};
    private static final String[] DIRS_IN_GAMES_SRC = {"/src/main", "/src/test"};
    private static final String[] FILES_IN_GAMES_SRC_MAIN = {"/src/main/Utils.java", "/src/main/Main.java"};
    private static final String[] FILES_IN_GAMES_TEMP = {"/temp/temp.txt"};

    public static void install() throws UnknownOsException, WorkDirExistAndPermException, FileCreationException, FolderCreationException {

        StringBuilder preLog = new StringBuilder();

        final String workDirName = determineOSAndWorkDir();
        preLog.append("Операционная система ").append(OS).append(" определена\n")
                .append("Структура каталогов определена\n");

        File workDir = new File(workDirName);
        checkExistAndPerm(workDir);
        preLog.append("Директория Games существует, достаточно прав для продолжения установки\n");

        File tempFile = createFiles(workDir, FILES_IN_GAMES_TEMP)[0];
        Logger logger = newLogger(tempFile);

        writeLog(preLog, tempFile);

        logger.info("Файл логов создан");

        try {
            createDirectories(workDir, DIRS_IN_GAMES);
            logger.info("Созданы директории в Games/");
            createDirectories(workDir, DIRS_IN_GAMES_RES);
            logger.info("Созданы директории в Games/res");
            createDirectories(workDir, DIRS_IN_GAMES_SRC);
            logger.info("Созданы директории в Games/src");
            createFiles(workDir, FILES_IN_GAMES_SRC_MAIN);
            logger.info("Созданы файлы в Games/main");
        } catch (FolderCreationException e) {
            logger.info("Не удалось создать директорию" + e);
            throw e;
        } catch (FileCreationException e) {
            logger.info("Не удалось создать файл" + e);
            throw e;
        }
    }

    private static void writeLog(StringBuilder preLog, File tempFile) {
        try (FileWriter writer = new FileWriter(tempFile, true)) {
            writer.write(preLog.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Logger newLogger(File tempFile) {
        Logger logger = Logger.getLogger("Log");
        FileHandler fh;

        try {
            // This block configures the logger with handler and formatter
            fh = new FileHandler(tempFile.getAbsolutePath());
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Begin logging");

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    private static File[] createFiles(File workDir, String[] paths) throws FileCreationException {
        String mWorkDir = workDir.getAbsolutePath();
        File[] files = new File[paths.length];
        String absPath;
        for (int i = 0; i < paths.length; i++) {
            absPath = mWorkDir + paths[i];
            try {
                files[i] = createFile(absPath);
            } catch (IOException e) {
                char delim = absPath.contains("/") ? '/' : '\\';
                File subDir = new File(absPath.substring(0, absPath.lastIndexOf(delim)));
                if (!subDir.exists()) {
                    subDir.mkdirs();
                    try {
                        files[i] = createFile(absPath);
                    } catch (IOException ex) {
                        throw new FileCreationException("Не удалось создать файл " + absPath);
                    }
                }
            }
        }
        return files;
    }

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        if (file.createNewFile()) {
            return file;
        }
        throw new IOException("Failed to create file '" + file.getAbsolutePath() + "' for an unknown reason.");
    }

    private static void createDirectories(File workDir, String[] dirs) throws FolderCreationException {
        String mWorkDir = workDir.getAbsolutePath();
        for (String path :
                dirs) {
            try {
                createDirectory(mWorkDir + path);
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

    private static String determineOSAndWorkDir() throws UnknownOsException {
        if (OS.contains("windows")) {
            replaceSlash(DIRS_IN_GAMES);
            replaceSlash(DIRS_IN_GAMES_RES);
            replaceSlash(DIRS_IN_GAMES_SRC);
            replaceSlash(FILES_IN_GAMES_SRC_MAIN);
            replaceSlash(FILES_IN_GAMES_TEMP);
            return WorkingDirectory.WINDOWS.toString();
        } else if (OS.contains("linux")) {
            return WorkingDirectory.LINUX.toString();
        } else if (OS.contains("mac")) {
            return WorkingDirectory.MAC.toString();
        } else {
            throw new UnknownOsException("Неизвестная операционная система");
        }
    }

    private static void replaceSlash(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace('/', '\\');
        }
    }

    private static void checkExistAndPerm(File workDirFile) throws WorkDirExistAndPermException {
        if (!workDirFile.exists() || !workDirFile.canWrite()
                || !workDirFile.canRead() || !workDirFile.canExecute()) {
            throw new WorkDirExistAndPermException("Директория Games не существует или недостачно прав");
        }
    }
}
