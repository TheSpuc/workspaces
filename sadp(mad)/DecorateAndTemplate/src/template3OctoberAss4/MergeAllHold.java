package template3OctoberAss4;

public class MergeAllHold extends FletteTemplate {
	
	private Sekvens a;
	private Sekvens b;
	
	public MergeAllHold(Sekvens a, Sekvens b){
		this.a = a;
		this.b = b;
	}
	
	public Sekvens MergeAll(){
		return flette(a, b, new ListSekvens());
	}
	
	@Override
	protected void flyt1(Sekvens s) {
		s.tail();
	}
	
	@Override
	protected void flyt2(Sekvens s) {
		s.tail();
	}

	@Override
	protected void flyt3(Sekvens s1, Sekvens s2) {
		g.add(s1.head());
		s1.tail();
		s2.tail();
	}

	@Override
	protected void flytHale1(Sekvens s) {
		s.tail();
	}

	@Override
	protected void flytHale2(Sekvens s) {
		s.tail();
	}
}
