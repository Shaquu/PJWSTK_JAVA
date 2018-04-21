package one;

public class OneEx {

    public OneEx(String arg) throws InterruptedException {
        Letters letters = new Letters(arg);

        for (Thread t : letters.getThreads())
            System.out.println(t.getName());

        letters.startThreads();

        Thread.sleep(5000);

        letters.stopThreads();

        System.out.println("\nProgram skończył działanie");
    }

}
