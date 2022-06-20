package lesson3.task1_2_3.exceptions;

import java.io.IOException;

public class SaveErrorException extends Exception {
    public SaveErrorException(String message) {
        super(message);
    }
}
