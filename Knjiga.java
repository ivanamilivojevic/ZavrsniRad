package zavrsni;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Knjiga {

	private ArrayList<String> noveReci;
	private Recnik recnik;
	private HashMap<String, Integer> pojavljivanjaReci;

	public Knjiga() {
		noveReci = new ArrayList<String>();
		pojavljivanjaReci = new HashMap<String, Integer>();
		recnik = new Recnik();
		recnik.popuniRecnik();
	}

	public void ukloniZnake() {
	
		try {
			BufferedReader br = new BufferedReader(new FileReader("knjiga"));
			FileWriter fw = new FileWriter("knjigaReci");

			String linija = br.readLine();

			while (linija != null) {
				linija = linija.toLowerCase();
				for (int i = 0; i < linija.length(); i++) {
					char ch = linija.charAt(i);
					if (!(ch >= 'a' && ch <= 'z')) {
						linija = linija.replace(ch, ' ');
					}
				}
				fw.write(linija + "\n");
				linija = br.readLine();

			}
			fw.flush();

			br.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> pronadjiNove() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("knjigaReci"));
			String linija = br.readLine();
			while (linija != null) {
				String[] reci = linija.split(" ");
				for (int i = 0; i < reci.length; i++) {
					if (!reci[i].isEmpty() && !recnik.nadjiRec(reci[i]) && !noveReci.contains(reci[i])) {
						noveReci.add(reci[i]);
						recnik.dodajNovuRec(reci[i]);
					}
				}
				linija = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return noveReci;
	}

	public HashMap<String, Integer> izbrojPojavljivanja() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("knjigaReci"));
			String linija = br.readLine();
			while (linija != null) {
				String[] reci = linija.split(" ");
				for (int i = 0; i < reci.length; i++) {
					if (!reci[i].isEmpty() && recnik.nadjiRec(reci[i]) == true) {
						if (pojavljivanjaReci.containsKey(reci[i]) == false) {
							
							pojavljivanjaReci.put(reci[i], 1);
						} else {
							
							int brojac = pojavljivanjaReci.get(reci[i]);
							pojavljivanjaReci.put(reci[i], brojac + 1);
						}
					}
				}
				linija = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pojavljivanjaReci;
	}

	public ArrayList<String> pronadjiNajcesceReci(int brojReci) {

		ArrayList<String> reci = new ArrayList<String>();
		for (String rec : pojavljivanjaReci.keySet()) {
			reci.add(rec);
		}

		for (int i = 0; i < reci.size() - 1; i++) {
			for (int j = i + 1; j < reci.size(); j++) {
				String recI = reci.get(i);
				String recJ = reci.get(j);
				int brojI = pojavljivanjaReci.get(recI);
				int brojJ = pojavljivanjaReci.get(recJ);
				if (brojI < brojJ) {
	
					reci.set(i, recJ);
					reci.set(j, recI);
				}
			}
		}

		if (brojReci > reci.size()) {
			brojReci = reci.size();
		}
		ArrayList<String> rezultat = new ArrayList<String>();
		for (int i = 0; i < brojReci; i++) {
			rezultat.add(reci.get(i));
		}
		return rezultat;
	}

	public void upisiSveUFajl(String imeFajla) {
		ArrayList<String> sveReciUKnjizi = new ArrayList<String>();
		sveReciUKnjizi.addAll(noveReci);
		sveReciUKnjizi.addAll(pojavljivanjaReci.keySet());
		Collections.sort(sveReciUKnjizi);
		
		try {
			FileWriter fw = new FileWriter("sveReci");

			for (String rec : sveReciUKnjizi) {
				rec = rec.toLowerCase();
				fw.write(rec + "\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Recnik getRecnik() {
		return recnik;
	}
}
