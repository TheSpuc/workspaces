package javaBean22August2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private YesNoPanel yesNoPanel;
	private JLabel lblReponds;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblReponds = new JLabel("");
		contentPane.add(lblReponds, BorderLayout.WEST);

		yesNoPanel = new YesNoPanel();
		yesNoPanel.addAnswerListener(new AnswerListener() {
			public void yes(AnswerEvent event) {
				lblReponds.setText(yesNoPanel.getYesText());
			}
			@Override
			public void no(AnswerEvent event) {
				lblReponds.setText(yesNoPanel.getNoText());
			}
		});
		contentPane.add(yesNoPanel, BorderLayout.CENTER);


	}

}
