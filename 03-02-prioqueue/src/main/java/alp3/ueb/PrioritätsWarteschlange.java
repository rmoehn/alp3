package alp3.ueb;

import java.lang.Comparable;

public interface PrioritätsWarteschlange<S extends Comparable<S>,W>{

	// wichtige Operationen
	public void einfuege(Eintrag<S,W> eintrag);
	public Eintrag<S,W> streicheMin() throws LeereWarteschlangeException;

	// weitere nützliche Operationen
	public int groesse();
	public boolean istLeer();
	public Eintrag<S,W> min() throws LeereWarteschlangeException;
}
