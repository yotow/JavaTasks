package lesson3.task1_2_3;

import lesson3.task1_2_3.exceptions.*;

import java.util.List;
import java.util.Random;

import static lesson3.task1_2_3.GameLogger.writeLog;

public class GameMenu {

    public static GameMenu init() {
        try {
            OSUtils.init();
            writeLog("Установка окончилась успешно");
        } catch (FolderCreationException e) {
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
        List<String> listSavedGames = OSUtils.getFiles(OSUtils.getSaveGamesDir());
        List<GameProgress> progresses = GameSaver.restoreGame(listSavedGames);
        for (GameProgress progress : progresses) {
            writeLog("Игра восстановлена");
            writeLog(progress.toString());
        }
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
        Zipper.zipFiles(pathToZip, listFiles);
        writeLog("Игра сохранена успешно");
        OSUtils.deleteFiles(listFiles);
    }

    void loadSavedGame() {
        String pathToZip = OSUtils.getSaveGamesZip();
        String destinationFolder = OSUtils.getSaveGamesDir();
        Zipper.openZip(pathToZip, destinationFolder);
        writeLog("Архив с сохранением распакован");
        OSUtils.deleteFile(OSUtils.getSaveGamesZip());
    }
}
