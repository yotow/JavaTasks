package L3T1;

import L3T1.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static L3T1.GameLogger.flush;
import static L3T1.GameLogger.writeLog;

public class GameMenu {

    void installGame() {
        try {
            GameInstaller.install();
            writeLog("Установка окончилась успешно");
        } catch (UnknownOsException | WorkDirExistAndPermException | FileCreationException |
                 FolderCreationException e) {
            System.out.printf("%s", e);
            writeLog("Установка окончилась неуспешно" + e);
            flush();
        } catch (IOException e) {
            writeLog("Установка окончилась неуспешно");
            flush();
            throw new RuntimeException(e);
        }

    }

    //В процессе игры изменяется прогресс
    GameProgress playNewGame() {
        writeLog("Прогресс игры изменен");
        return new GameProgress(new Random().nextInt(100), new Random().nextInt(20),
                new Random().nextInt(100), new Random().nextDouble(1000));
    }

    void resumeInGame() {

    }

    void saveGame(GameProgress progress) {
        try {
            GameSaver.save(progress);
        } catch (SaveErrorException | UnknownOsException e) {
            System.out.println("Не удалось сохранить игру, попробуйте позже " + e);
            writeLog("Не удалось сохранить игру, попробуйте позже");
        }
    }

    void zipFiles() {
        String pathToDir = OSUtils.getSaveGamesDir();
        List<String> listFiles = OSUtils.getFiles(pathToDir);
        String pathToZip = OSUtils.getSaveGamesZip();
        Ziper.zipFiles(pathToZip, listFiles);
        writeLog("Игра сохранена успешно");
        OSUtils.deleteFiles(listFiles);
    }

    void loadSavedGame() {

    }
}