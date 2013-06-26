package osbsp;
/**
 * Sammlung und Auswertung statistischer Daten eines Simulationslaufs
 */
public class Statistics {

	private int pageFaults;			// Anzahl Seitenfehler
	private int writeAccesses;		// Anzahl Schreibzugriffe
	private int readAccesses;		// Anzahl Lesezugriffe

	//	Seitenfehlerrrate = Anzahl Seitenfehler / Anzahl Zugriffe
	private float pageFaultRate;	
				
	/**
	 * Konstruktor
	 */
	public Statistics() {
		resetCounter();
	}

	/**
	 * Alle Statistik-Zähler zurücksetzen
	 */
	public void resetCounter() {
		pageFaults = 0;
		writeAccesses = 0;
		readAccesses = 0;
		pageFaultRate = 0;		
	}
	
	/**
	 * @return Seitenfehlerrrate = Anzahl Seitenfehler / Anzahl Zugriffe
	 */
	public float getPageFaultRate() {
		pageFaultRate = (float) pageFaults / (writeAccesses + readAccesses);
		return pageFaultRate;
	}

	/**
	 * @return Anzahl Seitenfehler
	 */
	public int getPageFaults() {
		return pageFaults;
	}

	/**
	 * @return Anzahl Zugriffe insgesamt
	 */
	public int getTotalAccesses() {
		return readAccesses + writeAccesses;
	}
	
	/**
	 * @return Anzahl Lesezugriffe
	 */
	public int getReadAccesses() {
		return readAccesses;
	}

	/**
	 * @return Anzahl Schreibzugriffe 
	 */
	public int getWriteAccesses() {
		return writeAccesses;
	}

	/**
	 * Seitenfehler zählen
	 */
	public void incrementPageFaults() {
		pageFaults++;
	}

	/**
	 * Lesezugriff zählen
	 */
	public void incrementReadAccesses() {
		readAccesses++;
	}

	/**
	 * Schreibzugriff zählen
	 */
	public void incrementWriteAccesses() {
		writeAccesses++;
	}

	/**
	 *  Statistik-Bericht auf der Console ausgeben
	 *
	 */
	public void showReport() {
		System.out.println("\n**************** Statistik *************************");
		System.out.println("*** Anzahl Seitenfehler: "+getPageFaults());
		System.out.println("*** Anzahl Zugriffe:     "+getTotalAccesses());
		System.out.println("*** Seitenfehlerrate:    "+getPageFaultRate());
		System.out.println("****************************************************");
	}
}
