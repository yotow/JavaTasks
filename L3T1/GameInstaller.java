package L3T1;

import L3T1.exceptions.FileCreationException;
import L3T1.exceptions.FolderCreationException;
import L3T1.exceptions.UnknownOsException;

import java.io.IOException;

import static L3T1.GameLogger.*;
import static L3T1.OSUtils.*;

public class GameInstaller {

    public static void install() throws UnknownOsException, IOException, FileCreationException, FolderCreationException {

        OSUtils.init();

        final String workDirName = getWorkDir();

        checkExistAndPerm(workDirName);
        writeLog("Директория Games существует, достаточно прав для продолжения установки\n");

        try {
            createDirectories(workDirName, DIRS_IN_GAMES);
            writeLog("Созданы директории в Games/");
            createDirectories(workDirName, DIRS_IN_GAMES_RES);
            writeLog("Созданы директории в Games/res");
            createDirectories(workDirName, DIRS_IN_GAMES_SRC);
            writeLog("Созданы директории в Games/src");
            createFiles(workDirName, FILES_IN_GAMES_SRC_MAIN);
            writeLog("Созданы файлы в Games/main");
        } catch (FolderCreationException e) {
            writeLog("Не удалось создать директорию или файл" + e);
            throw e;
        }
    }
}