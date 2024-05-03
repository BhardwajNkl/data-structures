/**
 * 
 */
package assignments.dsa.main;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author nikhilbhardwaj01
 *
 */
public class Stack<Type extends Comparable<Type>> implements Iterable<Type> {
	private ArrayList<Type> data; // reference to the array list that will be used for storing the values.
	private int size; // number of elements present in the stack

	public Stack() {
		data = new ArrayList<>();
		size = 0;
	}

	public void push(Type element) {
		data.add(element);
		size++;
	}

	public Type pop() {
		if (size == 0) {
			// empty stack exception
			throw new EmptyStackException();

		} else {
			Type element = data.get(size - 1);
			data.remove(size - 1);
			size--;
			return element;
		}
	}

	public Type peek() {
		if (size == 0) {
			// empty stack exception
			throw new EmptyStackException();
		} else {
			return data.get(size - 1);
		}
	}

	public boolean contains(Type element) {
		if (data.contains(element)) {
			return true;
		} else {
			return false;
		}
	}

	public int size() {
		return size;
	}

	public Type centre() {
		if (size == 0) {
			// empty stack exception
			throw new EmptyStackException();
		} else {
			return data.get((size - 1) / 2);
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
		return new StackIterator<Type>();
	}

	// inner class for implementing the iterator
	class StackIterator<Type> implements Iterator<Type> {
		int index;

		public StackIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public Type next() {
			if (index >= size) {
				// exception
				throw new NoSuchElementException();
			}
			Type nextValue = (Type) data.get(index);
			index++;
			return nextValue;
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
