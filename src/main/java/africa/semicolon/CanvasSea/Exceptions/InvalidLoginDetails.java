package africa.semicolon.CanvasSea.Exceptions;

public class InvalidLoginDetails extends RuntimeException {
    public InvalidLoginDetails(String message) {
        super(message);
    }
}
