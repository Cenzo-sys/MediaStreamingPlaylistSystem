/**
 * 
 */
package mediastreaming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**  
 * Vincenzo Scappaticci 40489763
 */
public class MediaStreamingPlaylistSystem {

	private static final String FILE_NAME = "podcasts-1.csv";
	private static final int JCF_SIZE = 0;
	private static final int LONG_FORM_CUTOFF = 1800;
	private static final int DEFAULT_DURATION = 0;
	public static List<Podcast> list = new LinkedList<Podcast>();
	
	/**
	 * loads details from a given csv and then displays a menu of set options to 
	 * display all, search through via Title or Series and to export any
	 * podcasts over thirty minutes in length
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			loadFromCSV(); 
			displayMenu();
		} catch (Exception e) {
			System.out.println("Contact Admin: "+e.getMessage());
		}	
	}

	/**
	 * displays a menu for the user to interact with via numbered inputs
	 * the menu appearing has been staggered using thread.sleep, stopping it
	 * from appearing after incorrect inputs that aren't an int also
	 */
	private static void displayMenu() {
		List<Podcast> methodResults = new LinkedList<Podcast>();
		//don't forget to close scanner
		Scanner sc = new Scanner(System.in);
		int choice;
	
		do {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			System.out.println("====================\n"+"Media Streaming Playlist System\n"+"====================\n");
			System.out.println("1. Display all podcasts");
			System.out.println("2. Podcasts search : Enter word to search (appears in either Title or Series)");
			System.out.println("3. Export long-form playlist (>=30 min)");
			System.out.println("0. Exit");
			
			while(!sc.hasNextInt()) {
				System.out.println("Please enter one of the numbered options.");
				sc.next(); //discard invalid value
			}
			
			choice = sc.nextInt();
			sc.nextLine(); //discards the enter and prepares for search input
			
			switch(choice) {
			case 1 : {
				displayAllPodcasts(list);
				break;
			}
			case 2 : {
				methodResults = searchByTitleOrSeries(sc);
				if(methodResults.size() != JCF_SIZE) {
					displaySearchResults(methodResults);
				}
				break;
			}
			case 3 : {
				Thread t = new Thread(new exportLongForm());
				t.start();
				break;
			}
			case 0 : {
				System.out.println("Exiting Program. \nGoodbye.");
				break;
			}
			default : {
				System.out.println("Invalid numbered choice.");
				break;
			}
			}
		} while (choice != 0);
		sc.close();
	}


	/**
	 * uses an advanced for loop to iterate through the objects in displayList and utilises
	 * the displayAll method to produce a clean output of the values in the object
	 * @param displayList
	 */
	private static void displayAllPodcasts(List<Podcast> displayList) {
		for (Podcast podcast : displayList) {
			System.out.println("-------------------");
			podcast.displayAll();
			System.out.println("-------------------");
		}
	} 

	/**
	 * uses a separate jcf to store any object that possesses the Title or Series
	 * that is provided by the user's input. This jcf is then checked to see if it has
	 * stored any objects, if not, the user is told that no podcasts were found with that
	 * given Title or Series
	 * 
	 * @param sc
	 * @return
	 */
	private static List<Podcast> searchByTitleOrSeries(Scanner sc) {
		List<Podcast> searchResults = new LinkedList<Podcast>();
		System.out.println("Search by Title or Series: ");
		String input = sc.nextLine();
		
		for (Podcast podcast : list) {
			if(podcast.getTitle().equalsIgnoreCase(input) || podcast.getSeries().equalsIgnoreCase(input)) {
				searchResults.add(podcast);
			} 
		}
		
		if(searchResults.size() == JCF_SIZE) {
			System.out.println("No podcasts found.");
		}
		return searchResults;
	}

	/**
	 * dislays the search results of the searchByTitleOrSeries method
	 * @param methodResults
	 */
	private static void displaySearchResults(List<Podcast> methodResults) {
		System.out.println("Search Results: ");
		if(methodResults.size() != JCF_SIZE)
		for (Podcast podcast : methodResults) {
			podcast.displayAll();
		}
	}
	
	/**
	 * iterates through the list in which the csv was read into and checks if any of the
	 * objects inside it have a duration greater than thrity minutes
	 * if so, they are then added to a seperate jcf that is sorted and then returned
	 * @param playList 
	 * @return
	 */
	public static List<Podcast> longFormCutOff(List<Podcast> playList) {
		Iterator<Podcast> iterator = playList.iterator();
		List<Podcast> cutOff = new LinkedList<Podcast>();
		
		while(iterator.hasNext()) {
			//object
			Podcast podcast = iterator.next();
			
			if(podcast.getDuration() >= LONG_FORM_CUTOFF) {
				cutOff.add(podcast);
			}
		}
		
		//sort the list by ID
		Collections.sort(cutOff, new CompareID());
		return cutOff;
	}
	
	/**
	 * reads in string values from the csv provided and splits them at each comma
	 * these values are then stored in seperate getters and setters in the object Podcast p,
	 * in which malformed data rules are in place in case of incorrect data in the csv
	 * and are then added to the jcf (list) and are displayed as they are added
	 * output also shows how many podcasts were attempted, how many were actually stored and
	 * how many were skipped
	 */
	private static void loadFromCSV() {
		List<Podcast> excludedPodcasts = new LinkedList<Podcast>();
		File file = new File(FILE_NAME);
		String line; //holds csv line string
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))){
			
			buffer.readLine(); //skips header of csv
			
			while((line = buffer.readLine()) != null) {
				String[] parts = line.split(","); //array to store the line string in separate elements
				
				//object instantiation
				Podcast p = new Podcast();
				
				//podcastID
				try {
					p.setPodcastID(Integer.parseInt(parts[0]));
				} catch (NumberFormatException e) {
					System.out.println("Podcast ID could not be found for this line of csv. Skipping line.");
					excludedPodcasts.add(p);
					buffer.readLine(); //skips line
				}
				
				//title
				try {
					p.setTitle(parts[1]);
				} catch (Exception e) {
					System.out.println("Title could not be found for this line of csv. Skipping line.");
					excludedPodcasts.add(p);
					buffer.readLine(); //skips line
				}
				
				//series
				try {
					p.setSeries(parts[2]);
				} catch (Exception e) {
					System.out.println("Series could not be found for this line of csv. Skipping line.");
					excludedPodcasts.add(p);
					buffer.readLine(); //skips line
				}
				
				//category
				try {
					switch(parts[3]) {
					case "News" : {
						p.setCatgeory(Category.NEWS);
						break;
					}
					case "Education" : {
						p.setCatgeory(Category.EDUCATION);
						break;
					}
					case "Comedy" : {
						p.setCatgeory(Category.COMEDY);
						break;
					}
					case "Sports" : {
						p.setCatgeory(Category.SPORTS);
						break;
					}
					default : {
						p.setCatgeory(Category.OTHER);
						System.out.println("Category could not be found for the podcast ID: "+p.getPodcastID()+". "+"Updating to default value of: "+p.getCatgeory());
						break;
			 		}
					}
				} catch (Exception e) {
					System.out.println("Could not assign default category to line: "+e.getMessage());
				}
				
				//duration
				try {
					p.setDuration(Integer.parseInt(parts[4]));
				} catch (NumberFormatException e) {
					System.out.println("Duration could not be found for this line of csv. Updating to default value.");
					p.setDuration(DEFAULT_DURATION);
				}
				
				list.add(p);
				p.displayAll();
			}
			
			System.out.println("Attempted to read "+list.size()+" podcasts.");
			System.out.println("Successfully added: "+list.size());
			System.out.println("Skipped: "+excludedPodcasts.size());
			
		} catch (Exception e) {
			System.out.println("Problem: "+e.getMessage());
		}
		
		
		
	}

	
	
	
}
