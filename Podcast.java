/**
 * 
 */
package mediastreaming;

/**
 * Vincenzo Scappaticci 40489763
 * creates an object of class Podcast
 */
public class Podcast {

	private int podcastID;
	private String title;
	private String series;
	private Category catgeory;
	private int duration; 
	
	public Podcast() {
		 
	}
 
	public Podcast(int podcastID, String title, String series, Category catgeory, int duration) {
		this.podcastID = podcastID;
		this.title = title;
		this.series = series;
		this.catgeory = catgeory;
		this.duration = duration;
	}

	public int getPodcastID() {
		return podcastID;
	}

	public void setPodcastID(int podcastID) {
		this.podcastID = podcastID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Category getCatgeory() {
		return catgeory;
	} 

	public void setCatgeory(Category catgeory) {
		this.catgeory = catgeory;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/** 
	 * Displays class variables in a organised manner for user interface
	 */
	public void displayAll() {
		System.out.println("Episode ID : "+podcastID);
		System.out.println("Title : "+title);
		System.out.println("Series : "+series);
		System.out.println("Category : "+catgeory);
		System.out.println("Duration (Seconds) : "+duration+"\n");
	}
}
