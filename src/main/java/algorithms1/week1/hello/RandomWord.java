package algorithms1.week1.hello;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int i = 1;

        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = string;
            }
            i++;
        }
        StdOut.println(champion);
    }
}
