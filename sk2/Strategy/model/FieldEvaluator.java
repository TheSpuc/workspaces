package model;

public class FieldEvaluator {
	private Evaluator evaluator;
	
	public FieldEvaluator(Evaluator evaluator){
		this.evaluator = evaluator;
	}
	
	public boolean evaluate(String s){
		return evaluator.isValid(s);
	}

}
