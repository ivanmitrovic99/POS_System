import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class NoviArtikal extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIme;
	private JTextField textFieldKolicina;
	private JTextField textFieldCena;
	private JTextField textFieldJedinicaMere;
	private JTextField textFieldPOznaka;
	private JTextField textFieldPStopa;
	private JLabel lblId;
	private JTextField textFieldID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoviArtikal frame = new NoviArtikal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NoviArtikal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ime");
		lblNewLabel.setBounds(55, 109, 64, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblKolicina = new JLabel("Kolicina");
		lblKolicina.setBounds(55, 143, 64, 23);
		contentPane.add(lblKolicina);
		
		JLabel lblCena = new JLabel("Cena");
		lblCena.setBounds(55, 177, 64, 23);
		contentPane.add(lblCena);
		
		JLabel lblJedinicaMere = new JLabel("Jedinica mere");
		lblJedinicaMere.setBounds(55, 217, 94, 23);
		contentPane.add(lblJedinicaMere);
		
		JLabel lblPoreskaOznaka = new JLabel("Poreska oznaka");
		lblPoreskaOznaka.setBounds(55, 251, 94, 23);
		contentPane.add(lblPoreskaOznaka);
		
		JLabel lblStopa = new JLabel("Stopa");
		lblStopa.setBounds(55, 293, 64, 23);
		contentPane.add(lblStopa);
		
		textFieldIme = new JTextField();
		textFieldIme.setBounds(151, 110, 140, 20);
		contentPane.add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldKolicina = new JTextField();
		textFieldKolicina.setColumns(10);
		textFieldKolicina.setBounds(151, 144, 140, 20);
		contentPane.add(textFieldKolicina);
		
		textFieldCena = new JTextField();
		textFieldCena.setColumns(10);
		textFieldCena.setBounds(151, 178, 140, 20);
		contentPane.add(textFieldCena);
		
		textFieldJedinicaMere = new JTextField();
		textFieldJedinicaMere.setColumns(10);
		textFieldJedinicaMere.setBounds(151, 218, 140, 20);
		contentPane.add(textFieldJedinicaMere);
		
		textFieldPOznaka = new JTextField();
		textFieldPOznaka.setColumns(10);
		textFieldPOznaka.setBounds(151, 252, 140, 20);
		contentPane.add(textFieldPOznaka);
		
		textFieldPStopa = new JTextField();
		textFieldPStopa.setColumns(10);
		textFieldPStopa.setBounds(151, 294, 140, 20);
		contentPane.add(textFieldPStopa);
		
		JButton btnAddArticle = new JButton("DODAJ");
		btnAddArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
				int id=Integer.parseInt(textFieldID.getText());
				String ime=textFieldIme.getText();
				float cena=Float.parseFloat(textFieldCena.getText());
				String POznaka=textFieldPOznaka.getText();
				String jm= textFieldJedinicaMere.getText();
				float kolicina=Float.parseFloat(textFieldKolicina.getText());
				float PStopa=Float.parseFloat(textFieldPStopa.getText());
				
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("INSERT INTO Stavke(ID,Ime, Kolicina, Cena, Jedinica_mere, Porez_oznaka, Porez_stopa) VALUES("+id+",'"+ime+"',"+kolicina+","+cena+",'"+jm+"','"+POznaka+"',"+PStopa+");");
				
				
				int row = st1.executeUpdate();
	             
	            if (row > 0) {
	            	JOptionPane.showMessageDialog(null, "Artikal uspesno dodat");
	            }
				}
				/*yourModel.addRow(new Object[]{str1, str2, str3});*/
				
				
				
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Doslo je do greske");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			
				
			dispose();
				 
				}
			
			
		});
		btnAddArticle.setBounds(151, 386, 121, 42);
		contentPane.add(btnAddArticle);
		
		lblId = new JLabel("ID");
		lblId.setBounds(55, 75, 64, 23);
		contentPane.add(lblId);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(151, 75, 140, 20);
		contentPane.add(textFieldID);
	}

}
