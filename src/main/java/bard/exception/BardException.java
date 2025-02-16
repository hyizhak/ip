package bard.exception;

import bard.ui.TextUi;

public class BardException extends Exception {
    public BardException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return TextUi.horizontalLine + getMessage() + "\n" + TextUi.horizontalLine;
    }
}
