import au.com.bytecode.opencsv.CSVReader


class ReadCSVFile {

	static main(args) {
		def reader = new CSVReader(new FileReader("yourfile_sorted.csv"))
		def line
		def lastTrack = new Track()
		while ((line = reader.readNext()) != null){
			//println line
//			def artist = line[1]
//			def song = line[2]
//			def album = line[3]
//			println "$artist:$song:$album"
			def t = new Track(artist:line[1], song:line[2], album:line[3])
			//println t
			if (t == lastTrack) println "duplicate tracks: $t"

			lastTrack = t
		}

	}

}

class Track implements Comparable {
	def artist
	def song
	def album

	String toString(){
		return "$artist:$song:$album"
	}

	int compareTo(that){
		if ((this.artist <=> that.artist) != 0) return this.artist <=> that.artist
		else if ((this.song <=> that.song) != 0) return this.song <=>that.song
		else return this.album <=> that.album
	}
}