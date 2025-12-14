package Exception;

public class SemanticException extends RuntimeException {
    private final int line;
    private String message;

    public SemanticException(int line) {
        super();
        this.line = line;
    }

    public SemanticException(int line, String message) {
        super();
        this.line = line;
        this.message = message;
    }

    public int getLine() {
        return this.line;
    }

    public String getMessage() {
        return this.message;
    }
}
