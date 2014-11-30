package gwm;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MyIndexer {
	static final File index = new File("myIndex");
	static final File docDir = new File("src");
	static boolean create = false;

	public static void main(String[] args) throws Exception {
		Directory dir = FSDirectory.open(index);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_9,
				analyzer);

		if (create) {
			// Create a new index in the directory, removing any
			// previously indexed documents:
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			// Add new documents to an existing index:
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		IndexWriter writer = new IndexWriter(dir, iwc);

		writer.close();
	}
}
