import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        ArrayList<BigInt> list = new ArrayList<BigInt>();
//        for (int i = -200; i < 200; i += 3)
//            list.add(new BigInt(String.valueOf(i)));
//        list.add(new BigInt("0"));
//        for (BigInt n : list) {
//            for (BigInt m : list) {
//                Integer intN = Integer.parseInt(n.toString());
//                Integer intM = Integer.parseInt(m.toString());
//                if (n.compareTo(m) != intN.compareTo(intM))                    System.out.println("compare error: " + n + ".compareTo(" + m + ") = " + n.compareTo(m));
//                if (Integer.parseInt(n.plus(m).toString()) != intN + intM)      System.out.println("add error: " + n + " + " + m + " = " + n.plus(m).toString());
//                if (Integer.parseInt(n.minus(m).toString()) != intN - intM) System.out.println("sub error: " + n + " - " + m + " = " + n.minus(m).toString());
//                if (Integer.parseInt(n.multiply(m).toString()) != intN * intM) System.out.println("mul error: " + n + " * " + m + " = " + n.multiply(m).toString());
//                if (intM != 0 && Integer.parseInt(n.divide(m).toString()) != intN / intM) System.out.println("div error: " + n + " / " + m + " = " + n.divide(m).toString());
//                if (n.equals(m) != (intN.equals(intM))) System.out.println("equals error: " + n + " " + (n.equals(m)?"==":"!=") + " " + m);
//            }
//        }
        // It is a very nice and elegant test, I don't wanna delete it ^^

        System.out.println("Buongiorno signoritta!! I am'a Giorno di Giovanna, benvenuta a la mio Magik Show!");
        Scanner scan = new Scanner(System.in);
        BigInt n = new BigInt("0");
        BigInt m = new BigInt("0");
        boolean correct = false;
        while (!correct) {
            try {
                System.out.print("Insertto di primo numero: ");
                n = new BigInt(scan.nextLine());
                System.out.print("Bueno, u la numera seconda: ");
                m = new BigInt(scan.nextLine());
                correct = true;
            } catch (IllegalArgumentException e) {
                System.out.println("NO NO NO! Di inputto was'a incorrecto, try againo!");
            }
        }
        System.out.println("Perfetto, multo bian!! Now I'a show you di magiko!!!");
        int compare = n.compareTo(m);
        System.out.println(n + " + " + m + " = " + n.plus(m).toString());
        System.out.println(n + " - " + m + " = " + n.minus(m).toString());
        System.out.println(n + " * " + m + " = " + n.multiply(m).toString());
        try {
            System.out.println(n + " / " + m + " = " + n.divide(m).toString());
        } catch (ArithmeticException e) {
            System.out.println("MAMA MIA! Divizia di 0 e no possibile!!");
        }
        System.out.println(n + " is " + (compare>0?"grande di":(compare<0?"minore di":"equalitto")) + " to " + m);
        System.out.println(n + " and " + m + (n.equals(m)?" are equalitti!":" are a'differentti!"));
        System.out.println("\nI was Giovanni, grazie for coming to my show! Please leave your good grades in my mamans-grades hat, mucho grasias!!");
        System.out.println("Oh no I said grasias they know I'm actually Spanish quick I must escape before the cops are coming\nbye.");
    }
}
