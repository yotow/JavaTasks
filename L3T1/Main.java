package L3T1;

import L3T1.exceptions.FileCreationException;
import L3T1.exceptions.FolderCreationException;
import L3T1.exceptions.UnknownOsException;
import L3T1.exceptions.WorkDirExistAndPermException;

public class Main {
    public static void main(String[] args) {
        try {
            GameInstaller.install();
        }catch (UnknownOsException | WorkDirExistAndPermException | FileCreationException | FolderCreationException e){
            System.out.printf("%s", e);
        }
    }
}
