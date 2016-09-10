package auctionsniper;

public class Defect extends RuntimeException {
    public Defect() {
        super();
    }

    public Defect(String message) {
        super(message);
    }

    public Defect(String message, Throwable cause) {
        super(message, cause);
    }

    public Defect(Throwable cause) {
        super(cause);
    }
}
