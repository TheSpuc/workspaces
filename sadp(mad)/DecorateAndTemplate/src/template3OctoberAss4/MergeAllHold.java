package template3OctoberAss4;

public class MergeAllHold<E extends Comparable<E>> extends FletteTemplate<E> {
	
	private Sekvens<E> a;
	private Sekvens<E> b;
	
	public MergeAllHold(Sekvens<E> a, Sekvens<E> b){
		this.a = a;
		this.b = b;
	}
	
	public Sekvens<E> MergeAll(){
		return flette(a, b, new ListSekvens<E>());
	}
	
	@Override
	protected void flyt1(Sekvens<E> s) {
		s.tail();
	}
	
	@Override
	protected void flyt2(Sekvens<E> s) {
		s.tail();
	}

	@Override
	protected void flyt3(Sekvens<E> s1, Sekvens<E> s2) {
		g.add(s1.head());
		s1.tail();
		s2.tail();
	}

	@Override
	protected void flytHale1(Sekvens<E> s) {
		s.tail();
	}

	@Override
	protected void flytHale2(Sekvens<E> s) {
		s.tail();
	}
}
