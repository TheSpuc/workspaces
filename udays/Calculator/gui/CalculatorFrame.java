package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Calculator;

/**
 * Models a Calculator gui.
 * @author Mark Medum Bundgaard
 *
 */
public class CalculatorFrame extends JFrame {
	
	private JTextField txfDisplay;
	private ArrayList<JButton> btnDigit;
	private JButton btnAdd, btnSub, btnMul, btnDiv, btnCalc,
					btnClearMem, btnReadMem, btnSaveMem, btnAddMem, 
					btnReset, btnClearNum, btnRemoveDig, btnChangeSign, 
					btnFactor, btnPower;

	private Calculator calculator = new Calculator();

	private DigitController digitCtrl = new DigitController();
	private OperatorController oprCtrl = new OperatorController();
	private MemoryController memCtrl = new MemoryController();
	private SpecialController speCtrl = new SpecialController();

	public CalculatorFrame() {
		btnDigit = new ArrayList<>();
		int wh = 40; // width and height of buttons
		int sep = 20; // distance between buttons and between buttons and
		// window-border

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(300,400);
		this.setTitle("Calculator");

		//JTextField setup
		txfDisplay = new JTextField();
		txfDisplay.setBounds(10, 10, 280, wh);
		txfDisplay.setHorizontalAlignment(JTextField.RIGHT);
		txfDisplay.setEditable(false);
		this.add(txfDisplay);

		//Placement values
		int x = 70;
		int y = 310;

		//Digit buttons
		for(int i=0; i<10; i++){
			JButton digit = new JButton(""+i);
			digit.setBounds(x, y, wh, wh);
			digit.addActionListener(digitCtrl);
			btnDigit.add(digit);
			this.add(digit);
			if(i%3 == 0){
				x = 70;
				y -= (wh+sep);
			}else {
				x += (wh+sep);
			}
		}

		//Special buttons
		x -= (wh+sep);
		btnFactor = new JButton("x!");
		btnFactor.setBounds(x, y, wh, wh);
		btnFactor.addActionListener(speCtrl);
		this.add(btnFactor);

		x += (wh+sep);
		btnRemoveDig = new JButton("<x");
		btnRemoveDig.setBounds(x, y, wh,wh);
		btnRemoveDig.addActionListener(speCtrl);
		this.add(btnRemoveDig);

		x += (wh+sep);
		btnClearNum = new JButton("CE");
		btnClearNum.setBounds(x, y, wh, wh);
		btnClearNum.addActionListener(speCtrl);
		this.add(btnClearNum);

		x += (wh+sep);
		btnReset = new JButton("C");
		btnReset.setBounds(x, y, wh, wh);
		btnReset.addActionListener(speCtrl);
		this.add(btnReset);

		//Memory buttons
		x -= ((wh+sep)*3);
		y += (sep+wh);
		btnClearMem = new JButton("MC");
		btnClearMem.setBounds(x, y, wh, wh);
		btnClearMem.addActionListener(memCtrl);
		this.add(btnClearMem);	

		y += (sep+wh);
		btnReadMem = new JButton("MR");
		btnReadMem.setBounds(x, y, wh, wh);
		btnReadMem.addActionListener(memCtrl);
		this.add(btnReadMem);

		y += (sep+wh);
		btnSaveMem = new JButton("MS");
		btnSaveMem.setBounds(x, y, wh, wh);
		btnSaveMem.addActionListener(memCtrl);
		this.add(btnSaveMem);

		y += (sep+wh);
		btnAddMem = new JButton("M+");
		btnAddMem.setBounds(x, y, wh, wh);
		btnAddMem.addActionListener(memCtrl);
		this.add(btnAddMem);

		//change sign button
		x += ((wh+sep)*2);
		btnChangeSign = new JButton("+/-");
		btnChangeSign.setBounds(x, y, wh, wh);
		btnChangeSign.addActionListener(speCtrl);
		this.add(btnChangeSign);
		
		//x^y button
		x += (wh+sep);
		btnPower = new JButton("^");
		btnPower.setBounds(x, y, wh, wh);
		btnPower.addActionListener(oprCtrl);
		this.add(btnPower);

		//Operator buttons
		x=250;
		y=310;
		btnCalc = new JButton("=");
		btnCalc.setBounds(x, y, wh, wh);
		btnCalc.addActionListener(oprCtrl);
		this.add(btnCalc);

		y -= (sep+wh);
		btnAdd = new JButton("+");
		btnAdd.setBounds(x, y, wh, wh);
		btnAdd.addActionListener(oprCtrl);
		this.add(btnAdd);

		y -= (sep+wh);
		btnSub = new JButton("-");
		btnSub.setBounds(x, y, wh, wh);
		btnSub.addActionListener(oprCtrl);
		this.add(btnSub);


		y -= (sep+wh);
		btnMul = new JButton("*");
		btnMul.setBounds(x, y, wh, wh);
		btnMul.addActionListener(oprCtrl);
		this.add(btnMul);

		y -= (sep+wh);
		btnDiv = new JButton("/");
		btnDiv.setBounds(x, y, wh, wh);
		btnDiv.addActionListener(oprCtrl);
		this.add(btnDiv);



	}

	// Updates the calculator's display.
	// In OPR state the display must show a number and the operator,
	// as in 23 + .
	// In NUM state the display must show the operator and the number,
	// as in + 107 .
	private void updateDisplay() {
		if(calculator.isNum()){
			txfDisplay.setText(""+ calculator.getOperator() + " " + calculator.getNum2());
		}else if(calculator.isOpr()){
			txfDisplay.setText("" +calculator.getNum1() + " " + calculator.getOperator());
		}else if(calculator.isErr()){
			txfDisplay.setText("Error, please input a new calculation");
		}
	}

	// Controller for digit buttons.
	private class DigitController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			calculator.addDigit(((JButton) e.getSource()).getText().charAt(0));
			updateDisplay();
		}
	}

	// Controller for operator buttons.
	private class OperatorController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			calculator.enterOp(((JButton) e.getSource()).getText().charAt(0));
			updateDisplay();
		}
	}
	// Controller for the memory buttons.
	private class MemoryController implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(btnClearMem)){
				calculator.clearMemory();
			}else if(e.getSource().equals(btnReadMem)){
				calculator.readMemory();
			}else if(e.getSource().equals(btnSaveMem)){
				calculator.saveInMemory();
			}else if(e.getSource().equals(btnAddMem)){
				calculator.addToMemory();
			}
			updateDisplay();
		}
	}

	//Controller for the special buttons.
	private class SpecialController implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(btnRemoveDig)){
				calculator.removeDigit();
			}else if(e.getSource().equals(btnClearNum)){
				calculator.clearNumber();
			}else if(e.getSource().equals(btnReset)){
				calculator.reset();
			}else if(e.getSource().equals(btnChangeSign)){
				calculator.changeSign();
			}else if(e.getSource().equals(btnFactor)){
				calculator.factor();
			}
			updateDisplay();
		}
	}
}

