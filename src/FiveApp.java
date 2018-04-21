import one.OneEx;
import two.TwoEx;

public class FiveApp {
    private static int zadanieCount = 1;

    public static void main(String...args){
        nastepneZadanie();
        try {
            new OneEx("ABCD");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nastepneZadanie();
        try {
            new TwoEx();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zadanieCount--;
        nastepneZadanie();
        try {
            new TwoEx("abort");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void nastepneZadanie(){
        System.out.printf("\n--------------------\nZadanie %d\n\n", zadanieCount++);
    }
}