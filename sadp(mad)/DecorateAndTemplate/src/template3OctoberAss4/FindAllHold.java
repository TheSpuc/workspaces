package template3OctoberAss4;

public class FindAllHold extends FletteTemplate {
	
	private Sekvens a;
	private Sekvens b;
	
	public FindAllHold(Sekvens a, Sekvens b){
		this.a = a;
		this.b = b;
	}
	
	public Sekvens findAll(){
		return flette(a, b, new ListSekvens());
	}
	
	@Override
	protected void flyt1(Sekvens s) {
		g.add(s.head());
		s.tail();
	}
	
	@Override
	protected void flyt2(Sekvens s) {
		g.add(s.head());
		s.tail();
	}

	@Override
	protected void flyt3(Sekvens s1, Sekvens s2) {
		g.add(s1.head());
		g.add(s2.head());
		s1.tail();
		s2.tail();
	}

	@Override
	protected void flytHale1(Sekvens s) {
		g.add(s.head());
		s.tail();
	}

	@Override
	protected void flytHale2(Sekvens s) {
		g.add(s.head());
		s.tail();
	}
}
