package Exception;

public class SyntacticException extends RuntimeException {
    private final int line;
    private String message;

    public SyntacticException(int line) {
        super();
        this.line = line;
    }

    public SyntacticException(int line, String message) {
        super();
        this.line = line;
        this.message = message;
    }

    public int getLine() {
        return line;
    }
    public String getMessage() {
        return this.message;
    }
}
