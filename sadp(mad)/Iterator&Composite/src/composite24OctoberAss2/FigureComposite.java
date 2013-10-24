package composite24OctoberAss2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FigureComposite implements FigureComponent {

	private String name;
	private int around;
	private List<FigureComponent> composites;
	
	public FigureComposite(String name, int around){
		this.name = name;
		this.around = around;
		composites = new ArrayList<>();
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addFigureComponent(FigureComponent component) {
		composites.add(component);
	}

	@Override
	public void removeFigureComponent(FigureComponent component) {
		composites.remove(component);
	}

	@Override
	public FigureComponent getChild(int i) {
		return composites.get(i);
	}

	@Override
	public int getAround() {
		int result = around;
		
		Iterator<FigureComponent> it = composites.iterator();
		while(it.hasNext()){
			FigureComponent f = it.next();
			result += f.getAround(); 
		}
		
		return result;
	}

	@Override
	public void draw() {
		System.out.println(name);
		
		Iterator<FigureComponent> it = composites.iterator();
		while(it.hasNext()){
			FigureComponent f = it.next();
			f.draw();
		}
	}

	@Override
	public String toString(){
		return name + " A: " + around;
	}

}
