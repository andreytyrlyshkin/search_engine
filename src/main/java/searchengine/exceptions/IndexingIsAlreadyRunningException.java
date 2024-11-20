package searchengine.exceptions;

public class IndexingIsAlreadyRunningException extends RuntimeException{
    public IndexingIsAlreadyRunningException(String message) {
        super(message);
    }
}