public class BardException extends Exception {
    public BardException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return Bard.horizontalLine + getMessage() + "\n" + Bard.horizontalLine;
    }
}
