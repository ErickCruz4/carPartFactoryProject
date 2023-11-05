package data_structures;

import java.io.PrintStream;

import interfaces.HashFunction;
import interfaces.List;
import interfaces.Map;


public class HashTableSC<K, V> implements Map<K, V> {
	
	private static class BucketNode<K,V>{
		private K key;
		private V value;
		
		public BucketNode(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {return key;}
		public V getValue() {return value;}
		
	}
	
	private int currentSize;
	private List<BucketNode<K,V>>[] buckets;
	private HashFunction<K> hashFunction;

	public HashTableSC(int initialCapacity, HashFunction<K> hashFunction) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Size must be at least 1");
		if(hashFunction == null) throw new IllegalArgumentException("Must provide a hash function");
		
		this.currentSize = 0;
		this.buckets = new SinglyLinkedList[initialCapacity];
		for (int i = 0; i < initialCapacity; i++) {
			buckets[i] = new SinglyLinkedList<BucketNode<K,V>>();
		}
		this.hashFunction = hashFunction;
	}
	
	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public V get(K key) {
		if(key == null) throw new IllegalArgumentException("Invalid parameter");
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		List<BucketNode<K,V>> bucket = buckets[targetBucket];
		for (BucketNode<K, V> bucketNode : bucket) {
			if(bucketNode.getKey().equals(key)) return bucketNode.getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		if(key == null || value == null) throw new IllegalArgumentException("Invalid parameters");
		V oldValue = remove(key);
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		List<BucketNode<K,V>> bucket = buckets[targetBucket];
		bucket.add(0, new BucketNode<>(key, value));
		currentSize++;
		return oldValue;
	}

	@Override
	public V remove(K key) {
		if(key == null) throw new IllegalArgumentException("Invalid parameter");
		int targetBucket = hashFunction.hashCode(key) % buckets.length;	
		List<BucketNode<K,V>> bucket = buckets[targetBucket];
		
		int pos = 0;
		for (BucketNode<K, V> bucketNode : bucket) {
			if (bucketNode.getKey().equals(key)) {
				bucket.remove(pos);
				currentSize--;
				return bucketNode.getValue();
			} else pos++;
		}
		
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	@Override
	public void clear() {
		currentSize = 0;
		for (List<BucketNode<K, V>> list : buckets) {
			list.clear();
		}
	}

	@Override
	public List<K> getKeys() {
		List<K> result = new SinglyLinkedList<>();
		for (int i = 0; i < buckets.length; i++) {
			for (BucketNode<K,V> BN : buckets[i]) {
				result.add(0, BN.getKey());
			}
		}
		
		return result;
	}

	@Override
	public List<V> getValues() {
		List<V> result = new SinglyLinkedList<>();
		for (int i = 0; i < buckets.length; i++) {
			for (BucketNode<K,V> BN : buckets[i]) {
				result.add(0, BN.getValue());
			}
		}
		
		return result;
	}

	@Override
	public String toString() {
		String str = "{ ";
		for (K key : this.getKeys()) {
			str += "(" + key + ", " + this.get(key) + ") ";
		}
		return str + "}";
	}
	@Override
	public void print(PrintStream out) {
		// TODO Auto-generated method stub

	}

}
