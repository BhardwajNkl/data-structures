/**
 * 
 */
package assignments.dsa.main;

import java.util.Iterator;

import assignments.dsa.exceptions.EmptyListException;

/**
 * @author nikhilbhardwaj01
 *
 */
public class LinkedList<T extends Comparable<T>> implements Iterable<T> {
	private Node<T> head;// reference to the first node in the linked list
	private Node<T> tail;// reference to the last node in the list. It is useful for delete and insert at
							// end operations.
	private int list_length; // number of elements, i.e nodes in the linked list

	LinkedList() {
		head = null;
		tail = null;
		list_length = 0;
	}

	// insert at the end
	public void insert(T value) {
		Node<T> newNode = new Node<>(value);
		if (head == null) {
			head = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = tail.next;
		}

		list_length++;
	}

	// insertAtposition: it takes an index and inserts the value there.
	public void insertAtPosition(int index, T value) throws IndexOutOfBoundsException {
		if (index < 0 || index > list_length - 1) {
			// index out of range. if ithe list size is 's', then the possible indices are:
			// 0,1,...,s-1. If you try to insert at indices out of this range, the program
			// causes an exception.
			throw new IndexOutOfBoundsException(); // using built in exception as it is quite suitable for this
													// case.
		} else if (index == 0) {
			Node<T> newNode = new Node<>(value);
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		} else {
			Node<T> temp = head;
			int step = 0;
			while (step < index - 1) {
				temp = temp.next;
				step++;
			}

			Node<T> newNode = new Node<T>(value);
			newNode.next = temp.next;
			temp.next.prev = newNode;
			newNode.prev = temp;
			temp.next = newNode;
		}
		list_length++;

	}

	// delete the last element
	public void delete() throws EmptyListException {
		if (head == null) {
			// null pointer exception
			throw new EmptyListException();
		} else if (head.next == null) {
			head = null;
			tail = null;
		} else {
			tail = tail.prev;
			tail.next = null;
		}

		list_length--;
	}

	// deleteAtPosition: it takes an index as argument and deletes the element at
	// that index.
	public void deleteAtPosition(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > list_length - 1) {
			// case when the specified index is not existing.
			throw new IndexOutOfBoundsException();
		} else if (head.next == null) {
			// case when only one element is present in the list.
			head = null;
			tail = null;
		} else if (index == 0) {
			// case when the user wants to delete the first element in the list.
			head = head.next;
			head.prev = null;
		} else if (index == list_length - 1) {
			// case when the user wants to delete the last element.
			tail = tail.prev;
			tail.next = null;
		} else {
			// case when user wants to delete intermediate elements
			Node<T> temp = head;
			int step = 0;
			while (step < index - 1) {
				temp = temp.next;
				step++;
			}
			temp.next = temp.next.next;
			temp.next.prev = temp;
		}
		list_length--;
	}

	// CENTRE ELEMENT: returns mid element when you have odd number of
	// elements. But in case of even number of elements, you get the lower middle
	// value
	public T centre() throws EmptyListException {
		T centreElement = null;
		if (head == null) {
			// no such element
			throw new EmptyListException();
		} else if (head.next == null) {
			centreElement = head.data;
		} else {
			Node<T> temp1 = head;
			Node<T> temp2 = tail;
			while (!(temp1.next == temp2 || temp1.next == temp2.prev)) {
				temp1 = temp1.next;
				temp2 = temp2.prev;
			}
			if (temp1.next == temp2) {
				centreElement = temp1.data;
			} else {
				centreElement = temp1.next.data;
			}

		}
		return centreElement;
	}

	// sorting using bubble sort
	public void sort() {
		Node<T> current = head;
		while (current != null) {
			Node<T> adjacent = current.next;
			while (adjacent != null) {
				// compare current with adjacent
				if (adjacent.data.compareTo(current.data) < 0) {
					// swap
					T temp = current.data;
					current.data = adjacent.data;
					adjacent.data = temp;
				}
				adjacent = adjacent.next;
			}
			current = current.next;
		}
	}

	// REVERSE
	public void reverse() {
		if (head == null || head.next == null) {
			return;
		} else {
			Node<T> temp1 = head;
			Node<T> temp2 = tail;
			int i = 0;
			int j = list_length - 1;
			while (i < j) {
				T tempValue = temp1.data;
				temp1.data = temp2.data;
				temp2.data = tempValue;
				temp1 = temp1.next;
				temp2 = temp2.prev;
				i++;
				j--;
			}

		}
	}

	// size of the list
	public int size() {
		return list_length;
	}

	public Iterator<T> iterator() {
		return new ListIterator<T>();
	}

	// inner class for implementing Iterator
	class ListIterator<T> implements Iterator<T> {
		Node<T> temp;

		public ListIterator() {
			temp = (Node<T>) head;
		}

		public boolean hasNext() {
			return temp != null;
		}

		public T next() {
			if (temp == null) {
				throw new NullPointerException();
			}
			T nextValue = temp.data;
			temp = temp.next;
			return nextValue;
		}
	}

	// print the list
	public void print() {
		Node<T> temp = (Node<T>) head;
		System.out.print("[ ");
		if (head != null) {

			while (temp != tail) {
				System.out.print(temp.data + ", ");
				temp = temp.next;
			}
			System.out.print(temp.data);
		}
		System.out.println(" ]");
	}
}

class Node<T> {
	T data;
	Node<T> next;
	Node<T> prev;

	Node(T a) {
		this.data = a;
		this.next = null;
		this.prev = null;
	}
}
