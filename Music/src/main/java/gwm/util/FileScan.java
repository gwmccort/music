package gwm.util;

import static gwm.util.FilePredicates.byFileExtensions;
import static java.util.Arrays.asList;

import java.io.File;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;

public class FileScan {

	public static void main(String[] args) {
		File dir = new File(".");
		FluentIterable<File> filesItr = Files.fileTreeTraverser().breadthFirstTraversal(dir).filter(byFileExtensions(asList("class", "java")));
		
		for (File file: filesItr) {
			System.out.println(file);
		}
	}
}

class FilePredicates {
	public static Predicate<File> byFileExtension(final String extenstion) {
		return new Predicate<File>() {
			public boolean apply(File file) {
				return extenstion.equals(Files.getFileExtension(file.getPath()));
				
			}
		};
	}
	
	public static Predicate<File> byFileExtensions(final List<String>extenstions){
		return new Predicate<File>() {
			public boolean apply(File file) {
				for (String ext: extenstions) {
					if (ext.equals(Files.getFileExtension(file.getPath()))) {
						return true;
					}
				}
				return false;
			}
		};
	}
}
