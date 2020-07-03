import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class Permutation {
    public static void main(String[] args) {
        if (args.length == 1) {
            RandomizedQueue<String> x = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                x.enqueue(StdIn.readString());
            }
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                StdOut.print(x.dequeue() + "\n");
            }
        }
    }
}
