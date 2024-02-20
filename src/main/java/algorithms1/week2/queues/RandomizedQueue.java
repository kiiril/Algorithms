package algorithms1.week2.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int numberOfItems;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }
    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isFull()) resizeArray(2 * items.length);

        items[size()] = item;
        numberOfItems++;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int index = getRandomIndex();
        int lastIndex = size() - 1;

        Item item = items[index];
        items[index] = items[lastIndex];
        items[lastIndex] = null;

        numberOfItems--;

        if (size() <= items.length / 4 && size() > 0) resizeArray(items.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int index = getRandomIndex();
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(40);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);

        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());

        System.out.println(randomizedQueue.dequeue());

        for (Integer item: randomizedQueue) {
            System.out.println(item);
        }
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(10);
        queue.dequeue();
        queue.isEmpty();
        queue.isEmpty();
        queue.enqueue(696);
    }

    private boolean isFull() {
        return numberOfItems == items.length;
    }

    private void resizeArray(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];

        for (int i = 0; i < size(); i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    private int getRandomIndex() {
        return StdRandom.uniformInt(size());
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indexes = new int[size()];
        private int count = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < size(); i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return count < size();
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return items[indexes[count++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
