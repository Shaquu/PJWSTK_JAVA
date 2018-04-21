package one;

class Letters {

    private static final long threadTimeout = 1000;

    private Thread[] threads;
    private final String letters;

    Letters(String letters){
        this.letters = letters;

        threads = new Thread[letters.length()];

        for(int i = 0; i < letters.length(); i++){
            addThread(i);
        }
    }

    Thread[] getThreads(){
        return threads;
    }

    void startThreads() {
        for(Thread t : threads){
            t.start();
        }
    }

    void stopThreads() {
        for(Thread t : threads){
            t.interrupt();
        }
    }

    private void addThread(int index){
        threads[index] = new LetterThread(threadTimeout, this.letters.charAt(index));
    }

    private class LetterThread extends Thread {
        private final char letter;
        private final long timeout;

        LetterThread(long timeout, char letter) {
            this.letter = letter;
            this.timeout = timeout;

            super.setName("Thread " + letter);
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.print(letter);
                    sleep(timeout);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
