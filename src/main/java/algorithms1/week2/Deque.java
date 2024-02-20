package algorithms1.week2;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implementation using circular array NOT WORKING PERFECTLY NEED TO BE IMPLEMENTED
public class Deque<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 2;
    private Item[] items;
    private int head;
    private int tail;
    private int numberOfItems;

    // construct an empty deque
    public Deque() {
        items = (Item[]) new Object[INITIAL_CAPACITY];
//        head = -1;
//        tail = 0;
        head = 0;
        tail = 1;
        numberOfItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (head < 0) {
             resizeArray(items.length + numberOfItems, Side.FRONT);
        }

        items[head--] = item;
        numberOfItems++;



//        if (head == -1) {
//            head = items.length - 1;
//        }
//
//        items[head--] = item;
//        numberOfItems++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (tail == items.length) {
            resizeArray(items.length + numberOfItems, Side.END);
        }

        items[tail++] = item;
        numberOfItems++;

//        if (numberOfItems == items.length) {
//            resizeArray(2 * items.length, Type.DECREASE);
//        }
//
//        if (tail == items.length) {
//            tail = 0;
//        }
//
//        items[tail++] = item;
//        numberOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = items[++head];
        items[head] = null;
        numberOfItems--;

        if (numberOfItems > 0 && head + items.length - tail >= numberOfItems * 4) {
            resizeArray(items.length / 2, Side.BOTH);
        }


//        head++;
//        if (head == items.length) {
//            head = 0;
//        }
//        Item item = items[head];
//        items[head] = null;
//        numberOfItems--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = items[--tail];
        items[tail] = null;
        numberOfItems--;


        if (numberOfItems > 0 && head + items.length - tail >= numberOfItems * 4) {
            resizeArray(items.length / 2, Side.BOTH);
        }

//        tail--;
//        if (tail == -1) {
//            tail = items.length - 1;
//        }
//        Item item = items[tail];
//        items[tail] = null;
//        numberOfItems--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int current = head;
        @Override
        public boolean hasNext() {
            return current != tail - 1;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[++current];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private void resizeArray(int capacity, Side side) {
        Item[] newArray = (Item[]) new Object[capacity];

        int srcStart = 0;
        int destStart = 0;
        int length = tail;

        if (side == Side.BOTH) {
            srcStart = head + 1;
            destStart = (capacity - numberOfItems) / 2;
            head = destStart - 1;
            length = numberOfItems;
            tail = destStart + length;
        } else if (side == Side.FRONT) {
            tail += numberOfItems;
            head = numberOfItems - 1;
            destStart = length = numberOfItems;
        }
        System.arraycopy(items, srcStart, newArray, destStart, length);

        items = newArray;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.isEmpty() ;        //==> true
        deque.addFirst(2);
        System.out.println(Arrays.toString(deque.items));
        deque.removeLast()   ;  // ==> 2
        System.out.println(Arrays.toString(deque.items));
        System.out.println(deque.head);
        System.out.println(deque.tail);
        deque.addFirst(4);
        System.out.println(Arrays.toString(deque.items));
    }

    private enum Side {
        FRONT,
        END,
        BOTH
    }

}


