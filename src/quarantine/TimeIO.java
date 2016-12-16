package quarantine;

public class TimeIO {
    private long timeIn;
    private long timeOut;
    private long timeTaken;
    private String stringItself;

    public TimeIO(long timeIn) {
        this.timeIn = timeIn;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public void setStringItself(String stringItself) {
        this.stringItself = stringItself;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;

    }

    @Override
    public String toString() {
        if(timeOut ==0){
            return "Time In: " + timeIn + "; And then dramatically lost :(";
        }else {
            return "Time In: " + timeIn + "; Time Out: "
                    + timeOut + "; Time taken: " + timeTaken + "; String itself: " + stringItself;
        }
    }
}
