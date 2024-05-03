/**
 * 
 */
package assignments.dsa.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import assignments.dsa.exceptions.EmptyPriorityQueueException;

/**
 * @author nikhilbhardwaj01
 *
 */
public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {
	private ArrayList<T> data; // reference to the array list that will store data.
	private int size; // number of elements in the priority queue
	private boolean isMinPriorityQueue; // A flag that marks whether it the priority queue is a min priority queue or
										// max priority queue. It is a min priority queue when the value is true.

	public PriorityQueue() {
		data = new ArrayList<T>();
		size = 0;
		isMinPriorityQueue = true;
	}

	public void enqueue(T element) {
		data.add(element);
		size++;
		// now to ensure that the heap property is intact, move thei new element upwards
		// till it has a higher priority than it's parent.
		moveUpward(size - 1);
	}

	private void moveUpward(int current) {
		int parent = (current - 1) / 2;
		while (current > 0 && isMorePrior(current, parent)) {
			// swap current with parent
			T temp = data.get(current);
			data.set(current, data.get(parent));
			data.set(parent, temp);
			// update current and parent
			current = parent;
			parent = (current - 1) / 2;
		}
	}

	// The below method is used for deciding which element has higher priority. In
	// case of a min priority queue, smaller element has higher priority. While, in
	// case of max priority queue, the larger element has higher priority.
	// the method return true if the element at current index has higher priority
	// than the element at parent index.
	private boolean isMorePrior(int current, int parent) {
		T obj1 = data.get(current);
		T obj2 = data.get(parent);
		boolean ans = false;
		if (isMinPriorityQueue) {
			if (obj1.compareTo(obj2) <= 0) {
				ans = true;
			}
		} else {
			if (obj1.compareTo(obj2) >= 0) {
				ans = true;
			}
		}

		return ans;

	}

	public T dequeue() throws EmptyPriorityQueueException {
		if (size == 0) {
			// empty priority queue exception
			throw new EmptyPriorityQueueException();
		} else {
			// we need to remove the root element, but we first save it to a variable and
			// then the last element is brought to the index 0 and then we delete the last
			// element. This is for better performance of dequeue operation.
			T ans = data.get(0);// this element will be returned
			data.set(0, data.get(data.size() - 1));
			data.remove(data.size() - 1);

			// And now ensure the heap property. Because the bringing of last element to the
			// root may have changed violated the heap property.
			moveDownward(0);
			size--;
			return ans;
		}

	}

	private void moveDownward(int current) {
		int leftChild = 2 * current + 1;
		int rightChild = 2 * current + 2;
		while (true) {
			if (leftChild >= data.size()) {
				// both child not present
				break;
			} else if (rightChild >= data.size()) {
				// this means left child is present
				if (isMorePrior(current, leftChild)) {
					break;
				} else {
					// swap
					T temp = data.get(current);
					data.set(current, data.get(leftChild));
					data.set(leftChild, temp);

					// now, update current and left and right
					current = leftChild;
					leftChild = current * 2 + 1;
					rightChild = current * 2 + 2;
				}
			} else {
				// both child present
				// first, find out which one is smaller
				int smallest = leftChild;
				if (isMorePrior(rightChild, leftChild)) {
					smallest = rightChild;
				}

				// now compare with current
				if (isMorePrior(current, smallest)) {
					break;
				} else {
					// swap current and smallest
					T temp = data.get(current);
					data.set(current, data.get(smallest));
					data.set(smallest, temp);

					// now, update current and left and right
					current = smallest;
					leftChild = current * 2 + 1;
					rightChild = current * 2 + 2;
				}

			}
		}
	}

	public T peek() throws EmptyPriorityQueueException {
		if (size <= 0) {
			// empty priority queue exception
			throw new EmptyPriorityQueueException();
		}
		return data.get(0);
	}

	public boolean contains(T element) {
		return data.contains(element);
	}

	public int size() {
		return this.size;
	}

	// REVERSE: This method changes converts a min priority queue into a max
	// priority queue and vice versa.
	public void reverse() {
		if (isMinPriorityQueue) {
			// convert to max priority queue
			// step1. change the value for isMinPriorityQueue variable
			isMinPriorityQueue = false;
			// step2. BUILD MAX HEAP
			buildHeap();
		} else {
			// convert to min priority queue
			// step1. change the value for isMinPriorityQueue variable
			isMinPriorityQueue = true;
			// step2. build min heap
			buildHeap();
		}
	}

	private void buildHeap() {
		int current = (size - 2) / 2;// starting from last node's parent
		while (current >= 0) {
			moveDownward(current);
			current--;
		}
	}

	public T centre() throws EmptyPriorityQueueException {
		if (size == 0) {
			// empty priority queue exception
			throw new EmptyPriorityQueueException();
		}
		return data.get((size - 1) / 2);
	}

	public Iterator<T> iterator() {
		return new PriorityQueueIterator<T>();
	}

	// inner class for implementing the iterator
	class PriorityQueueIterator<T> implements Iterator<T> {
		int index;

		public PriorityQueueIterator() {
			index = 0;
		}

		public boolean hasNext() {
			return index < size;
		}

		public T next() {
			if (index >= size) {
				// raise exception
				throw new NoSuchElementException();
			}

			T nextValue = (T) data.get(index);
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
