package se.patrickthomsson.util;

import java.util.ArrayList;
import java.util.Collection;

public class IntervalCreator {
	
	private static final int MAX_LOOPS = 10000;

	public static Collection<Interval> create(int maxValue, int step) {
		Collection<Interval> intervals = new ArrayList<Interval>();
		
		int counter = 0;

		while(counter < MAX_LOOPS) {
			int start = step * counter;
			int end = step * (counter + 1);
			
			if(end > maxValue) {
				end = maxValue;
			}
			
			intervals.add(new Interval(start, end));
			
			if(end == maxValue || maxValue == 0) {
				break;
			}
			
			counter++;
		}
		
		return intervals;
	}

}
