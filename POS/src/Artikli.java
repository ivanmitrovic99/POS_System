import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Artikli extends JFrame {

	private JPanel contentPane;
	private JTable tableProducts;
	private JTextField textFieldSearch;
	private NoviArtikal artikal = new NoviArtikal();
	private JTextField textFieldOldID;
	private JTextField textFieldNewID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Artikli frame = new Artikli();
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
	public Artikli() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		DefaultTableModel model = new DefaultTableModel(); 
		JTable tableProducts = new JTable(model);
		tableProducts.setBackground(SystemColor.activeCaption);
		model.addColumn("ID");
		model.addColumn("Naziv");
		model.addColumn("Kolicina");
		model.addColumn("Cena");
		model.addColumn("Jedinica mere");
		model.addColumn("Poreska oznaka");
		model.addColumn("Poreska stopa");
		tableProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tableProducts.setCellSelectionEnabled(true);
		tableProducts.setRowSelectionAllowed(true);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 217, 1030, 507);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(tableProducts);
		
		String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
		
		
		try {
			Connection connection = DriverManager.getConnection(databaseURL);
		
		
		
		try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Stavke",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet= st1.executeQuery();
		/*yourModel.addRow(new Object[]{str1, str2, str3});*/
		
		while(resultSet.next()) {
		model.addRow(new Object[] {resultSet.getInt(7),resultSet.getString(1),resultSet.getFloat(3),resultSet.getFloat(6),resultSet.getString(2),resultSet.getString(4),resultSet.getFloat(5)});}}
		catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(null, "Artikal nije pronadjen");
		}}
		catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		JButton btnLoad = new JButton("Ucitaj");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
				
				model.setRowCount(0);
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Stavke",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet= st1.executeQuery();
				/*yourModel.addRow(new Object[]{str1, str2, str3});*/
				
				while(resultSet.next()) {
				model.addRow(new Object[] {resultSet.getInt(7),resultSet.getString(1),resultSet.getFloat(3),resultSet.getFloat(6),resultSet.getString(2),resultSet.getString(4),resultSet.getFloat(5)});}}
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Artikal nije pronadjen");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			
				
			
				 
				}
			
		});
		btnLoad.setBounds(115, 86, 89, 23);
		contentPane.add(btnLoad);
		
		JLabel lblNewLabel = new JLabel("Naziv stavke");
		lblNewLabel.setBounds(115, 137, 89, 14);
		contentPane.add(lblNewLabel);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(203, 134, 135, 20);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Pretraga");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
				
				model.setRowCount(0);
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Stavke WHERE Ime LIKE '%"+textFieldSearch.getText()+"%'",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet= st1.executeQuery();
				
				
				while(resultSet.next()) {
				model.addRow(new Object[] {resultSet.getInt(7),resultSet.getString(1),resultSet.getFloat(3),resultSet.getFloat(6),resultSet.getString(2),resultSet.getString(4),resultSet.getFloat(5)});}}
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Artikal nije pronadjen");
				}}
				catch (SQLException e1) {
					// ID = 7, NAZIV = 1, KOL = 3, CENA = 6, JM = 2, POZNAKA= 4, PSTOPA=5
					e1.printStackTrace();
				}
			}
				
		});
		btnSearch.setBounds(115, 165, 89, 23);
		contentPane.add(btnSearch);
		
		JButton btnAdd = new JButton("Dodaj novi artikal");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			artikal.setVisible(true);
			
			}
		});
		btnAdd.setBounds(492, 133, 206, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Obrisi selektovan red");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProducts.getSelectedRow() != -1) {
		               int i = tableProducts.getSelectedRow();
		               String id = String.valueOf(tableProducts.getValueAt(i,0));
		               
		               try {
							Connection connection = DriverManager.getConnection(databaseURL);
						
						
						
						try{PreparedStatement st1= connection.prepareStatement("DELETE FROM Stavke WHERE Id="+Integer.parseInt(id));
						
						
						int row = st1.executeUpdate();
			             
			            if (row > 0) {
			            	JOptionPane.showMessageDialog(null, "Artikal uspesno obrisan");
			            }
						}
						
						
						
						
						catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Doslo je do greske");
						}}
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
		               
		               model.removeRow(tableProducts.getSelectedRow());
		               
		               
			}
				else {JOptionPane.showMessageDialog(null, "Niste selektovali red");}
			}
		});
		btnDelete.setBounds(492, 165, 206, 23);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Azuriraj izmenjen red");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProducts.getSelectedRow() != -1) {
				 int i = tableProducts.getSelectedRow();
	               int id = Integer.parseInt(String.valueOf(tableProducts.getValueAt(i,0)));
	               String naziv=String.valueOf(tableProducts.getValueAt(i, 1));
	               float kolicina= Float.parseFloat(String.valueOf(tableProducts.getValueAt(i, 2)));
	               float cena= Float.parseFloat(String.valueOf(tableProducts.getValueAt(i, 3)));
	               String jm=String.valueOf(tableProducts.getValueAt(i, 4));
	               String poznaka=String.valueOf(tableProducts.getValueAt(i, 5));
	               float stopa= Float.parseFloat(String.valueOf(tableProducts.getValueAt(i, 6)));
	               try {
						Connection connection = DriverManager.getConnection(databaseURL);
					
					
					
					try{
						
						
						
						
						PreparedStatement st1= connection.prepareStatement("UPDATE Stavke SET Ime='"+naziv+"',Kolicina="+kolicina+",Cena="+cena+",Jedinica_mere='"+jm+"',Porez_oznaka='"+poznaka+"',Porez_stopa="+stopa+" WHERE Id="+id+";");
					
					
					int row = st1.executeUpdate();
		             
		            if (row > 0) {
		            	JOptionPane.showMessageDialog(null, "Artikal uspesno azuriran");
		            }
					}
				
					
					
					
					catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Doslo je do greske");
					}}
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
			}else{
				JOptionPane.showMessageDialog(null, "Niste selektovali red");}
			}
		});
		btnUpdate.setBounds(492, 99, 206, 23);
		contentPane.add(btnUpdate);
		
		JLabel lblNewLabel_1 = new JLabel("Promena ID-a");
		lblNewLabel_1.setBounds(902, 63, 103, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Stari ID");
		lblNewLabel_1_1.setBounds(821, 90, 103, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Novi ID");
		lblNewLabel_1_2.setBounds(821, 124, 103, 14);
		contentPane.add(lblNewLabel_1_2);
		
		textFieldOldID = new JTextField();
		textFieldOldID.setBounds(890, 87, 86, 20);
		contentPane.add(textFieldOldID);
		textFieldOldID.setColumns(10);
		
		textFieldNewID = new JTextField();
		textFieldNewID.setColumns(10);
		textFieldNewID.setBounds(890, 121, 86, 20);
		contentPane.add(textFieldNewID);
		
		JButton btnNewChangeID = new JButton("Promeni");
		btnNewChangeID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int oldid=Integer.parseInt(textFieldOldID.getText());
				int newid=Integer.parseInt(textFieldNewID.getText());
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{
					
					
					
					
					PreparedStatement st1= connection.prepareStatement("UPDATE Stavke SET ID="+newid+" WHERE ID="+oldid+";");
				
				
				int row = st1.executeUpdate();
	             
	            if (row > 0) {
	            	JOptionPane.showMessageDialog(null, "Artikal uspesno azuriran");
	            }
				}
			
				
				
				
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Doslo je do greske");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnNewChangeID.setBounds(890, 149, 89, 23);
		contentPane.add(btnNewChangeID);
		
		JButton btnBack = new JButton("Nazad");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 final POS pos = new POS();
				dispose();
				pos.frame.setVisible(true);
			}
		});
		btnBack.setBounds(115, 11, 89, 23);
		contentPane.add(btnBack);
	}
}
