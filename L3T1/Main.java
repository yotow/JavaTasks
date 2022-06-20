package L3T1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameMenu menu = new GameMenu();
        menu.installGame();
        GameProgress progress1 = menu.playNewGame();
        GameProgress progress2 = menu.playNewGame();
        GameProgress progress3 = menu.playNewGame();
        menu.saveGame(progress1);
        Thread.sleep(1000);
        menu.saveGame(progress2);
        Thread.sleep(1000);
        menu.saveGame(progress3);
        menu.zipFiles();
        menu.loadSavedGame();
        menu.resumeInGame();
    }
}