package alp3.ueb;

import java.lang.Comparable;

public interface Eintrag<S extends Comparable<S>,W>{
	public S getSchluessel();
	public W getWert();
}
