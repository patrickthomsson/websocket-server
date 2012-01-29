package se.patrickthomsson.util;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class IntervalCreatorTest {
	
	@Test
	public void shouldCreateInterval() {
		Collection<Interval> intervals = IntervalCreator.create(1, 1);
		assertEquals(1, intervals.size());
		
		Interval i = intervals.iterator().next();
		assertEquals(0, i.getStart());
		assertEquals(1, i.getEnd());
	}
	
	@Test
	public void shouldCreateIntervalWithTwoEntries() {
		Collection<Interval> intervals = IntervalCreator.create(2, 1);
		assertEquals(2, intervals.size());
		
		Iterator<Interval> iterator = intervals.iterator();
		Interval firstInterval = iterator.next();
		Interval secondInterval = iterator.next();
		
		assertEquals(0, firstInterval.getStart());
		assertEquals(1, firstInterval.getEnd());

		assertEquals(1, secondInterval.getStart());
		assertEquals(2, secondInterval.getEnd());
	}
	
	@Test
	public void shouldNotHaveToFillAFullStep() {
		Collection<Interval> intervals = IntervalCreator.create(1, 2);
		assertEquals(1, intervals.size());
		
		Interval i = intervals.iterator().next();
		assertEquals(0, i.getStart());
		assertEquals(1, i.getEnd());
	}
	
	@Test
	public void shouldNotHaveToFillTheLastFullStep() {
		Collection<Interval> intervals = IntervalCreator.create(5, 2);
		assertEquals(3, intervals.size());
		
		Iterator<Interval> iterator = intervals.iterator();
		Interval firstInterval = iterator.next();
		Interval secondInterval = iterator.next();
		Interval thirdInterval = iterator.next();
		
		assertEquals(0, firstInterval.getStart());
		assertEquals(2, firstInterval.getEnd());

		assertEquals(2, secondInterval.getStart());
		assertEquals(4, secondInterval.getEnd());
		
		assertEquals(4, thirdInterval.getStart());
		assertEquals(5, thirdInterval.getEnd());
	}
	
	@Test
	public void should() {
		Collection<Interval> intervals = IntervalCreator.create(0, 2);
		assertEquals(1, intervals.size());
		
		Interval i = intervals.iterator().next();
		assertEquals(0, i.getStart());
		assertEquals(0, i.getEnd());
	}
	
	
}
