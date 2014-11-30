package gwm.nio;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class PathMatcherExample {
	public static void main(String[] args) {
		FileSystem fileSystem = FileSystems.getDefault();
//		PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:D:/**/*.java");
		PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:*.mp3");
		Path path = Paths.get("c:/users/glen/downloads");
		System.out.println(pathMatcher.matches(path));
	}
}