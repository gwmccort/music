/**
 * Find empty directories in the pogo plug music directory
 */

// check if dir is empty
def isDirEmpty = { dirName ->
	def dir = new File(dirName)
	dir.exists() && dir.directory && (dir.list() as List).empty
}

dir = new File(/P:\FANTOM HDS721010CLA332\Media\Music/)
emptyDirs = []
dir.eachDirRecurse { 
	if ((it.list() as List).empty) {
		emptyDirs << it.path
	}
}

emptyDirs.each {
	println it
}