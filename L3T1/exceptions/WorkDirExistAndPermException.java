package L3T1.exceptions;

import java.io.IOException;

public class WorkDirExistAndPermException extends IOException {
    public WorkDirExistAndPermException(String message) {
        super(message);
    }
}
