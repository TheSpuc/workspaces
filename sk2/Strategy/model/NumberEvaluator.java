package model;

public class NumberEvaluator implements Evaluator {

	@Override
	public boolean isValid(String s) {
		try{
			Double.parseDouble(s);
		}catch (NumberFormatException e){
			return false;
		}
		return true;
	}

}
