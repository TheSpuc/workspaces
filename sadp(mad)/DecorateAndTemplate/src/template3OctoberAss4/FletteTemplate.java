package template3OctoberAss4;

public abstract class FletteTemplate {
	protected Sekvens g;

	// Template method med fletteskabelonen
	// Pre: s1 og s2 er sorteret i ikke aftagende orden
	// g er en tom sekvens
	// Post: g sorteret sekvens indeholdende resultatet af
	// fletningen af s1 og s2
	public final Sekvens flette(Sekvens s1, Sekvens s2, Sekvens g) {
		this.g = g;
		while (!s1.isEmpty() && !s2.isEmpty()) {
			if ((s1.head()).compareTo(s2.head()) < 0) {
				flyt1(s1);
			} else if (s1.head().compareTo(s2.head()) > 0) {
				flyt2(s2);
			} else {
				flyt3(s1, s2);
			}
		}
		if (!s1.isEmpty()) {
			flytHale1(s1);
		} else if (!s2.isEmpty()) {
			flytHale2(s2);
		}
		return g;
	}

	// Primitive metoder der kaldes fra Template Method og
	// overskrives i subklasser
	protected void flyt1(Sekvens s) {
	}

	protected void flyt2(Sekvens s) {
	}

	protected void flyt3(Sekvens s1, Sekvens s2) {
	}

	protected void flytHale1(Sekvens s) {
	}

	protected void flytHale2(Sekvens s) {
	}

}
