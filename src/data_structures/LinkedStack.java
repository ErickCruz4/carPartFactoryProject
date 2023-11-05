package data_structures;

import java.util.NoSuchElementException;

import interfaces.Stack;


/**
 * This implementation of the Stack uses Nodes directly and works similar to the SinglyLinkedList.
 * Similarly we could use a DoublyLinked implementation, but this is more efficient in terms of 
 * effort and memory. Why is that??
 * @author Gretchen Bonilla
 *
 * @param <E>
 */
public class LinkedStack<E> implements Stack<E> {

	/**
	 * Class that holds a single piece of data and a single reference to the node that goes after it.
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */
	private class Node<E> {
		// References the node that goes after it
		private Node<E> next;
		// The data
		private E element;
		
		/**
		 * Create empty node.
		 */
		public Node() {
			this.next = null;
			this.element =null;
		}
		/**
		 * Creates node with data elm and whose next node is next
		 * @param next - (Node) The node the goes after this one
		 * @param elm - (E) the data that this node holds
		 */
		public Node(Node<E> next, E elm) {
			this.next = next;
			this.element = elm;
		}
		/**
		 * Creates a node that holds the element elm, but doesn't have a next node yet.
		 * @param elm - (E) the data that the node will hold
		 */
		public Node(E elm) {
			this.element = elm;
			this.next=null;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E element) {
			this.element = element;
		}
		/**
		 * EMpties the node to help with clean up
		 */
		public void clear() {
			this.next = null;
			this.element=null;
		}	
		
	}
	// Node that REPRESENTS the top of the Stack (not dummy)
	private Node<E> top;
	// Unlike with an internal List we now need to keep track of size ourselves 
	private int size;
	
	public LinkedStack() {
		// Initially it's empty
		this.top=null;
		this.size = 0;
		
	}
	/**
	 * Check if Stack is empty by seeing if the size is zero.
	 * We could also check if top == null
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Checks the current size of the Stack
	 * @return
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	/**
	 * Checks the top value in the Stack, this is the element stored
	 * in the Node top.
	 * @return
	 */
	@Override
	public E top() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.top.getElement();
	}

	/**
	 * Removes and returns the current value at the top.
	 * This is done saving the value at node top and then updating its reference to the next node.
	 */
	@Override
	public E pop() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		E topValue = this.top.getElement();
		Node<E> currTop = top;
		top = top.getNext();
		currTop.clear();
		this.size--;
		return topValue;
	}

	/**
	 * Inserts a new value to the Stack. This is done by creating a new node
	 * and making top point to it.
	 */
	@Override
	public void push(E obj) {
		if(obj == null)
			throw new IllegalArgumentException();
		// Why do we not worry too much about the edge cases here?
		Node<E> newTop = new Node<E>(top, obj);
		top = newTop;
		size++;	
	}

	/**
	 * Empty the Stack by removing every item from it.
	 */
	@Override
	public void clear() {
		while(!isEmpty())
			pop();
		
	}

	@Override
	public String toString() {
		String str = "{ ";
		Node<E> curr = top;
		while(curr != null) {
			str += curr.getElement() + " ";
		}
		return str + "}";
	}

}
