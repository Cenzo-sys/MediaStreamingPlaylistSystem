package mediastreaming;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PodcastTesting {

	//object
	Podcast podcast;
	
	//positive pathways
	int podcastIDValid;
	String titleValid;
	String seriesValid;
	Category categoryValid;
	int durationValid;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		//object instantiation
		podcast = new Podcast();
		
		//variables
		podcastIDValid = 101;
		titleValid = "History Bites";
		seriesValid = "Past & Present";
		categoryValid = Category.EDUCATION;
		durationValid = 2100;
	}

	@Test
	void testPodcastNoArgs() {
		assertNotNull(podcast);
	}

	@Test
	void testPodcastArgs() {
		podcast = new Podcast(podcastIDValid, titleValid, seriesValid, categoryValid, durationValid);
		assertEquals(podcastIDValid, podcast.getPodcastID());
		assertEquals(titleValid, podcast.getTitle());
		assertEquals(seriesValid, podcast.getSeries());
		assertEquals(categoryValid, podcast.getCatgeory());
		assertEquals(durationValid, podcast.getDuration());
	}

	@Test
	void testGetSetPodcastID() {
		podcast.setPodcastID(podcastIDValid);
		assertEquals(podcastIDValid, podcast.getPodcastID());
	}
 
	@Test
	void testGetSetTitle() {
		podcast.setTitle(titleValid);
		assertEquals(titleValid, podcast.getTitle());
	}

	@Test
	void testGetSetSeries() {
		podcast.setSeries(seriesValid);
		assertEquals(seriesValid, podcast.getSeries());
	}

	@Test
	void testGetSetCatgeory() {
		podcast.setCatgeory(categoryValid);
		assertEquals(categoryValid, podcast.getCatgeory());
	}

	@Test
	void testGetSetDuration() {
		podcast.setDuration(durationValid);
		assertEquals(durationValid, podcast.getDuration());
	}

}
