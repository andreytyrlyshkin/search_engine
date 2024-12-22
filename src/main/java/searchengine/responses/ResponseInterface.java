package searchengine.responses;

import java.util.concurrent.atomic.AtomicBoolean;

public class ResponseInterface {
    public static AtomicBoolean isRunning = new AtomicBoolean();
    public String errorText = "";

    public ResponseInterface(AtomicBoolean isRunning){
        this.isRunnig = isRunning;
    }
    public ResponseInterface(AtomicBoolean isRunning, String errorText){
        this.isRunnig = isRunning;
        this.errorText = errorText;
    }

    public AtomicBoolean getIsRunning(){
        return isRunning;
    }

    public String getErrorText(){
        return errorText;
    }

    public void setIsRunning(AtomicBoolean isRunning){
        this.isRunning = isRunning;
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }

}
