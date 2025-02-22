package bard.exception;

import bard.ui.TextUi;

/**
 * Represents an exception thrown by Bard.
 */
public class BardException extends Exception {
    public BardException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return TextUi.getHorizontalLine() + getMessage() + "\n" + TextUi.getHorizontalLine();
    }
}
