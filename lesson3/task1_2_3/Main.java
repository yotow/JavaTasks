package lesson3.task1_2_3;

import lesson3.task1_2_3.exceptions.FolderCreationException;

import java.io.File;

public class Main {
    public static void main(String[] args) throws InterruptedException, FolderCreationException {

        //Установка
        GameMenu menu = GameMenu.init(); // Инициализация системы, создание путей, папок. Было бы проще с Paths...

        GameProgress progress1 = menu.playGame();
        GameProgress progress2 = menu.playGame();
        GameProgress progress3 = menu.playGame();
        menu.saveGame(progress1);
        Thread.sleep(1000);
        menu.saveGame(progress2);
        Thread.sleep(1000);
        menu.saveGame(progress3);
        menu.zipFiles();

        menu.loadSavedGame(); // распаковка ZIP

        menu.resumeInGame(); // Десериализация
    }
}
