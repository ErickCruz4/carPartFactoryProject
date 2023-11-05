package data_structures;

import java.util.NoSuchElementException;

import interfaces.List;
import interfaces.Queue;

/**
 * Implements the Queue interface using a List to store the data.
 * Any List can be used, but the most efficient would be one that has direct access to the start and end of the list.
 * @author Gretchen Bonilla
 *
 * @param <E>
 */
public class ListQueue<E> implements Queue<E>{
	
	private List<E> theData;
	
	public ListQueue() {
		// We picked DoublyLinkedList since it has a dummy header and trailer.
		this.theData = new DoublyLinkedList<E>();
	}

	@Override
	public int size() {
		return theData.size();
	}

	@Override
	public boolean isEmpty() {
		return this.theData.isEmpty();
	}

	@Override
	public void clear() {
		this.theData.clear();		
	}

	@Override
	public void enqueue(E obj) {
		// if(obj == null)
		// 	throw new IllegalArgumentException();
		this.theData.add(obj);
		
	}

	@Override
	public E dequeue() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		E oldValue = this.theData.get(0);
		this.theData.remove(0);
		return oldValue;
	}

	@Override
	public E front() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.theData.get(0);
	}

	@Override
	public String toString() {
		String str = "{ ";
		for (E e : theData) {
			str += e + " ";
		}
		return str + "}";
	}

}
