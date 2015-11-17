package comparetest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparetest.model.Track;
import comparetest.model.Track1;
import comparetest.model.Track2;


public class CompareTest {

	public static void main(String[] args) {
		Track1 t1 = new Track1("name", "artist", "album33");
		Track2 t2 = new Track2("name", "artist", "album2");

		System.out.println(t1.equals(t2));
		System.out.println(t1.compareTo(t2));

		List<Track> tracks = new ArrayList<Track>();
		tracks.add(t1);
		tracks.add(t2);

		System.out.println("--- before sort:");
		for (Track t : tracks) {
			System.out.println("\t" + t);
		}

		Collections.sort(tracks);

		System.out.println("\n\n--- after sort:");
		for (Track t : tracks) {
			System.out.println("\t" + t);
		}

		if (t1.compareTo(t2) == 0){
			System.out.println("compare");
		}
		else {
			System.out.println("don't compare");
		}




	}

}
