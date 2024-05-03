/**
 * 
 */
package assignments.dsa.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author nikhilbhardwaj01
 *
 */
public class HashMap<T1, T2> implements Iterable<T1> {
	private int tableLength;// length of the array list that we are using as table
	private int size;// the number of MyElements present in the table.
	private ArrayList<ArrayList<MyElement<T1, T2>>> table; // reference to the array list that will be used a as the
															// hash table.

	public HashMap() {
		// default size 10
		this.tableLength = 10;
		this.size = 0;
		table = new ArrayList<ArrayList<MyElement<T1, T2>>>(this.tableLength);
		for (int i = 0; i < this.tableLength; i++) {
			table.add(null);
		}
	}

	// when user specifies the length of table
	public HashMap(int tableLength) {
		this.tableLength = tableLength;
		this.size = 0;
		table = new ArrayList<ArrayList<MyElement<T1, T2>>>(this.tableLength);
		// initialize the table with null
		for (int i = 0; i < this.tableLength; i++) {
			table.add(null);
		}
	}

	// a simple hash function
	private int hash(T1 key) {
		int hashCode = 0;
		String k = key.toString();
		for (int i = 0; i < k.length(); i++) {
			hashCode += k.charAt(i);
			hashCode = (hashCode * k.charAt(i)) % tableLength;
		}
		return hashCode;
	}

	public void insert(T1 key, T2 value) {
		int index = hash(key);// find the index in the table for this key
		if (table.get(index) == null) {
			ArrayList<MyElement<T1, T2>> chain = new ArrayList<MyElement<T1, T2>>();
			chain.add(new MyElement<T1, T2>(key, value));
			table.set(index, chain);
			size++;
		} else {
			ArrayList<MyElement<T1, T2>> chain = table.get(index); // getting the list of elements at that index in the
																	// table.

			int inChainIndex = 0;
			MyElement<T1, T2> e = new MyElement<T1, T2>(key, value); // created this new object to be inserted.
			// the below loop is looks in the chain for existing keys. If the new key
			// already exists then only the value gets updated.
			while (inChainIndex < chain.size()) {
				if (chain.get(inChainIndex).key.equals(key)) {
					// update the existing value
					chain.set(inChainIndex, e);
					break;
				}
				inChainIndex++;
			}
			if (inChainIndex >= chain.size()) {
				chain.add(e);
				this.size++;
			}
		}

	}

	public void delete(T1 key) {
		int index = hash(key); // calculate the index using hash function
		ArrayList<MyElement<T1, T2>> chain = table.get(index);
		if (chain == null) {
			// no such element exception
			throw new NoSuchElementException();
		} else {
			boolean deleted = false; // flag for checking if the key is present in the map or not.
			for (MyElement<T1, T2> e : chain) {
				if (e.key.equals(key)) {
					chain.remove(e);
					size--;// reduce number of entries in the map
					// if due to this removal, chain becomes empty then we make the table entry null
					if (chain.size() == 0) {
						table.set(index, null);
					}

					deleted = true;
					break;
				}
			}
			if (!deleted) {
				throw new NoSuchElementException();
			}
		}
	}

	public boolean contains(T1 key) {
		int index = hash(key);
		ArrayList<MyElement<T1, T2>> chain = table.get(index);
		if (chain == null) {
			return false;
		} else {
			for (MyElement<T1, T2> e : chain) {
				if (e.key.equals(key)) {
					return true;
				}
			}
			return false;
		}

	}

	public T2 getByKey(T1 key) {
		int index = hash(key);
		ArrayList<MyElement<T1, T2>> chain = table.get(index);
		if (chain == null) {
			// key not found exception
			throw new NoSuchElementException();
		} else {
			for (MyElement<T1, T2> e : chain) {
				if (e.key.equals(key)) {
					return e.value;
				}
			}
			throw new NoSuchElementException();
		}
	}

	public int size() {
		return this.size;
	}

	public void print() {
		int count = 0; // it is used for preventing the comma from getting printed unnecessarily while
		// printing the map.
		System.out.print("[ ");
		for (int i = 0; i < tableLength; i++) {
			ArrayList<MyElement<T1, T2>> chain = table.get(i);
			if (chain == null) {
				continue;
			} else {
				for (MyElement<T1, T2> e : chain) {
					count++;
					if (count < size) {
						System.out.print(e.key + "-> " + e.value + ", ");
					} else {
						System.out.print(e.key + "-> " + e.value);
					}

				}
			}
		}
		System.out.print(" ]");
		System.out.println();
	}

	// this iterator allows to iterate through the keys of the map.
	public Iterator<T1> iterator() {
		return new MapKeysIterator<T1>();
	}

	// inner class for implementing the iterator
	class MapKeysIterator<T1> implements Iterator<T1> {
		int inTableIndex;
		int inChainIndex;
		int count;// to decide if there are more elements in the map based on comparison with
					// size.

		public MapKeysIterator() {
			inTableIndex = 0;
			inChainIndex = 0;
			count = 0;
		}

		public boolean hasNext() {
			return count < size;
		}

		public T1 next() {
			if (count >= size) {
				// no such element
				throw new NoSuchElementException();
			} else {
				while (inTableIndex < table.size() && table.get(inTableIndex) == null) {
					inTableIndex++;
				}
				T1 key = (T1) table.get(inTableIndex).get(inChainIndex).key;
				count++;
				inChainIndex++;
				if (inChainIndex >= table.get(inTableIndex).size()) {
					inTableIndex++;
					inChainIndex = 0;
				}
				return key;
			}
		}
	}

}

class MyElement<T1, T2> {
	T1 key;
	T2 value;

	MyElement(T1 key, T2 value) {
		this.key = key;
		this.value = value;
	}
}