package algorithms1.week2.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

// LinkedList implementation of the Deque
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int numberOfItems;

    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
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

        Node oldFirst = first;
        first = new Node();
        first.value = item;
        first.next = oldFirst;

        if (isEmpty()) last = first;
        else oldFirst.prev = first;

        numberOfItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.value = item;
        last.prev = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;

        numberOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Node oldFirst = first;
        first = oldFirst.next;

        numberOfItems--;

        if (isEmpty()) last = null;
        else first.prev = null;

        return oldFirst.value;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node oldLast = last;
        last = oldLast.prev;

        numberOfItems--;

        if (isEmpty()) first = null;
        else last.next = null;

        return oldLast.value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>();
        deq.addFirst(20);
        deq.addFirst(10);
        deq.addLast(30);
        deq.addLast(40);

        for (Integer item: deq) {
            System.out.println(item);
        }

        System.out.println();

        deq.removeFirst();
        for (Integer item: deq) {
            System.out.println(item);
        }

        System.out.println();

        deq.removeLast();
        for (Integer item: deq) {
            System.out.println(item);
        }

        System.out.println();

        deq.removeFirst();
        deq.removeFirst();
        for (Integer item: deq) {
            System.out.println(item);
        }

    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.value;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
