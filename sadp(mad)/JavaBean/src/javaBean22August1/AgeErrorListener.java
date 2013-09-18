package javaBean22August1;

import java.util.EventListener;

public interface AgeErrorListener extends EventListener {
	
	public void ageOutOfRange(AgeErrorEvent event);
}
