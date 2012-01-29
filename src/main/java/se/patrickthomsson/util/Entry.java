package se.patrickthomsson.util;

public class Entry<K,V> {
	
	private K key;
	private V value;
	
	public Entry(K key, V value) {
		this.key = key;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
	
	public String toString () {
		return String.format(String.format("Entry: key='%s, value=%s'", key, value));
	}

}
