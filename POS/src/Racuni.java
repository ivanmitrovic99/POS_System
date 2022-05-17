import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Racuni extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableProducts;
	
	
	

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
		}
	public static final String DATE_FORMAT_YESTERDAY = "yyyy-MM-dd HH:mm:ss";
	public static String yesterday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YESTERDAY);
		cal.add(Calendar.DATE, -1);
		return sdf.format(cal.getTime());
		}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Racuni frame = new Racuni();
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
	public Racuni() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel(); 
		JTable table = new JTable(model);
		table.setBackground(SystemColor.activeCaption);
		model.addColumn("ID");
		model.addColumn("Kasir");
		model.addColumn("Cena");
		model.addColumn("Porez");
		model.addColumn("Datum");
		
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 199, 710, 473);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);
		
		JButton btnUcitaj = new JButton("Ucitaj");
		btnUcitaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				model.setRowCount(0);
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Racun",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet= st1.executeQuery();
				/*yourModel.addRow(new Object[]{str1, str2, str3});*/
				
				while(resultSet.next()) {
				model.addRow(new Object[] {resultSet.getString(1),resultSet.getString(2),resultSet.getFloat(3),resultSet.getFloat(4),resultSet.getString(5).substring(0, 19)});}}
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Greska pri ucitavanju racuna");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
			}
		});
		btnUcitaj.setBounds(49, 144, 102, 23);
		contentPane.add(btnUcitaj);
		
		DefaultTableModel modelProducts = new DefaultTableModel(); 
		JTable tableProducts = new JTable(modelProducts);
		tableProducts.setBackground(SystemColor.activeCaption);
		modelProducts.addColumn("Naziv");
		modelProducts.addColumn("Kolicina");
		modelProducts.addColumn("Cena");
		modelProducts.addColumn("Jedinica m.");
		modelProducts.addColumn("P. oznaka");
		modelProducts.addColumn("P. stopa");
		
		tableProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tableProducts.setCellSelectionEnabled(true);
		tableProducts.setRowSelectionAllowed(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(772, 199, 402, 473);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tableProducts);
		
		JButton btnProducts = new JButton("Ucitaj stavke");
		btnProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				modelProducts.setRowCount(0);
				if(table.getSelectedRow()!=-1) {
					int i = table.getSelectedRow();
					int index=Integer.parseInt(String.valueOf(model.getValueAt(i, 0)));
					
					try {
						Connection connection = DriverManager.getConnection(databaseURL);
					
					
					
					try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Artikal WHERE RacunID="+index+";",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet resultSet= st1.executeQuery();
					
					
					while(resultSet.next()) {
					modelProducts.addRow(new Object[] {resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(7),resultSet.getString(8)});
					}}
					catch (SQLException e1) {
						
						JOptionPane.showMessageDialog(null, "Greska pri ucitavanju racuna");
					}}
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnProducts.setBounds(888, 130, 138, 23);
		contentPane.add(btnProducts);
		
		JButton btnBack = new JButton("Nazad");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final POS pos = new POS();
				dispose();
				pos.frame.setVisible(true);
			}
			
		});
		btnBack.setBounds(49, 30, 102, 23);
		contentPane.add(btnBack);
		
		JButton btnDanasnji = new JButton("Poslednja 24h");
		btnDanasnji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				model.setRowCount(0);
		
			
				
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Racun WHERE Datum<'"+now()+"' AND Datum > '"+yesterday()+"';");
				ResultSet resultSet= st1.executeQuery();
				/*yourModel.addRow(new Object[]{str1, str2, str3});*/
				
				while(resultSet.next()) {
				model.addRow(new Object[] {resultSet.getString(1),resultSet.getString(2),resultSet.getFloat(3),resultSet.getFloat(4),resultSet.getString(5).substring(0, 19)});}}
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Greska pri ucitavanju racuna");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnDanasnji.setBounds(395, 144, 143, 23);
		contentPane.add(btnDanasnji);
		
		JButton btnRefund = new JButton("Storniranje");
		btnRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1) {
				ArrayList<Purchase> lista = new ArrayList<>();
				String kasir=String.valueOf(model.getValueAt(table.getSelectedRow(), 1));
				float total=0;
				for(int j = 0; j < tableProducts.getRowCount(); j++){
					total=total-Float.parseFloat(String.valueOf(modelProducts.getValueAt(j, 1)))*Float.parseFloat(String.valueOf(modelProducts.getValueAt(j, 2)));
				}
				for(int i = 0; i < tableProducts.getRowCount(); i++){
					String stavka= String.valueOf(modelProducts.getValueAt(i, 0));
					float kolicina = Float.parseFloat(String.valueOf(modelProducts.getValueAt(i, 1)));
					float cena=Float.parseFloat(String.valueOf(modelProducts.getValueAt(i, 2)));
					String jm= String.valueOf(modelProducts.getValueAt(i, 3));
					String p_oznaka= String.valueOf(modelProducts.getValueAt(i, 4));
					float p_stopa= Float.parseFloat(String.valueOf(modelProducts.getValueAt(i, 5)));
					Purchase purchase= new Purchase(stavka,cena,kolicina,jm,p_oznaka,p_stopa);
					lista.add(purchase);
					
					}
					Naplata naplata1 = new Naplata(total,lista,kasir);
					naplata1.setVisible(true);
					dispose();
				
					
					/*float kolicina1 = Float.parseFloat(textFieldQuantity.getText());
			              model.addRow(new Object[] {modelSearch.getValueAt(i, 0),modelSearch.getValueAt(i, 1),kolicina1,modelSearch.getValueAt(i, 2),modelSearch.getValueAt(i, 3),modelSearch.getValueAt(i, 4),modelSearch.getValueAt(i, 5)});
			              String sStavka=String.valueOf(modelSearch.getValueAt(i, 1));
			              float sCena= Float.parseFloat(String.valueOf(modelSearch.getValueAt(i, 2)));
			              String sJedMere=String.valueOf(modelSearch.getValueAt(i, 3));
			              String sPOznaka=String.valueOf(modelSearch.getValueAt(i, 4));
			              float sPStopa= Float.parseFloat(String.valueOf(modelSearch.getValueAt(i, 5)));
			              Purchase purchase2 = new Purchase(sStavka,sCena,kolicina1,sJedMere,sPOznaka,sPStopa);
			              lista.add(purchase2);*/
				}
			}
		});
		btnRefund.setBounds(211, 144, 118, 23);
		contentPane.add(btnRefund);
		
		JButton btnRefundedInvoices = new JButton("Stornirani racuni");
		btnRefundedInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				model.setRowCount(0);
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Racun WHERE Cena<0",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet= st1.executeQuery();
				/*yourModel.addRow(new Object[]{str1, str2, str3});*/
				
				while(resultSet.next()) {
				model.addRow(new Object[] {resultSet.getString(1),resultSet.getString(2),resultSet.getFloat(3),resultSet.getFloat(4),resultSet.getString(5).substring(0, 19)});}}
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Greska pri ucitavanju racuna");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
			}
		});
		btnRefundedInvoices.setBounds(395, 99, 143, 23);
		contentPane.add(btnRefundedInvoices);
		
		
		String databaseURL="jdbc:ucanaccess://Racuni.accdb";
		model.setRowCount(0);
		try {
			Connection connection = DriverManager.getConnection(databaseURL);
		
		
		
		try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Racun",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet= st1.executeQuery();
		/*yourModel.addRow(new Object[]{str1, str2, str3});*/
		
		while(resultSet.next()) {
		model.addRow(new Object[] {resultSet.getString(1),resultSet.getString(2),resultSet.getFloat(3),resultSet.getFloat(4),resultSet.getString(5).substring(0, 19)});}}
		catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(null, "Greska pri ucitavanju racuna");
		}}
		catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	}
}
