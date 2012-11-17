package alp3.ueb;

import java.util.Collection;

	public interface WoerterBuch<S extends Comparable<S>,W>{

	//wichtige Operationen
	public Eintrag<S,W> finde(S schluessel);
	public void einfuege (Eintrag<S,W> e);
	public void streiche (Eintrag<S,W> e);

	// weitere nützliche Operationen
	public int groesse();
	public boolean istLeer();

	//könnte man auch noch aufnehmen

	public Collection<Eintrag<S,W>> findeAlle(S s);

	}

