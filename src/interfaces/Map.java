package interfaces;

import java.io.PrintStream;
/**
 * 
 * @author Fernando J Bermudez (bermed28)
 *
 */
public interface Map<K,V> {
	public int size();
	public boolean isEmpty();
	public V get(K key);
	public V put(K key, V value);
	public V remove(K key);
	public boolean containsKey(K key);
	public void clear();
	public List<K> getKeys();
	public List<V> getValues();
	public void print(PrintStream out);
}