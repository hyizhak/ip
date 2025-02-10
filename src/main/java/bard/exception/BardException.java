package bard.exception;

import bard.ui.Ui;

public class BardException extends Exception {
    public BardException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return Ui.horizontalLine + getMessage() + "\n" + Ui.horizontalLine;
    }
}
