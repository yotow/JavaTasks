package L3T1;

import L3T1.exceptions.SaveErrorException;
import L3T1.exceptions.UnknownOsException;

import java.io.*;

import static L3T1.GameLogger.getTime;
import static L3T1.GameLogger.writeLog;

public class GameSaver {

    private static final String EXT = ".save";
    static void save(GameProgress progress) throws UnknownOsException, SaveErrorException {

        String path = OSUtils.getSaveGamesDir() + getTime() + EXT;
        System.out.println(path);
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(progress);
        } catch (IOException e) {
            writeLog("Ошибка сохранения игры");
            throw new SaveErrorException("Не удалось сохранить игру");
        }
    }
}