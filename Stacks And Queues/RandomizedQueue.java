import java.util.Iterator;
// import edu.princeton.cs.algs4.StdOut;
 import edu.princeton.cs.algs4.StdIn;
//import java.util.Scanner;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rqueue;
    private int size;
    private int firstIndex;
    private int lastIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rqueue = (Item[]) new Object[2];
        firstIndex = 1;
        lastIndex = 1;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        if (lastIndex == rqueue.length) {
            resize(rqueue);
        }
            rqueue[lastIndex] = item;
            lastIndex++;
            size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Randomized Queue is empty");
        }
        if (size > 0 && size == rqueue.length/4) {
            resize(rqueue);
        }
        // putting random item at the end so there are no holes in the array
        int randIndex = ((int) Math.floor(Math.random()*(size))) + firstIndex;
        Item placeholder = rqueue[randIndex];
        rqueue[randIndex] = rqueue[lastIndex - 1];
        rqueue[lastIndex - 1] = null;
        lastIndex--;
        size--;
        return placeholder;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Randomized Queue is empty");
        }
        int randIndex = ((int) Math.floor(Math.random()*(size))) + firstIndex;
        Item placeholder = rqueue[randIndex];
        return placeholder;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex = firstIndex;
        public boolean hasNext() {
            return currentIndex != lastIndex;
        }
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No further items");
            } else {
                Item item = rqueue[currentIndex];
                currentIndex = currentIndex + 1;
                return item;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
//         Scanner keys = new Scanner(System.in);
        RandomizedQueue<String> x = new RandomizedQueue<String>();
        Iterator<String> n;
//       while(keys.hasNextLine()) {
        while (!StdIn.isEmpty()) {
            n = x.iterator();
            while (n.hasNext()) {
                System.out.print(n.next() + " ");
            }
            System.out.println();
            String s = StdIn.readString();
            if (s.equals("-"))
                System.out.println(x.dequeue());
            else if (s.equals("+"))
                System.out.println(x.sample());
            else if (s.length() > 0)
                x.enqueue(s);
        }
    }

    private void resize(Item[] arr) {
        if (lastIndex == arr.length) {
            Item[] oldRqueue = rqueue;
            rqueue = (Item[]) new Object[oldRqueue.length * 2];
            for (int i = firstIndex; i < lastIndex; i++ ) {
                rqueue[i] = oldRqueue[i];
            }
        } else if (size == arr.length/4) {
            Item[] oldRqueue = rqueue;
            rqueue = (Item[]) new Object[oldRqueue.length / 2];
            int oldFirstIndex = firstIndex;
            firstIndex = rqueue.length/4;
            lastIndex = firstIndex + size;
            for (int i = firstIndex; i <= lastIndex; i++ ) {
                rqueue[i] = oldRqueue[oldFirstIndex];
                oldFirstIndex++;
            }
        } else {
            return;
        }
    }
}