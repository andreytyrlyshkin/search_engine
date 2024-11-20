package searchengine.exceptions;

public class IndexingIsNotRunningException extends RuntimeException{
    public IndexingIsNotRunningException(String message) {
        super(message);
    }
}
