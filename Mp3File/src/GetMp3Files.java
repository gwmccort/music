import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * Use common-io to get list of audio files
 * @author Glen
 *
 */
public class GetMp3Files {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		System.out.println("GetMp3Files.main() - start");
		
		String ext[] =  {"mp3", "flac"};
		Collection<File> files = FileUtils.listFiles(new File("C:/Users/Glen/Downloads"), ext, true);
		for (File file : files){
			System.out.println("file:" + file);
		}

		System.out.println("GetMp3Files.main() - end");
	}

}
