package se.patrickthomsson.util;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionUtilTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnEmptyStringIfCollectionIsEmpty() {
		assertEquals("", CollectionUtil.asDelimitedString(Collections.EMPTY_LIST));
	}
	
	@Test
	public void shouldReturnElementAsStringWhenCollectionContainsOneElement() {
		assertEquals("foo", CollectionUtil.asDelimitedString(Arrays.asList("foo")));
	}
	
	@Test
	public void shouldReturnElementsDelimitedWithCommaIfNoDelimiterSpecified() {
		assertEquals("foo,bar", CollectionUtil.asDelimitedString(Arrays.asList("foo", "bar")));
	}
	
	@Test
	public void shouldReturnElementsDelimitedWithSpecifiedDelimiterIfDelimiterSpecified() {
		assertEquals("foo...bar", CollectionUtil.asDelimitedString(Arrays.asList("foo", "bar"), "..."));
	}

}
