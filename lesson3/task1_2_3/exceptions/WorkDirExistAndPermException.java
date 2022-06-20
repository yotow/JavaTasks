package lesson3.task1_2_3.exceptions;

import java.io.IOException;

public class WorkDirExistAndPermException extends IOException {
    public WorkDirExistAndPermException(String message) {
        super(message);
    }
}
