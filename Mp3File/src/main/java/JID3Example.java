
import java.io.File;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.ID3v1;
import org.jaudiotagger.tag.id3.ID3v1Tag;

public class JID3Example
{
	
	public static void main(String[] args) throws Exception {
		
		MP3File mp3File = new MP3File(new File("C:/Users/Public/Music/Sample Music/Kalimba.mp3"));
		ID3v1 tag = mp3File.getID3v1Tag();
		System.out.println("artist:" + tag.getArtist());
		System.out.println("album:" + tag.getAlbum());
		System.out.println("bit rate:" + mp3File.getBitRate());
		
		
	}
	/*
    private static void main(String[] args)
        throws Exception
    {
        // the file we are going to modify
        File oSourceFile = new File("some_file.mp3");

        // create an MP3File object representing our chosen file
        MediaFile oMediaFile = new MP3File(oSourceFile);

        // create a v1.0 tag object, and set some values
        ID3V1_0Tag oID3V1_0Tag = new ID3V1_0Tag();
        oID3V1_0Tag.setAlbum("Album");
        oID3V1_0Tag.setArtist("Artist");
        oID3V1_0Tag.setComment("Comment");
        oID3V1_0Tag.setGenre(ID3V1Tag.Genre.Blues);
        oID3V1_0Tag.setTitle("Title");
        oID3V1_0Tag.setYear("1999");
       
        // set this v1.0 tag in the media file object
        oMediaFile.setID3Tag(oID3V1_0Tag);
       
        // create a v2.3.0 tag object, and set some frames
        ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();
        TPE1TextInformationID3V2Frame oTPE1 = new TPE1TextInformationID3V2Frame("Lead Performer");
        oID3V2_3_0Tag.setTPE1TextInformationFrame(oTPE1);
        TRCKTextInformationID3V2Frame oTRCK = new TRCKTextInformationID3V2Frame(3, 9);
        oID3V2_3_0Tag.setTRCKTextInformationFrame(oTRCK);
        TIT2TextInformationID3V2Frame oTIT2 = new TIT2TextInformationID3V2Frame("Song Title");
        oID3V2_3_0Tag.setTIT2TextInformationFrame(oTIT2);
       
        // set this v2.3.0 tag in the media file object
        oMediaFile.setID3Tag(oID3V2_3_0Tag);
       
        // update the actual file to reflect the current state of our object 
        oMediaFile.sync();
    }
*/
	
}