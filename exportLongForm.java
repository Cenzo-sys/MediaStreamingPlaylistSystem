package mediastreaming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * 
 * creates a new file that contains csv data on all podcasts that are longer or equal to
 * thrity minutes in length in sorted order
 */ 
public class exportLongForm implements Runnable {

	private static final String FILE_TO_WRITE = "LongForm_PodcastPlaylist.csv";
	private static final int JCF_SIZE = 0;
	
	@Override
	public void run() {
		exportLongFormPlaylist(MediaStreamingPlaylistSystem.longFormCutOff(MediaStreamingPlaylistSystem.list));
	}

	/**
	 * creates a file that then stores objects within list in a specific output
	 * if the list contains objects, an output will tell the user how many were written
	 * to file and what the name of the file is
	 * if no objects are found, the output will tell the user that no podcasts were found 
	 * and therefore, no list was created and the file was not created
	 * @param list
	 */
	private void exportLongFormPlaylist(List<Podcast> list) {
		File file = new File(FILE_TO_WRITE);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
			
			bw.write("PodcastId,Title,Series,Category,DurationSeconds");
			
			for (Podcast podcast : list) {
				bw.write("\n"+podcast.getPodcastID()+","+podcast.getTitle()+","+podcast.getSeries()+","
						+podcast.getCatgeory()+","+podcast.getDuration());
			}
			
			if(list.size() != JCF_SIZE) {
				System.out.println("Playlist created: "+list.size()+" podcasts written to "+FILE_TO_WRITE);
			} else {
				System.out.println("No podcast found. List not created");
				file.deleteOnExit();
				System.out.println("Files generated will be deleted once program has stopped running.");
			}
		} catch (Exception e) {
			System.out.println("Issue creating the file: "+e.getMessage());
		}
		
	}

}

