import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;


public class GetMp3Files {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		System.out.println("GetMp3Files.main() - start");
		
		String ext[] =  {"mp3"};
		Collection<File> files = FileUtils.listFiles(new File("C:/Users/Glen/Downloads/Torrents/Completed"), ext, true);
		for (File file : files){
			System.out.println("file:" + file);
		}

		System.out.println("GetMp3Files.main() - end");
	}

}
