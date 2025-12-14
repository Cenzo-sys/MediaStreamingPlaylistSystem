/**
 * 
 */
package mediastreaming;

import java.util.Comparator;

/**
 * Vincenzo Scappaticci 40489763
 * compares two objects of the same class and assigns a value depending on their order
 * set in descending order
 */
public class CompareID implements Comparator<Podcast> {

	@Override
	public int compare(Podcast o1, Podcast o2) {
		return Integer.compare(o2.getPodcastID(), o1.getPodcastID());
	}

}
