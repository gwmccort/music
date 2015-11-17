package gwm.musicfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Handler;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * MP3 File, gets info from the mp3 tag.
 * 
 * @author gwmccort
 * 
 */
public class Mp3File {
	File file;
	String title;
	String artist;
	String albumArtist;
	String album;
	String bitRate;

//	 static final String INPUT_FILE = "C:\\Users\\Public\\Music";
//	 static final String INPUT_FILE = "P:\\FANTOM HDS721010CLA332\\Music\\My Music\\Beastie Boys";
//	 static final String INPUT_FILE = "C:\\Users\\Glen\\Downloads";
//	static final String INPUT_DIR = "P:\\FANTOM HDS721010CLA332\\Media\\Music";
	static final String INPUT_DIR = "C:\\Users\\Public\\Music";
//	static final String INPUT_DIR = "P:\\FANTOM HDS721010CLA332\\Media\\Music\\Tea Leaf Green\\Coffee Bean Brown Comes Alive";
//	static final String INPUT_DIR = "C:\\Users\\Glen\\Music\\deleteme";
	static final String OUT_FILE = "output/bitrate.csv";
//	static final String OUT_FILE = "deleteme.txt";
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Mp3File.class);


	/**
	 * Create a new Mp3File object
	 * 
	 * @param file
	 *            that contains mp3 file
	 */
	public Mp3File(File file) { // TODO: deal w/ exception
//		System.out.println("file:" + file);
		log.debug("file:{}", file);
		this.file = file;
//		AudioFile af;
//		MP3File af;
//			af = AudioFileIO.read(file);
			
			
			try {
				MP3File mp3File = new MP3File(file);
//				System.out.println("mp3File:" + mp3File);
//				log.info("mp3File:{}", mp3File.toString());
				log.debug("mp3File:{}", mp3File);

				Tag tag = mp3File.getTag();
				if (tag != null){
					this.title = tag.getFirst(FieldKey.TITLE);
					this.artist = tag.getFirst(FieldKey.ARTIST);
					this.albumArtist = tag.getFirst(FieldKey.ALBUM_ARTIST);
					this.album = tag.getFirst(FieldKey.ALBUM);
				}
				this.bitRate = mp3File.getAudioHeader().getBitRate();
			} catch (IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException e) {
//				System.out.println("file:" + file);
//				e.printStackTrace();
				log.error("file: {}", file, e);
			}

			
		
	}

	public Mp3File(String location, String title, String artist,
			String albumArtist, String album) {
		this.file = new File(location);
		this.title = title;
		this.artist = artist;
		this.albumArtist = albumArtist;
		this.album = album;
	}

	@Override
	public String toString() {
		return "Mp3File [path=" + file.getAbsolutePath() + ", title=" + title
				+ ", artist=" + artist + ", albumArtist=" + albumArtist
				+ ", album=" + album + "]";
	}

	/**
	 * Create a string array with mp3 file attributes
	 * 
	 * @return String[] of mp3 file attributes
	 */
	public String[] toArray() {
		String[] sa = new String[6];
		sa[0] = albumArtist;
		sa[1] = artist;
		sa[2] = album;
		sa[3] = title; 
		sa[4] = bitRate;
		sa[5] = file.toString();
		return sa;
	}

	boolean isLocationValid() {
		return file.exists();
	}

	/**
	 * Write mp3 file attributes as a csv file entry
	 * 
	 * @param writer
	 *            CSV file writer
	 */
	public void toCSV(CSVWriter writer) {
		writer.writeNext(toArray());
	}

	/**
	 * Index mp3 file attributes into a Lucene database
	 * 
	 * @param writer
	 * @throws Exception
	 */
	public void index(IndexWriter writer) throws Exception {
		Document doc = new Document();
		doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("artist", artist, Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("albumArtist", albumArtist, Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("album", album, Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}

	/**
	 * Index mp3 files in a directory tree
	 * 
	 * @param path
	 *            to index
	 * @param writer
	 *            Lucene index writes
	 * @throws Exception
	 */
	public static void indexPath(File path, IndexWriter writer)
			throws Exception {

		// TODO: change to not use file filter
		// IOFileFilter ff = FileFilterUtils.fileFileFilter();
		// IOFileFilter javaSuffix = FileFilterUtils.suffixFileFilter(".mp3");
		// IOFileFilter fileFilter = FileFilterUtils.and(ff, javaSuffix);
		// Collection<File> files = FileUtils.listFiles(path, fileFilter,
		// TrueFileFilter.INSTANCE);
		Collection<File> files = FileUtils.listFiles(path,
				new String[] { "mp3" }, true);
		for (File f : files) {
			try {
				Mp3File mp3 = new Mp3File(f);
				mp3.index(writer);
			} catch (Exception e) {
				System.out.println("Excpetion:" + f);
				e.printStackTrace();
			}
		}
	}

	public static void indexPathTest(String[] args) throws Exception {
		String indexPath = "index";
		Directory dir = FSDirectory.open(new File(indexPath));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31,
				analyzer);
		// iwc.setOpenMode(OpenMode.CREATE);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir, iwc);
		Mp3File.indexPath(new File("C:\\Users\\Public\\Music\\Sample Music"),
				writer);
		writer.close();
	}

	public static void singleFileTest(String[] args) throws Exception {
		File file = new File(
				"C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3");
		Mp3File mp3 = new Mp3File(file);
		System.out.println(mp3);
	}

	/**
	 * Scan INPUT_DIR and write tag information to csv file in output directory.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// disable jul logging output
		java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger("");
		Handler[] handlers = globalLogger.getHandlers();
		for (Handler handler : handlers) {
			globalLogger.removeHandler(handler);
		}

		CSVWriter writer = new CSVWriter(new FileWriter(OUT_FILE));
		File path = new File(INPUT_DIR);
		Collection<File> files = FileUtils.listFiles(path,
				new String[] { "mp3" }, true);
//		System.out.println("files.size:" + files.size());
		log.debug("files.size:{}",  files.size());
		for (File f : files) {
			Mp3File mp3 = new Mp3File(f);
			mp3.toCSV(writer);
		}
		writer.close();
	}

}
