package two;

@SuppressWarnings("StringConcatenationInLoop")
public class StringTask implements Runnable {

    private final String message;
    private final int times;

    private String catMessage;

    private Thread thread;
    private State state;

    StringTask(String message, int times) {
        this.message = message;
        this.times = times;

        this.catMessage = "";

        this.thread = new Thread(this);

        this.state = State.CREATED;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.times; i++) {
            if(Thread.currentThread().isInterrupted()){
                return;
            }

            this.catMessage += this.message;
        }
        this.state = State.READY;
    }

    State getState() {
        return this.state;
    }

    void start() {
        this.state = State.RUNNING;
        this.thread.start();
    }

    void abort() {
        this.state = State.ABORTED;
        this.thread.interrupt();
    }

    boolean isDone(){
        return this.getState() == State.READY || this.getState() == State.ABORTED;
    }

    public enum State {
        CREATED, RUNNING, ABORTED, READY
    }

    String getResult(){
        return this.catMessage;
    }
}
