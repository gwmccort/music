package gwm.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkFileTreeExample {

	private static final Logger log = LoggerFactory
			.getLogger(WalkFileTreeExample.class);

	public static void main(String[] args) throws Exception {
		// disable jul logging output
		java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger("");
		Handler[] handlers = globalLogger.getHandlers();
		for (Handler handler : handlers) {
			globalLogger.removeHandler(handler);
		}

		log.error("in main");

		List<File> files = new ArrayList<>();
		Path path = Paths.get("c:/Users/glen/downloads");

		// disable jul logging output
		// java.util.logging.Logger globalLogger = java.util.logging.Logger
		// .getLogger("");
		// Handler[] handlers = globalLogger.getHandlers();
		// for (Handler handler : handlers) {
		// globalLogger.removeHandler(handler);
		// }

		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

			FileSystem fileSystem = FileSystems.getDefault();
			PathMatcher pathMatcher = fileSystem
					.getPathMatcher("glob:**/*.{mp3,flac}");

			// PathMatcher pathMatcher = fileSystem
			// .getPathMatcher("glob:**/*.mp3");

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (pathMatcher.matches(file)) {
					// System.out.println(file);
					files.add(file.toFile());
				}
				return FileVisitResult.CONTINUE;
			}
		};

		try {
			Files.walkFileTree(path, visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (File file : files) {
			System.out.println("file:" + file.getName());
//			MP3File mp3File = new MP3File(file);
//			Tag tag = mp3File.getTag();
			AudioFile audioFile = AudioFileIO.read(file);
			Tag tag = audioFile.getTag();
			if (tag != null) {
				System.out.println(tag.getFirst(FieldKey.TITLE));
				System.out.println(tag.getFirst(FieldKey.ARTIST));
				System.out.println(tag.getFirst(FieldKey.ALBUM_ARTIST));
				System.out.println(tag.getFirst(FieldKey.ALBUM));
			}
		}
	}
}