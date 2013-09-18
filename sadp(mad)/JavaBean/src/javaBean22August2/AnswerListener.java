package javaBean22August2;

import java.util.EventListener;

public interface AnswerListener extends EventListener {
	
	public void yes(AnswerEvent event);
	public void no(AnswerEvent event);
	
}
