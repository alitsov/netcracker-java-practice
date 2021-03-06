package com.netcracker.adlitsov.homework4.part1;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntFunction;

/*
 * Tests are located in test/java/com/netcracker/adlitsov/homework4/part1/MyLinkedListTest.java instead of MainClass.java
 */

public class MyLinkedList<E> implements ILinkedList<E> {
    private Node<E> head, tail;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E element) {
        Node<E> newNode = new Node<>(element, null);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.nextNode = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and <= size! Current index: " + index + ", size: " + size);
        }

        if (index == size) {
            add(element);
        } else {
            if (index == 0) {
                head = new Node<>(element, head);
            } else {
                Node<E> prevNode = getNode(index - 1);
                prevNode.nextNode = new Node<>(element, prevNode.nextNode);
            }
            size++;
        }
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < size! Current index: " + index + ", size: " + size);
        }

        Node<E> nodeAtIndex = getNode(index);
        E prevValue = nodeAtIndex.element;
        nodeAtIndex.element = element;
        return prevValue;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < size! Current index: " + index + ", size: " + size);
        }
        return getNode(index).element;
    }

    private Node<E> getNode(int index) {
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.nextNode;
        }
        return current;
    }

    @Override
    public int indexOf(E element) {
        Node<E> current = head;
        int currentIndex = 0;
        while (current != null) {
            if (Objects.equals(current.element, element)) {
                return currentIndex;
            }
            current = current.nextNode;
            currentIndex++;
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < size! Current index: " + index + ", size: " + size);
        }

        E removedElement;
        if (index == 0) {
            removedElement = head.element;
            head = head.nextNode;
        } else {
            Node<E> prevNode = getNode(index - 1);
            Node<E> nodeAtIndex = prevNode.nextNode;
            removedElement = nodeAtIndex.element;
            prevNode.nextNode = nodeAtIndex.nextNode;
        }
        size--;
        return removedElement;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] resArray = new Object[size];
        Node<E> current = head;
        int i = 0;
        while (current != null) {
            resArray[i++] = current.element;
            current = current.nextNode;
        }
        return resArray;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        }

        Object[] tmpArray = array;
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            tmpArray[i] = current.element;
            current = current.nextNode;
        }

        if (size < array.length) {
            array[size] = null;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(IntFunction<T[]> constructor) {
        T[] array = constructor.apply(size);
        Object[] tmpArray = array;
        int i = 0;
        for (Node<E> current = head; current != null; current = current.nextNode) {
            tmpArray[i++] = current.element;
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append('[');
        Node<E> current = head;
        while (current != null) {
            sb.append(current.element).append((current.nextNode != null) ? ", " : "");
            current = current.nextNode;
        }
        return sb.append(']').toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<E> {
        Node<E> current = head;
        Node<E> prev, prevPrev;
        private boolean isRemovable;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = current.element;
            prevPrev = prev;
            prev = current;
            current = current.nextNode;
            isRemovable = true;
            return element;
        }

        @Override
        public void remove() {
            if (!isRemovable) {
                throw new IllegalStateException();
            }
            if (prevPrev == null) {
                // removing head
                head = current;
            } else {
                if (!hasNext()) {
                    tail = prevPrev;
                }
                prevPrev.nextNode = current;
            }

            prev = prevPrev;
            isRemovable = false;
            size--;
        }
    }

    private static class Node<E> {
        E element;
        Node<E> nextNode;

        Node(E element, Node<E> nextNode) {
            this.element = element;
            this.nextNode = nextNode;
        }
    }
}
