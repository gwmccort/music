package gwm;

import static gwm.LuceneConstants.LUCENE_VERSION;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/** 
 * example of multi term parser
 * 
 * @author Glen
 *
 */
public class RamIndexTest {
	// static final Version LUCENE_VERSION = Version.LUCENE_4_9;
	static ArrayList<Track> tracks = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		// index tracks
		StandardAnalyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);
		Directory index = new RAMDirectory();
		IndexWriterConfig iwConf = new IndexWriterConfig(LUCENE_VERSION,
				analyzer);
		IndexWriter writer = new IndexWriter(index, iwConf);
		for (Track t : tracks) {
			t.index(writer);
		}
		writer.close();

		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] hits = Track.search(index, "trucking band grateful album* track3");
		Track.displayHitsScore(searcher, hits);

//		hits = Track.search(index, new String[] { "band" }, "trucking band grateful album* track3");
		hits = Track.search(index, new String[] { "band", "title" }, "band trucking track3");
		Track.displayHitsScore(searcher, hits);

	}

	public static void main2(String[] args) throws Exception {
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
		Directory index = new RAMDirectory();
		IndexWriterConfig iwConf = new IndexWriterConfig(Version.LUCENE_4_9,
				analyzer);
		IndexWriter writer = new IndexWriter(index, iwConf);

		// for (Track t : tracks) {
		// addTrack(w, t);
		// }
		for (Track t : tracks) {
			t.index(writer);
		}
		writer.close();

		// 2. query
		String querystr = "band";

		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		Query q = new QueryParser(Version.LUCENE_4_9, "band", analyzer)
				.parse(querystr);

		// 3. search
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		displayHits(searcher, hits);

		hits = booleanSearch(index);
		displayHits(searcher, hits);

		hits = multiFieldSearch(index);
		displayHits(searcher, hits);
		displayHitsScore(searcher, hits);

		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close();
	}

	private static void displayHits(IndexSearcher searcher, ScoreDoc[] hits)
			throws IOException {
		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("band") + "\t"
					+ d.get("title") + "\t" + d.get("album"));
		}
	}

	private static void displayHitsScore(IndexSearcher searcher,
			ScoreDoc[] scoreDocs) throws IOException {
		// 4. display results
		// System.out.println("Found " + scoreDocs.length + " hits.");
		// for (int i = 0; i < scoreDocs.length; ++i) {
		// int docId = scoreDocs[i].doc;
		// Document d = searcher.doc(docId);
		// System.out.println((i + 1) + ". " + d.get("band") + "\t"
		// + d.get("title") + "\t" + d.get("album"));
		// }

		// TopDocs hits = searcher.search(q,maxHits);
		// ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank,score,docId)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;
			Document d = searcher.doc(docId);
			System.out.printf("%3d %4.2f %d %s\t%s\t%s\n", n, score, docId,
					d.get("band"), d.get("title"), d.get("album"));
		}
	}

	private static ScoreDoc[] booleanSearch(Directory index) throws Exception {
		// IndexReader reader = IndexReader.Open("<lucene dir>");
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);

		BooleanQuery booleanQuery = new BooleanQuery();
		Query query1 = new TermQuery(new Term("band", "band"));
		Query query2 = new TermQuery(new Term("title", "trucking"));
		booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		// Use BooleanClause.Occur.MUST instead of BooleanClause.Occur.SHOULD
		// for AND queries
		// Hits hits = searcher.Search(booleanQuery);

		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);
		searcher.search(booleanQuery, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	private static ScoreDoc[] multiFieldSearch(Directory index)
			throws Exception {
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);

		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				LUCENE_VERSION, new String[] { "band", "title", "album" },
				analyzer);

		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);

		// searcher.search(queryParser.parse("band album~"), collector);
		// searcher.search(queryParser.parse("album*"), collector);
		searcher.search(queryParser.parse("album~"), collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	static {
		Track t = new Track("Trucking", "Grateful Dead", "album1");
		tracks.add(t);
		t = new Track("track2", "band2", "album2");
		tracks.add(t);
		t = new Track("Playing In The Band", "Grateful Dead", "album2");
		tracks.add(t);
		t = new Track("track3", "band3", "album3");
		tracks.add(t);
		t = new Track("track3", "The Band", "album3");
		tracks.add(t);
	}
}

class Track {
	String title;
	String band;
	String album;

	public void index(IndexWriter writer) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("band", band, Field.Store.YES));
		doc.add(new TextField("album", album, Field.Store.YES));

		// StringField doesn't tokenize text
		// doc.add(new StringField("title", track.getTitle(), Field.Store.YES));
		// doc.add(new StringField("band", track.getBand(), Field.Store.YES));
		// doc.add(new StringField("album", track.getAlbum(), Field.Store.YES));

		// use a string field for isbn because we don't want it tokenized
		// doc.add(new StringField("song", song, Field.Store.YES));
		writer.addDocument(doc);
	}

	static ScoreDoc[] search(Directory index, String searchTerm)
			throws Exception {
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);

		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				LUCENE_VERSION, new String[] { "band", "title", "album" },
				analyzer);

		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);

		// searcher.search(queryParser.parse("band album~"), collector);
		// searcher.search(queryParser.parse("album*"), collector);
		searcher.search(queryParser.parse(searchTerm), collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	static ScoreDoc[] search(Directory index, String[] searchFields, String searchTerm)
			throws Exception {
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);

		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				LUCENE_VERSION, searchFields,
				analyzer);

		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);

		// searcher.search(queryParser.parse("band album~"), collector);
		// searcher.search(queryParser.parse("album*"), collector);
		searcher.search(queryParser.parse(searchTerm), collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	static void displayHits(IndexSearcher searcher, ScoreDoc[] hits)
			throws IOException {
		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("band") + "\t"
					+ d.get("title") + "\t" + d.get("album"));
		}
	}

	static void displayHitsScore(IndexSearcher searcher, ScoreDoc[] scoreDocs)
			throws IOException {
		// 4. display results
		// System.out.println("Found " + scoreDocs.length + " hits.");
		// for (int i = 0; i < scoreDocs.length; ++i) {
		// int docId = scoreDocs[i].doc;
		// Document d = searcher.doc(docId);
		// System.out.println((i + 1) + ". " + d.get("band") + "\t"
		// + d.get("title") + "\t" + d.get("album"));
		// }

		// TopDocs hits = searcher.search(q,maxHits);
		// ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank,score,docId)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;
			Document d = searcher.doc(docId);
			System.out.printf("%3d %4.2f %d %s\t%s\t%s\n", n, score, docId,
					d.get("band"), d.get("title"), d.get("album"));
		}
	}

	public Track(String title, String band, String album) {
		super();
		this.title = title;
		this.band = band;
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
}

class LuceneConstants {
	static final Version LUCENE_VERSION = Version.LUCENE_4_9;
}
