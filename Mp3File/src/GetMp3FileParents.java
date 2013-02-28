import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

/**
 * Use common-io to get list of audio files
 * @author Glen
 *
 */
public class GetMp3FileParents {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		System.out.println("GetMp3Files.main() - start");
		
		String ext[] =  {"mp3", "flac"};
		Collection<File> files = FileUtils.listFiles(new File("C:/Users/Glen/Downloads"), ext, true);
		
		Set<String> parents = new TreeSet<String>();
		for (File file : files){
			parents.add(file.getParent());
		}
		
		for (String p : parents){
			System.out.println(p);
		}

		System.out.println("GetMp3Files.main() - end");
	}

}
