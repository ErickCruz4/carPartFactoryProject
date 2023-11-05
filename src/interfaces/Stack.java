package interfaces;

import java.util.NoSuchElementException;

public interface Stack<E> {
	/**
	 * Checks if the Stack is empty. True if empty, false otherwise.
	 * @return (boolean) whether Stack is empty
	 */
	public boolean isEmpty();
	/**
	 * Returns the current size of the Stack
	 * @return (int) size of Stack
	 */
	public int size();
	/**
	 * Item at the top of the Stack, most recently added item.
	 * @return (E) item at top of stack
	 * @throws NoSuchElementException
	 */
	public E top();
	/**
	 * Removes the top item from the Stack. The most recently added item.
	 * @return (E) The removed item.
	 * @throws NoSuchElementException
	 */
	public E pop();
	/**
	 * Inserts the value obj into the Stack.
	 * @param obj - (E) The item to add
	 * @throws IllegalArgumentException
	 */
	public void push(E obj);
	/**
	 * Empties the Stack, by removing everything.
	 */
	public void clear();

}
