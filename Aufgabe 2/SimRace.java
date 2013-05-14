package main;

import java.util.Arrays;

public class SimRace {

	final static int autos = 5;
	final static int runden = 10;
	
	public static void main(String[] args) {
		// Ich habe ein mulmiges Gefühl bei der Sache
		Accident acc = new Accident(Thread.currentThread(), runden * 100);
		acc.start();
		
		// Autis starten yaaaay
		Car[] autis = new Car[autos];
		for (int i = 0; i < autos; i++) {
			autis[i] = new Car(i, runden);
			autis[i].start();
		}
		
		// Kommen sie vor einem Unfall ans Ziel?
		try {
			for (Car auti : autis) {
				auti.join();
			}
		} catch (InterruptedException e) {
			for (Car auti : autis) {
				auti.interrupt();
			}
			return;
		}

		// Alle ankommen! Siegerehrung
		Arrays.sort(autis);
		System.out.println("**** Endstand ****");
		for (int i = 0; i < autos; i++) {
			System.out.println((i + 1) + ". Platz: Wagen " + autis[i].nummer + " Zeit: " + autis[i].gesamtFahrzeit);
		}
	}

}

class Car extends Thread implements Comparable<Car> {
	private int runden;
	public int nummer;
	public int gesamtFahrzeit = 0;

	Car(int nummer, int runden) {
		this.nummer = nummer;
		this.runden = runden;
	}

	public void run() {
		for (int i = 0; i < runden; i++) {
			try {
				long zeit = (long)(Math.random() * 100);
				gesamtFahrzeit += zeit;
				Thread.sleep(zeit);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}

	@Override
	public int compareTo(Car o) {
		return gesamtFahrzeit - o.gesamtFahrzeit;
	}
}

class Accident extends Thread {
	private Thread parent;
	private int crashModifier;
	
	Accident(Thread parent, int crashModifier) {
		this.parent = parent;
		this.crashModifier = crashModifier;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep((long) (Math.random() * crashModifier));
		} catch (InterruptedException e) {
			interrupt();
		}
		parent.interrupt();
	}
}