package se.patrickthomsson.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionUtilTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnEmptyStringIfCollectionIsEmpty() {
		String string = CollectionUtil.asDelimitedString(Collections.EMPTY_LIST);
        assertEquals("", string);
	}
	
	@Test
	public void shouldReturnElementAsStringWhenCollectionContainsOneElement() {
		List<String> asList = Arrays.asList("foo");
        String asDelimitedString = CollectionUtil.asDelimitedString(asList);
        assertEquals("foo", asDelimitedString);
	}
	
	@Test
	public void shouldReturnElementsDelimitedWithCommaIfNoDelimiterSpecified() {
		List<String> asList = Arrays.asList("foo", "bar");
        String asDelimitedString = CollectionUtil.asDelimitedString(asList);
        assertEquals("foo,bar", asDelimitedString);
	}
	
	@Test
	public void shouldReturnElementsDelimitedWithSpecifiedDelimiterIfDelimiterSpecified() {
		List<String> asList = Arrays.asList("foo", "bar");
        String asDelimitedString = CollectionUtil.asDelimitedString(asList, "...");
        assertEquals("foo...bar", asDelimitedString);
	}

}
