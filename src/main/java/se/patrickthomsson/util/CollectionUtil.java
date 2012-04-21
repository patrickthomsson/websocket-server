package se.patrickthomsson.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
	
	private static final String EMPTY_STRING = "";
	private static final String DEFAULT_DELIMITER = ",";
	
	/**
	 * Returns a String representation of the elements delimited with commas (",") 
	 * 
	 * @param elements the elements to build the String out of
	 * @return the delimited String
	 */
	public static <T> String asDelimitedString(Collection<T> elements) {
		return asDelimitedString(elements, DEFAULT_DELIMITER);
	}
	
	/**
	 * Returns a String representation of the elements delimited with the specified delimiter 
	 * 
	 * @param elements the elements to build the String out of
	 * @param delimiter the specified delimiter
	 * @return the delimited String
	 */
	public static <T> String asDelimitedString(Collection<T> elements, String delimiter) {
		StringBuilder sb = new StringBuilder();
		Iterator<T> iterator = elements.iterator();
		while(iterator.hasNext()) {
			sb.append(iterator.next().toString());
			sb.append(iterator.hasNext() ? delimiter : EMPTY_STRING);
		}
		return sb.toString();
	}

}
