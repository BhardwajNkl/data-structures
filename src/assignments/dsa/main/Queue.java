/**
 * 
 */
package assignments.dsa.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import assignments.dsa.exceptions.EmptyQueueException;

/**
 * @author nikhilbhardwaj01
 *
 */
public class Queue<Type extends Comparable<Type>> implements Iterable<Type> {
	private ArrayList<Type> data;
	private int size;

	public Queue() {
		data = new ArrayList<Type>();
		size = 0;
	}

	public void enqueue(Type element) {
		data.add(element);
		size++;
	}

	public Type dequeue() throws EmptyQueueException {
		if (size == 0) {
			// empty queue exception
			throw new EmptyQueueException();
		} else {
			Type element = data.get(0);
			data.remove(0);
			size--;
			return element;
		}
	}

	public Type peek() {
		if (size == 0) {
			// empty queue exception
			throw new NoSuchElementException();
		} else {
			Type element = data.get(0);
			return element;
		}
	}

	public boolean contains(Type element) {
		return data.contains(element);
	}

	public int size() {
		return size;
	}

	public Type centre() throws EmptyQueueException {
		if (size == 0) {
			// empty queue exception
			throw new EmptyQueueException();
		} else {
			return (data.get((size - 1) / 2));
		}
	}

	// sorting using bubble sort
	public void sort() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (data.get(j + 1).compareTo(data.get(j)) < 0) {
					// swap
					Type temp = data.get(j);
					data.set(j, data.get(j + 1));
					data.set(j + 1, temp);
				}
			}
		}
	}

	public void reverse() {
		int i = 0;
		int j = size - 1;
		while (i < j) {
			Type temp = data.get(i);
			data.set(i, data.get(j));
			data.set(j, temp);
			i++;
			j--;
		}
	}

	public Iterator<Type> iterator() {
		return new QueueIterator<Type>();
	}

	// inner class for implementing the iterator
	class QueueIterator<Type> implements Iterator<Type> {
		int index;

		QueueIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public Type next() {
			if (index >= size) {
				// raise exception
				throw new NoSuchElementException();
			} else {
				return (Type) data.get(index++);
			}
		}

	}

	public void print() {
		System.out.print("[ ");
		if (size > 0) {
			for (int i = 0; i < data.size() - 1; i++) {
				System.out.print(data.get(i) + ", ");
			}
			System.out.print(data.get(data.size() - 1));
		}
		System.out.print(" ]");
		System.out.println();
	}

}
