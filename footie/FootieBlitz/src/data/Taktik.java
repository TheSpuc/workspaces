package data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Taktik {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String[] parametre = {"aggression", "closingdown", "mentality", "longshots", 
				"throughballs", "dribble", "runs", "crossball", "tightmarking"};
				
		JFrame frame = new JFrame("Taktik");
		frame.setSize(400, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		
		JLabel lblP = new JLabel("Parameter");
		lblP.setLocation(10, 10);
		lblP.setSize(200, 25);
		frame.add(lblP);
		
		JLabel lblV = new JLabel("Vaerdi");
		lblV.setLocation(225, 10);
		lblV.setSize(100, 25);
		frame.add(lblV);
		
		JLabel lblT = new JLabel("Troejenummer");
		lblT.setLocation(290, 10);
		lblT.setSize(100, 25);
		frame.add(lblT);
		
		
		final JComboBox cmbParametre = new JComboBox(parametre);
		cmbParametre.setLocation(10, 35);
		cmbParametre.setSize(200, 25);
		frame.add(cmbParametre);
		
		final JTextField txfVaerdi = new JTextField();
		txfVaerdi.setLocation(220, 35);
		txfVaerdi.setSize(50, 25);
		txfVaerdi.setToolTipText("Vaerdi");
		frame.add(txfVaerdi);
		
		final JTextField txfNummer = new JTextField();
		txfNummer.setLocation(330, 35);
		txfNummer.setSize(50, 25);
		txfNummer.setToolTipText("Troejenummer");
		frame.add(txfNummer);
		
		JButton btnGo = new JButton("Go");
		btnGo.setVisible(true);
		btnGo.setSize(100, 25);
		btnGo.setLocation(290, 150);
		btnGo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {

				String sql = "";
				
				if (txfNummer.getText().equals("*")){
					sql = "UPDATE playertactics SET " 
						+ cmbParametre.getSelectedItem().toString() + "=" + Integer.parseInt(txfVaerdi.getText());
				}
				else{	
					sql = "UPDATE playertactics SET " 
						+ cmbParametre.getSelectedItem().toString() + "=" + Integer.parseInt(txfVaerdi.getText()) + 
						" FROM person WHERE person.personId=playertactics.personId AND shirtnumber=" 
						+ Integer.parseInt(txfNummer.getText());
				}
			
				
				System.out.println(sql);
				try {
					DAOCake.openConnection();
					DAOCake.executeSimpleStatement(sql);
					DAOCake.closeConnection();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				}
			}
			
		});
		
		frame.add(btnGo);
		
		frame.repaint();
	}

}
