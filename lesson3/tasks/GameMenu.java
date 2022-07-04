package lesson3.tasks;

import lesson3.tasks.exceptions.FileCreationException;
import lesson3.tasks.exceptions.SaveErrorException;
import lesson3.tasks.exceptions.WorkDirExistAndPermException;

import java.util.List;
import java.util.Random;

import static lesson3.tasks.GameLogger.writeLog;

public class GameMenu {

    public static GameMenu init() {
        try {
            OSUtils.init();
            writeLog("Установка окончилась успешно");
        } catch (WorkDirExistAndPermException | FileCreationException e) {
            writeLog("Установка окончилась неуспешно" + e);
        }
        return new GameMenu();
    }

    //В процессе игры изменяется прогресс
    GameProgress playGame() {
        writeLog("Прогресс игры изменен");
        GameProgress progress = new GameProgress(new Random().nextInt(100), new Random().nextInt(20),
                new Random().nextInt(100), new Random().nextDouble(1000));
        writeLog(progress.toString());
        return progress;
    }

    void resumeInGame() {
        List<String> listSavedGames = OSUtils.getFilesInDir(OSUtils.getSaveGamesDir());
        List<GameProgress> progresses = GameSaver.restoreGame(listSavedGames);
        for (GameProgress progress : progresses) {
            writeLog("Игра восстановлена");
            writeLog(progress.toString());
        }
    }

    void saveGame(GameProgress progress) {
        try {
            GameSaver.save(progress);
        } catch (SaveErrorException e) {
            writeLog("Не удалось сохранить игру, попробуйте позже");
        }
    }

    void zipFiles(String pathToDir) {
        List<String> listFiles = OSUtils.getFilesInDir(pathToDir);
        String nameZipFile = pathToDir + "/game.zip";
        Zipper.zipFiles(nameZipFile, listFiles);
        writeLog("Игра сохранена успешно");
        OSUtils.deleteFiles(listFiles);
    }

    void loadSavedGame(String pathToZip) {
        Zipper.openZip(pathToZip, pathToZip);
        writeLog("Архив с сохранением распакован");
        OSUtils.deleteFile(pathToZip);
    }
}
