package model;

/**
 * Models a Calculator class that uses whole number calculation.
 * @author Mark Medum Bundgaard
 *
 */
public class Calculator {
	//The current state of the calculator.
	private int state;

	//Operator buttons set state to OPR.
	private final int OPR = 0;

	//Digit buttons set state to NUM.
	private final int NUM = 1;

	// SOMETHING
	private final int ERR = -1;

	//Last calculated number.
	private int num1;

	//Last entered operator.
	//Operators are: + - * / =.
	private char op1;

	//Number currently being entered.
	//if state == OPR, num2 is undefined.
	private int num2;

	//for holding the number which should be saved.
	private int memory;

	/**
	 * Constructor for the class Calculator.
	 * Demand: all calculations must be inside the range of Integer.MAX_VALUE;
	 */
	public Calculator() {
		num1 = 0;
		num2 = 0;
		op1 = '=';
		state = OPR;
	}

	/**
	 * Check if the calculator is in NUM state.
	 * @return boolean
	 */
	public boolean isNum(){
		return state == NUM;
	}

	/**
	 * Check if the calculator is in OPR state.
	 * @return boolean
	 */
	public boolean isOpr(){
		return state == OPR;
	}

	/**
	 * Check if the calculator is in ERR state.
	 * @return boolean
	 */
	public boolean isErr(){
		return state == ERR;
	}

	/**
	 * Return the result number.
	 * @return num1
	 */
	public int getNum1(){
		return num1;
	}

	/**
	 * Return the calculation number.
	 * @return num2
	 */
	public int getNum2(){
		return num2;
	}

	/**
	 * Return the operator that is saved.
	 * @return op1
	 */
	public char getOperator(){
		return op1;
	}

	/**
	 * Method for entering a digit in the calculator.
	 * @param digit
	 */
	public void addDigit(char digit) {
		state = NUM;
		num2 = num2*10 + digit - '0';
	}

	/**
	 * Method calculating the result or changing the operator being used.
	 * @param op2
	 */
	public void enterOp(char op2) {
		if(state == NUM){
			state = OPR;
			if(op1 == '='){
				num1 = num2;
			}else if(op1 == '+'){
				num1 = num1+num2;
			}else if(op1 == '-'){
				num1 = num1-num2;
			}else if(op1 == '*'){
				num1 = num1*num2;
			}else if(op1 == '/'){
				if(num2 != 0){
					num1 = num1/num2;
				}else {
					state = ERR;
					num1 = 0;
					op1 = '=';
				}
			}else if(op1 == '^'){
				num1 = (int) Math.pow(num1, num2);
			}
			num2 = 0;
		}else if(state == ERR){
			state = OPR;
		}
		op1 = op2;
	}

	/**
	 * Method for clearing the number saved in memory.
	 */
	public void clearMemory(){
		memory = 0;
	}

	/**
	 * Method for reading what is stored in the memory.
	 */
	public void readMemory(){
		if(state == OPR){
			state = NUM;
			num2 = memory;
		}else {
			num2 = memory;
		}
	}

	/**
	 * Method for saving a specific number in memory.
	 */
	public void saveInMemory(){
		if(state == NUM){
			memory = num2;
		}else if(state == OPR){
			memory = num1;
		}
	}

	/**
	 * Method for adding a number to what
	 * is already stored in memory.
	 */
	public void addToMemory(){
		if(state == NUM){
			memory += num2;
		}else{
			memory += num1;
		}
	}

	/**
	 * Method for resetting the calculator.
	 */
	public void reset(){
		state = OPR;
		num1 = 0;
		op1 = '=';
		num2 = 0;
	}

	/**
	 * Method for deleting the whole input of the user.
	 */
	public void clearNumber(){
		if(state == NUM){
			num2 = 0;
		}
	}

	/**
	 * Method for removing the last digit of the user input.
	 */
	public void removeDigit(){
		num2 /= 10;
	}

	/**
	 * Method for changing the input to be positive or negative.
	 */
	public void changeSign(){
		if(state == NUM){
			num2 *= -1;
		}else if(state == OPR){
			num1 *= -1;
		}
	}

	/**
	 * Method for calculating factorization.
	 */
	public void factor(){
		if(state != ERR){
			if(state == NUM){
				num1 = num2;
			}else{
				num2 = num1;	
			}
			if(num2>=0 || num2<=12){
				if(num2 == 0) {
					num1 = 1;
				}else{
					while(num2>=3){
						num2--;
						num1 *= num2;
					}
					state = OPR;
					num2 = 0;
					op1 = '=';
				}
			}else{
				state = ERR;
			}
		}
	}
}


