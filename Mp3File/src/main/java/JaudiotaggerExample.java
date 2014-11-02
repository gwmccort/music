import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class JaudiotaggerExample {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws ReadOnlyFileException 
	 * @throws TagException 
	 * @throws IOException 
	 * @throws CannotReadException 
	 */
	public static void main(String[] args) throws  Exception {
		
		
		// disable java logging
        Logger globalLogger = Logger.getLogger("");
        Handler[] handlers = globalLogger.getHandlers();
        for (Handler handler : handlers) {
                globalLogger.removeHandler(handler);
        }
		
        // get all files from my music folder
		String ext[] =  {"mp3"};
//		Collection<File> files = FileUtils.listFiles(new File("C:/Users/Glen/Music"), ext, true);
		Collection<File> files = FileUtils.listFiles(new File("C:/Users/Glen/Downloads/Torrents/Completed"), ext, true);
		for (File file : files){
//			AudioFile f = AudioFileIO.read(new File("C:/Users/Public/Music/Sample Music/Kalimba.mp3"));
			AudioFile mp3File = AudioFileIO.read(file);
			AudioHeader header = mp3File.getAudioHeader();
			Tag tag = mp3File.getTag();
			
			System.out.println("\n\n--------------- " + file + " --------------------------");
			System.out.println("field count:" + tag.getFieldCount());
			System.out.println("artist:" + tag.getFirst(FieldKey.ARTIST));
			System.out.println("album:" + tag.getFirst(FieldKey.ALBUM));
			System.out.println("title:" + tag.getFirst(FieldKey.TITLE));
			System.out.println("comment:" + tag.getFirst(FieldKey.COMMENT));
			System.out.println("year:" + tag.getFirst(FieldKey.YEAR));
			System.out.println("track:" + tag.getFirst(FieldKey.TRACK));
			System.out.println("disc#:" + tag.getFirst(FieldKey.DISC_NO));
			System.out.println("composer:" + tag.getFirst(FieldKey.COMPOSER));
			System.out.println("artist sort:" + tag.getFirst(FieldKey.ARTIST_SORT));
			System.out.println("bit rate:" + header.getBitRate());
			System.out.println("sample rate:" + header.getSampleRate());
		}
		
		
	}

}
