package gwm.lastfm;

import java.util.Collection;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;

/**
 * @author Janni Kovacs
 */
public class ArtistTest {

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "rcproxy");
		System.setProperty("http.proxyPort", "80");

		String artist = "Phish";
		//artist = "Depeche Mode";
		artist = "Railroad Earth";

		String key = "b25b959554ed76058ac220b7b2e0a026"; //this is the key used in the last.fm API examples online.
		Collection<Track> topTracks = Artist.getTopTracks(artist, key);
		System.out.println("Top Tracks for " + artist + ":");
		int c = 0;
		for (Track track : topTracks) {

			System.out.printf("%d:%s (%d plays)%n", ++c, track.getName(), track.getPlaycount());
		}

		System.out.println("Top albums:");
		Collection<Album> topAlbums = Artist.getTopAlbums(artist, key);
		for(Album album : topAlbums){
			System.out.println(album.getName());
		}


//		Artist a = Artist.getInfo(artist, key);
//		System.out.println(a.getName());
//		Artist.getTopTracks(a.getName(), key);
	}
}
