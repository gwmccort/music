package comparetest;

import java.util.HashSet;
import java.util.Set;

import comparetest.model.Track;
import comparetest.model.Track1;
import comparetest.model.Track2;

public class SetTest {

	final static Class t1Class = Track1.class;
	final static Class t2Class = Track2.class;

	public static void main(String[] args) {
		Track1 t1 = new Track1("name", "artist", "album");
		Track2 t2 = new Track2("name", "artist", "album");

//can't user == operator??		System.out.println("t1==t2:" + (t1==t2) );
		System.out.println("t1.equals(t2):" + t1.equals(t2) );
		System.out.println("t1 hash:" + t1.hashCode());
		System.out.println("t2 hash:" + t2.hashCode());


		Set<Track> set1 = new HashSet<Track>();
		set1.add(t1);
		t1 = new Track1("name1", "artist", "album");
		set1.add(t1);
		System.out.println(set1);

		Set<Track> set2 = new HashSet<Track>();
		set2.add(t2);
		t2 = new Track2("name2", "artist", "album");
		set2.add(t2);
		System.out.println(set2);


		Set<Track> difference = new HashSet<Track>(set1);
		difference.removeAll(set2);
		System.out.println(difference);
		System.out.println("----- difference ------");
		for (Track t : difference) {
			System.out.println(t + " class:" + t.getClass().getName());

		}

		//set xor
		Set<Track> tmpSet = new HashSet<Track>(set1);
		set1.removeAll(set2); //s1 all items not in s2
		set2.removeAll(tmpSet); //s2 all items not in s1
		set1.addAll(set2);
		System.out.println("----- xor ------");
		for (Track t : set1) {
			if (t.getClass() == t1Class) System.out.print("Track1: ");
			else System.out.print("Track2: ");
			System.out.println(t + " class:" + t.getClass().getName());
		}



	}

}
