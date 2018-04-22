import java.util.*;

public class Main {

    private static int zadanieCount = 1;

    public static void main(String[] args) throws ZaMlodyException {

        //Zadanie 1
        nastepneZadanie();
        wyswietlZnaki('a', 'd');
        wyswietlZnaki('z', 'u');

        //Zadanie 2
        nastepneZadanie();
        int[] zad2 = new int[]{1, 2, 3, 0};
        System.out.printf("W tabeli %s znajduje sie %d liczb nieparzystych\n",
                Arrays.toString(zad2),
                ileNieparzystych(zad2));

        //Zadanie 3
        nastepneZadanie();
        int[][] zad3 = new int[][]{{1, 2, 3}, {2, 3, 0, 1}, {3, 2}};
        int[][] zad3reversed = odbicie(zad3);
        System.out.printf("Tabela %s odbita %s\n",
                Arrays.deepToString(zad3),
                Arrays.deepToString(zad3reversed));

        //Zadanie 4
        nastepneZadanie();
        int zad4 = 4;
        System.out.printf("Suma mniejszych dla %d to %d\n", zad4, sumaMniejszych(zad4));

        //Zadanie 5
        nastepneZadanie();
        String zad5msg = "Ala ma kota";
        int zad5by = 2;
        String zad5cip = szyfruj(zad5msg, zad5by);
        System.out.printf("%s zaszyfrowane (przesuniecie %d) do %s\n",
                zad5msg,
                zad5by,
                zad5cip);
        System.out.printf("%s odszyfrowane (przesuniecie %d) do %s\n",
                zad5cip,
                zad5by,
                odszyfruj(zad5cip, zad5by));

        //Zadanie 6
        nastepneZadanie();
        Prostokat zad6 = new Prostokat();
        System.out.printf("%s\n", zad6.toString());


        //Zadanie 7
        nastepneZadanie();
        int[] zad7 = new int[]{4, 2, 5, 12, 65, 1, 3, 5};

        Sortowanie.printArr(zad7);

        Sortowanie.printArr(Sortowanie.insertSort(zad7));

        zad7 = new int[]{4, 2, 5, 12, 65, 1, 3, 5};
        Sortowanie.printArr(Sortowanie.selectionSort(zad7));

        zad7 = new int[]{4, 2, 5, 12, 65, 1, 3, 5};
        Sortowanie.printArr(Sortowanie.mergeSort(zad7));

        //Zadanie 8
        nastepneZadanie();
        Cennik cennik = Cennik.getInstanceOf();
        cennik.wyswietlCennik();
        Sklep sklep = new Sklep(cennik, 10500); //cennik, budz?et
        System.out.println(Pracownik.ileStworzonych);
        Pracownik pracownik = new Pracownik("Anna", "Nowak", 1990, 1200);
        System.out.println(pracownik);
        System.out.println(Pracownik.ileStworzonych);
        sklep.zatrudnij(pracownik);
        sklep.zatrudnij("Jan", "Kowalski", 1982, 1200);
        sklep.wyswietlPracownikow();
        sklep.wyplacPensje();
        sklep.zwiekszBudzet(1000);
        cennik.ileKosztuje("jablko");
        cennik.ileKosztuje("kgakasdf");
    }

    static void nastepneZadanie() {
        System.out.printf("\n--------------------\nZadanie %d\n\n", zadanieCount++);
    }

    //Zadanie 1
    private static void wyswietlZnaki(char a, char b) {
        System.out.printf("Wyswietlam znaki pomiedzy %s oraz %s\n", a, b);

        if (a <= b) {
            int tempChar = a;
            while (tempChar < b - 1) {
                tempChar++;
                System.out.printf("%c\n", tempChar);
            }
        } else {
            int tempChar = a;
            while (tempChar > b + 1) {
                tempChar--;
                System.out.printf("%c\n", tempChar);
            }
        }
    }

    //Zadanie 2
    private static int ileNieparzystych(int[] arr) {
        int cnt = 0;
        for (int a : arr) {
            if (a % 2 != 0)
                cnt++;
        }
        return cnt;
    }

    //Zadanie 3
    private static int[][] odbicie(int[][] arr) {
        int[][] reversed = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = new int[arr[i].length];
            for (int j = 0; j < arr[i].length; j++) {
                reversed[i][j] = arr[i][j];
            }
        }

        for (int i = 0; i < reversed.length; i++) {
            int[] tempArr = reversed[i];
            int tempLng = tempArr.length;
            for (int j = 0; j < tempLng / 2; j++) {
                int temp = tempArr[j];
                tempArr[j] = tempArr[tempLng - j - 1];
                tempArr[tempLng - j - 1] = temp;
            }
            reversed[i] = tempArr;
        }
        return reversed;
    }

    //Zadanie 4
    private static int sumaMniejszych(int n) {
        if (n > 1)
            return n + sumaMniejszych(n - 1);
        return n;
    }

    //Zadanie 5
    private static String szyfruj(String message, int by) {

        StringBuilder sb = new StringBuilder(message);
        int rFrom = ' ';
        int rTo = '?' - by;
        if (by < 0) {
            rFrom = '?';
            rTo = ' ' + by;
        }
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == rFrom) {
                sb.setCharAt(i, (char) rTo);
            }
        }

        char cipher[] = sb.toString().toCharArray();

        for (int i = 0; i != cipher.length; i++) {
            int c = cipher[i];
            c += by;
            cipher[i] = (char) c;
        }

        return new String(cipher);
    }

    //Zadanie 5
    private static String odszyfruj(String message, int by) {
        return szyfruj(message, -by);
    }


}

//Zadanie 6
class Prostokat {
    private int a;
    private int b;
    private int pole;
    private double przekatna;

    private Scanner sc;

    public Prostokat() {
        a = 0;
        b = 0;
        pole = 0;
        przekatna = 0.d;

        sc = new Scanner(System.in);

        czytajDane();
    }

    public void czytajDane() {
        getNextInt('a');
        getNextInt('b');

        obliczPole();

        obliczPrzekatna();
    }

    private void getNextInt(char key) {
        System.out.printf("Wprowadz wartosc dla %s (int) : ", key);
        while (!sc.hasNextInt()) sc.next();
        switch (key) {
            case 'a':
                this.a = sc.nextInt();
                break;
            case 'b':
                this.b = sc.nextInt();
                break;
            default:
                break;
        }
    }

    public int obliczPole() {
        this.pole = this.a * this.b;
        return pole;
    }

    public double obliczPrzekatna() {
        this.przekatna = Math.sqrt(Math.pow(this.a, 2) + Math.pow(this.b, 2));
        return przekatna;
    }

    public String toString() {
        return "Prostokat: a=" + this.a
                + ", b=" + this.b
                + ", pole=" + this.pole
                + ", przekatna=" + this.przekatna;
    }
}

//Zadanie 7
//Za dlugo zeby to teraz robic :) 
//Polecam copy paste ze stacka i wczytanie sie w kod aby zrozumiec
class Sortowanie {

    //Bierzemy dowolny element z tabeli (pierwsza) i przenosimy go do nowej tabeli (druga)
    //(2) Bierzemy dowolny element z tabeli (pierwsza) i....
    //(3) przenosimy go do tabeli(druga) i porownujemy z kazdym elementem poki nie znajdzuiesz >= i tam go wstawiamy (przed)
    //I tak caly czas od (2) do (3) dopoki tabela (pierwsza) jest nie pusta
    public static int[] insertSort(int[] arr) {
        int n = arr.length;
        for (int j = 1; j < n; j++) {
            int key = arr[j];
            int i = j - 1;
            while ((i > -1) && (arr[i] > key)) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
        return arr;
    }

    //Zaczynamy od lewej strony tablicy czyli [0]
    //Znajdujemy index najmniejszego elementu na prawo
    //Zamieniamy
    //Kolejny element [1]
    //Znajdujemy index najmniejszego elementu na prawo
    //Zamieniamy
    //I tak dalej
    public static int[] selectionSort(int[] arr) {
        int i, j, minIndex, tmp;
        int n = arr.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
        return arr;
    }

    //Tabela np [1, 34, 12, 6, 56]
    //Dzielimy tabele na dwa
    //I znowu dzielimy, az tabela nie bedzie wielkosci == 1
    //  [1, 34, 12, 6] [56]
    //  [1, 34] [12, 6] [56]
    //  [1][34] [12][6] [56]
    //Powstaje duzo tabel o wielkosci 1
    //Laczymy dwie tabele ktore zostaly zwrocone i sortujemy je
    //  [1, 34] [6, 12] [56]
    //I znowu laczymy i sortujemy
    //  [1, 6, 12, 34] [56]
    //  [1, 6, 12, 34, 56]
    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int[] first = new int[arr.length / 2];
        int[] second = new int[arr.length - first.length];
        System.arraycopy(arr, 0, first, 0, first.length);
        System.arraycopy(arr, first.length, second, 0, second.length);

        mergeSort(first);
        mergeSort(second);

        merge(first, second, arr);
        return arr;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})

    private static void merge(int[] first, int[] second, int[] result) {
        int iFirst = 0;
        int iSecond = 0;
        int iMerged = 0;

        while (iFirst < first.length && iSecond < second.length) {
            if (first[iFirst] < (second[iSecond])) {
                result[iMerged] = first[iFirst];
                iFirst++;
            } else {
                result[iMerged] = second[iSecond];
                iSecond++;
            }
            iMerged++;
        }

        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    }

    // :D
    public static void printArr(int[] arr) {
        System.out.printf("Tablica: %s\n", Arrays.toString(arr));
    }
}

//Zadanie 8
class Sklep {
    Pracownik[] pracownicy;
    int pracownicyCnt = 0;
    int budget;
    Cennik cennik;

    public Sklep(Cennik cennik, int budget){
        pracownicy = new Pracownik[5];

        this.cennik = cennik;
        this.budget = budget;
    }

    public void zatrudnij(String imie, String nazwisko, int rokUrodzenia, int pensja){
        try {
            zatrudnij(new Pracownik(imie, nazwisko, rokUrodzenia, pensja));
        } catch (ZaMlodyException e) {
            e.printStackTrace();
        }
    }

    public void zatrudnij(Pracownik pracownik){
        pracownicy[pracownicyCnt++] = pracownik;
    }

    public void wyplacPensje(){
        for(int i = 0; i < Pracownik.ileStworzonych; i++){
            budget -= pracownicy[i].pensja;
        }
        System.out.printf("Stan budz?etu po wyp?aceniu pensji: %d\n", budget);
    }

    public void zwiekszBudzet(int o){
        if(o > 0){
            budget += o;
            System.out.printf("BStan budz?etu po wp?acie: %d\n", budget);
        }
    }

    public void wyswietlPracownikow(){
        System.out.printf("----- Lista pracowniko?w: -----\n");
        for(int i = 0; i < Pracownik.ileStworzonych; i++){
            System.out.println(pracownicy[i].toString());
        }
        System.out.printf("----------------------------------------\n");
    }
}

class Pracownik {
    static int ileStworzonych = 0;

    String imie;
    String nazwisko;
    int rokUrodzenia;
    int pensja;

    public Pracownik(){
        ileStworzonych++;
        this.imie = "";
        this.nazwisko = "";
        this.rokUrodzenia = 0;
        this.pensja = 0;
    }

    public Pracownik(String imie, String nazwisko, int rokUrodzenia, int pensja) throws ZaMlodyException {
        this();
        if(rokUrodzenia <= 18)
            throw new ZaMlodyException();

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokUrodzenia = rokUrodzenia;
        this.pensja = pensja;
    }

    public String toString() {
        return this.imie + " " + this.nazwisko
                + ", " + (Calendar.getInstance().get(Calendar.YEAR) - rokUrodzenia) + " lat"
                + ", pensja " + this.pensja;
    }

}

class Cennik {
    private static Cennik instance = null;
    protected Cennik() {
        produkty = new String[3];
        cena = new int[3];

        produkty[0] = "jablko";
        cena[0] = 3;

        produkty[1] = "ananas";
        cena[1] = 4;

        produkty[2] = "d?ugopis";
        cena[2] = 2;
    }
    public static Cennik getInstanceOf() {
        if(instance == null) {
            instance = new Cennik();
        }
        return instance;
    }

    private String[] produkty;
    private int[] cena;

    public int ileKosztuje(String produkt){
        for(int i = 0; i < produkty.length; i++){
            if(produkty[i].equalsIgnoreCase(produkt)){
                return cena[i];
            }
        }
        return -1;
    }

    public void wyswietlCennik(){
        System.out.printf("----- Cennik: ----\n");
        for(int i = 0; i < produkty.length; i++){
            System.out.printf("%s - %d\n", produkty[i], cena[i]);
        }
        System.out.printf("----------------------\n");
    }

}

class ZaMlodyException extends Exception {}