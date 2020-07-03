import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Scanner;
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
            Node<Item> firstItem = first;
            first = new Node<Item>();
            first.item = item;
            first.next = firstItem;
            first.previous = null;
            if (!isEmpty()) {
                firstItem.previous = first;
            }
        if (isEmpty()) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
            Node<Item> oldLast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
        last.previous = oldLast;
            if (!isEmpty()) {
                oldLast.next = last;
            }
        if (isEmpty()) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        Item firstItem = first.item;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        Item lastItem = last.item;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
//         Scanner keys = new Scanner(System.in);
        Deque<String> x = new Deque<String>();
        Iterator<String> n;

//               while(keys.hasNextLine()) {
        while (!StdIn.isEmpty()) {
            n = x.iterator();
            while (n.hasNext()) {
                System.out.println(n.next() + " ");
            }
            System.out.println();
//            String s = keys.nextLine();
            String s = StdIn.readString();
            if (s.equals("-"))
                System.out.println(x.removeFirst());
            else if (s.equals("--")) {
                System.out.println(x.removeLast());
            } else if ((s.length() > 0) && (s.split(" ")[0].equals("add"))) {
                x.addFirst(s.split(" ")[1]);
            }
            else if(s.length() > 0)
                x.addLast(s);
        }
    }

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
        public Item next() {
            if (!hasNext()) {
                    throw new java.util.NoSuchElementException("No further items");
            } else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
