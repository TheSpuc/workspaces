package composite24OctoberAss2;

public interface FigureComponent {
	
	
	public void setName(String name);
	
	public String getName();
	
	public void addFigureComponent(FigureComponent component);
	
	public void removeFigureComponent(FigureComponent component);
	
	public FigureComponent getChild(int i);
	
	public int getAround();
	
	public void draw();

}
