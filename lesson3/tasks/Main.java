package lesson3.tasks;

public class Main {
    public static void main(String[] args) throws InterruptedException {

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
        menu.zipFiles(OSUtils.getSaveGamesDir());

        menu.loadSavedGame(OSUtils.getSaveGamesDir() + "/game.zip"); // распаковка ZIP

        menu.resumeInGame(); // Десериализация
    }
}
