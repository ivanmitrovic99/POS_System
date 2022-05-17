import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Font;

public class POS {

	JFrame frame;
	private JTextField textFieldID;
	private JTextField textFieldQuantity;
	private JTable tableProducts;
	private JTextField textFieldCena;
	private JTable tableSearch;
	private JTextField textFieldSearch;
	
	
	private Purchase purchase = new Purchase();
	private Artikli artikli = new Artikli();
	private Racuni racuni= new Racuni();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					POS window = new POS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
					
			}
		});
	}

	/**
	 * Create the application.
	 */
	public POS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 255));
		frame.setBounds(100, 100, 722, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnKupovina = new JButton("Kupovina");
		btnKupovina.setBackground(new Color(255, 255, 204));
		btnKupovina.setForeground(new Color(204, 102, 102));
		btnKupovina.setFont(new Font("Lucida Bright", Font.PLAIN, 20));
		btnKupovina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				purchase.setVisible(true);
				frame.dispose();
			 
			}
		});
		btnKupovina.setBounds(90, 116, 525, 79);
		frame.getContentPane().add(btnKupovina);
		
		JButton btnArtikli = new JButton("Artikli");
		btnArtikli.setForeground(new Color(204, 102, 0));
		btnArtikli.setFont(new Font("Lucida Bright", Font.PLAIN, 20));
		btnArtikli.setBackground(new Color(255, 255, 204));
		btnArtikli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				artikli.setVisible(true);
				frame.dispose();
			}
		});
		btnArtikli.setBounds(90, 226, 525, 79);
		frame.getContentPane().add(btnArtikli);
		
		JButton btnRacuni = new JButton("Racuni");
		btnRacuni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				racuni.setVisible(true);
				frame.dispose();
			}
		});
		btnRacuni.setForeground(new Color(204, 102, 0));
		btnRacuni.setFont(new Font("Lucida Bright", Font.PLAIN, 20));
		btnRacuni.setBackground(new Color(255, 255, 204));
		btnRacuni.setBounds(90, 347, 525, 79);
		frame.getContentPane().add(btnRacuni);
		
	}
}
