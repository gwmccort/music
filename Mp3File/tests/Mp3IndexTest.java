import gwm.Mp3File;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Mp3IndexTest {

	// static final String path = "c:\\users\\glen\\music";
	static final String path = "c:\\users\\glen\\downloads";
	static final String indexPath = "index";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// disable jul logging output
		Logger globalLogger = Logger.getLogger("");
		Handler[] handlers = globalLogger.getHandlers();
		for (Handler handler : handlers) {
			globalLogger.removeHandler(handler);
		}
		
		IndexWriter writer = null;
		try {
			Directory dir = FSDirectory.open(new File(indexPath));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_33,
					analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			// iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(dir, iwc);
			Mp3File.indexPath(new File(path), writer);
		} finally {
			writer.close();
		}
	}

}
