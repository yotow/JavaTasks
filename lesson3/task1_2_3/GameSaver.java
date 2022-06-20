package lesson3.task1_2_3;

import lesson3.task1_2_3.exceptions.SaveErrorException;
import lesson3.task1_2_3.exceptions.UnknownOsException;

import java.io.*;

public class GameSaver {

    private static final String EXT = ".save";
    static void save(GameProgress progress) throws UnknownOsException, SaveErrorException {

        String path = OSUtils.getSaveGamesDir() + GameLogger.getTime() + EXT;
        System.out.println(path);
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(progress);
        } catch (IOException e) {
            GameLogger.writeLog("Ошибка сохранения игры");
            throw new SaveErrorException("Не удалось сохранить игру");
        }
    }
}