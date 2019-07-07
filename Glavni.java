package zavrsni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Glavni {
	
	public static void main(String[] args) {
		Knjiga k = new Knjiga();
		k.ukloniZnake();
		k.pronadjiNove();
		k.izbrojPojavljivanja();
		ArrayList<String> najcesceReci = k.pronadjiNajcesceReci(20);
		for (String rec : najcesceReci) {
			System.out.println(rec);
		}
		k.upisiSveUFajl("sveReci");
	}

}
