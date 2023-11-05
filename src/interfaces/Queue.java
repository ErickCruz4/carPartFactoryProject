package interfaces;
/**
 * This is a data ADT that doesn't use positions only the order of arrival.
 * Its FIFO, which means that the first item to arruve is the first item to leave the Queue
 * @author The Dark Ass
 *
 * @param <E>
 */
public interface Queue<E> {
	/**
	 * Returns how many items are in the Queue
	 * @return (int) Number of values in the Queue
	 */
	public int size();
	/**
	 * Check whether the Queue is empty, true if it is, false otherwise
	 * @return
	 */
	public boolean isEmpty();
	/**
	 * Removes everything from the queue
	 */
	public void clear();
	/**
	 * Adds a new vale to the Queue, it goes to the end of the Queue
	 * @param obj
	 */
	public void enqueue(E obj);
	/**
	 * Removes and returns the front item from the Queue, this is the value that has been the longest in the Queue.
	 * @return (E) Item removed
	 */
	public E dequeue();
	/**
	 * Returns the item at the front of the Queue. This would be the item that has been in the Queue the longest.
	 * @return
	 */
	public E front();
}
