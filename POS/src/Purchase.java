import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;


public class Purchase extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private JTextField textFieldID;
	private JTextField textFieldQuantity;
	
	private JTextField textFieldCena;
	private JTable tableSearch;
	private JTextField textFieldSearch;
	private JTextField textFieldKasir;
	ArrayList<Purchase> lista = new ArrayList<>();
	DefaultTableModel model = new DefaultTableModel(); 
	JTable tableProducts = new JTable(model);
	
	float total;
	float kolicina;
	String stavka;
	String p_oznaka;
	float p_stopa;
	float cena;
	String jedinica_mere;
	
	
	public String getJedMere() {
		
		return jedinica_mere;
	}
	
	public void setJedMere(String jedinica_mere1) {
		this.jedinica_mere=jedinica_mere1;
	}
	
	
	public float getCena() {
		return cena;}
	
	public void setCena(float cena1) {
		this.cena=cena1;
	}
	
	public float getPStopa() {
		return p_stopa;
	}
	
	public void setPStopa(float p_stopa1) {
		this.p_stopa=p_stopa1;
	}
	
	
	public String getPOznaka() {
		return p_oznaka;
	}
	
	public void setPOznaka(String p_oznaka1) {
		this.p_oznaka=p_oznaka1;
	}
	
	public float getKolicina() {
		return kolicina;
	}
	
	public void setKolicina(float kolicina1) {
		this.kolicina=kolicina1;
	}
	
	public String getStavka() {
		return stavka;
	}
	
	public void setStavka(String stavka1) {
		this.stavka=stavka1;
	}
	
	public float getTotal() {
		
		return total;
	}
	public void setTotal(float total1) {
		this.total = total1;
	}

	
	public Purchase(String stavka,float cena,float kolicina,String jedinica_mere,String p_oznaka, float p_stopa) {
		this.stavka=stavka;
		this.cena=cena;
		this.kolicina=kolicina;
		this.p_oznaka=p_oznaka;
		this.p_stopa=p_stopa;
		this.jedinica_mere=jedinica_mere;
		
	}
	public void ReadJSON(String file) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser=new JSONParser();
		FileReader fileReader = new FileReader(file);
		JSONArray array = (JSONArray) parser.parse(fileReader);
		
		for(Object obj : array) {
			JSONObject jobj = (JSONObject) obj;
			//model.addRow(new Object[] {modelSearch.getValueAt(i, 0),modelSearch.getValueAt(i, 1),kolicina1,modelSearch.getValueAt(i, 2),modelSearch.getValueAt(i, 3),modelSearch.getValueAt(i, 4),modelSearch.getValueAt(i, 5)})
			model.addRow(new Object[] {String.valueOf(jobj.get("id")),String.valueOf(jobj.get("stavka")),String.valueOf(jobj.get("kolicina")),String.valueOf(jobj.get("cena")),String.valueOf(jobj.get("jedinica_mere")),String.valueOf(jobj.get("p_oznaka")),String.valueOf(jobj.get("p_stopa"))});
			String naziv = String.valueOf(jobj.get("stavka"));
			float kolicina = Float.parseFloat(String.valueOf(jobj.get("kolicina")));
			float cena = Float.parseFloat(String.valueOf(jobj.get("cena")));
			float p_stopa = Float.parseFloat(String.valueOf(jobj.get("p_stopa")));
			String jm = String.valueOf(jobj.get("jedinica_mere"));
			String p_oznaka = String.valueOf(jobj.get("p_oznaka"));
			
			Purchase purchase = new Purchase(naziv,cena,kolicina,jm,p_oznaka,p_stopa);
			lista.add(purchase);
		}	
	}
	public void ReadCSV(String file) throws IOException {
	String line="";
	String split=",";
	BufferedReader br =new BufferedReader(new FileReader(file));
	br.readLine();
		while((line=br.readLine()) != null) {
			String[] purchase= line.split(split);
			model.addRow(new Object[] {purchase[0],purchase[1],purchase[2],purchase[3],purchase[4],purchase[5],purchase[6]});
			String naziv = purchase[1];
			float kolicina = Float.parseFloat(purchase[3]);
			float cena = Float.parseFloat(purchase[2]);
			float p_stopa = Float.parseFloat(purchase[6]);
			String jm = purchase[4];
			String p_oznaka = purchase[5];
			
			Purchase purchase1 = new Purchase(naziv,cena,kolicina,jm,p_oznaka,p_stopa);
			lista.add(purchase1);
			
		}
		br.close();
	}
	
	
	public Purchase() {
		new JFrame();
		setBounds(100, 100, 1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		textFieldID = new JTextField();
		textFieldID.setBounds(90, 110, 86, 20);
		getContentPane().add(textFieldID);
		textFieldID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID artikla:");
		lblNewLabel.setBounds(10, 113, 91, 14);
		getContentPane().add(lblNewLabel);
		
		textFieldQuantity = new JTextField();
		textFieldQuantity.setBounds(90, 141, 86, 20);
		getContentPane().add(textFieldQuantity);
		textFieldQuantity.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Kolicina:");
		lblNewLabel_1.setBounds(10, 144, 70, 14);
		getContentPane().add(lblNewLabel_1);
		
		
		model.addColumn("ID");
		model.addColumn("Naziv");
		model.addColumn("Kolicina");
		model.addColumn("Cena");
		model.addColumn("Jedinica mere");
		model.addColumn("Poreska oznaka");
		model.addColumn("Poreska stopa");
		
		tableProducts.setBackground(UIManager.getColor("Button.shadow"));
		tableProducts.setBounds(10, 218, 697, 288);
		tableProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tableProducts.setCellSelectionEnabled(false);
		tableProducts.setRowSelectionAllowed(true);
		tableProducts.setDefaultEditor(Object.class, null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 218, 697, 288);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(tableProducts);
		
		DefaultTableModel modelSearch = new DefaultTableModel(); 
		JTable tableSearch = new JTable(modelSearch);
		modelSearch.addColumn("ID");
		modelSearch.addColumn("Naziv");
		modelSearch.addColumn("Cena");
		modelSearch.addColumn("Jed. mere");
		modelSearch.addColumn("P.oznaka");
		modelSearch.addColumn("P.stopa");
		tableSearch.getColumn("ID").setMaxWidth(35);
		tableSearch.setBackground(UIManager.getColor("Button.shadow"));
		tableSearch.setBounds(777, 218, 307, 288);
		tableSearch.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tableSearch.setCellSelectionEnabled(false);
		tableSearch.setRowSelectionAllowed(true);
		tableSearch.setDefaultEditor(Object.class, null);
		
		
		JScrollPane scrollPaneSearch = new JScrollPane();
		scrollPaneSearch.setBounds(777, 218, 397, 288);
		getContentPane().add(scrollPaneSearch);
		scrollPaneSearch.setViewportView(tableSearch);
		
		
		
	  
		
JButton btnAdd = new JButton("Dodaj");
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
				
			
			try {
				Connection connection = DriverManager.getConnection(databaseURL);
			int id= Integer.parseInt(textFieldID.getText());
			
			
			try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Stavke WHERE ID="+id+"",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet= st1.executeQuery();
			// ID = 7, NAZIV = 1, KOL = 3, CENA = 6, JM = 2, POZNAKA= 4, PSTOPA=5
			resultSet.first();
			float kolicina = Float.parseFloat(textFieldQuantity.getText());
			Purchase purchase1=new Purchase(resultSet.getString(1),resultSet.getFloat(6),kolicina,resultSet.getString(2),resultSet.getString(4),resultSet.getFloat(5));
			lista.add(purchase1);
			model.addRow(new Object[] {resultSet.getInt(7),resultSet.getString(1),kolicina,resultSet.getFloat(6),resultSet.getString(2),resultSet.getString(4),resultSet.getString(5)});}
			catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Artikal nije pronadjen");
			}}
			catch (SQLException e) {
				
				e.printStackTrace();
			}
			float total = 0;
			 for(int i = 0; i < tableProducts.getRowCount(); i++){
			        
			       float cena =Float.parseFloat(tableProducts.getValueAt(i,3)+"");
			       float kolicina = Float.parseFloat(tableProducts.getValueAt(i, 2)+"");
			        total = cena*kolicina+total;
			     
			        
			    }
			textFieldCena.setText(String.valueOf(total));
			setTotal(Float.parseFloat(textFieldCena.getText()));
			
		
			 
			}
		});
		
		btnAdd.setBounds(90, 172, 89, 23);
		getContentPane().add(btnAdd);
		
		textFieldCena = new JTextField();
		textFieldCena.setBounds(263, 517, 86, 20);
		getContentPane().add(textFieldCena);
		textFieldCena.setColumns(10);
		textFieldCena.setText("0");
		
		JLabel lblNewLabel_2 = new JLabel("Ukupno");
		lblNewLabel_2.setBounds(203, 520, 52, 14);
		getContentPane().add(lblNewLabel_2);
		
		JButton btnDeleteRow = new JButton("Ukloni");
		btnDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProducts.getSelectedRow() != -1) {
		               
		               model.removeRow(tableProducts.getSelectedRow());
		               lista.remove(tableProducts.getSelectedRow()+1);
		               
			}
			
				  float total = 0;
		 			 for(int i = 0; i < tableProducts.getRowCount(); i++){
		 			        
		 			       float cena =Float.parseFloat(tableProducts.getValueAt(i,3)+"");
		 			       float kolicina = Float.parseFloat(tableProducts.getValueAt(i, 2)+"");
		 			        total = cena*kolicina+total;
		 			     
		 			        
		 			    }
		 			textFieldCena.setText(String.valueOf(total));
		 			setTotal(Float.parseFloat(textFieldCena.getText()));
			}
		});
		btnDeleteRow.setBounds(217, 172, 89, 23);
		getContentPane().add(btnDeleteRow);
		
	
		
		JLabel lblNewLabel_3 = new JLabel("Ime:");
		lblNewLabel_3.setBounds(763, 144, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(800, 141, 119, 21);
		getContentPane().add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Pretraga");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String databaseURL="jdbc:ucanaccess://BazaPodataka1.accdb";
				
				
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				modelSearch.setRowCount(0);
				try{PreparedStatement st1= connection.prepareStatement("SELECT * FROM Stavke WHERE Ime LIKE '%"+textFieldSearch.getText()+"%';",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet= st1.executeQuery();
				// ID = 7, NAZIV = 1, KOL = 3, CENA = 6, JM = 2, POZNAKA= 4, PSTOPA=5
				
				
				while(resultSet.next()) {
				modelSearch.addRow(new Object[] {resultSet.getInt(7),resultSet.getString(1),resultSet.getFloat(6),resultSet.getString(2),resultSet.getString(4),resultSet.getString(5)});
				
				}}
				
				catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null, "Artikal nije pronadjen");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(799, 172, 89, 23);
		getContentPane().add(btnSearch);
		
		JButton btnAddSearched = new JButton("Dodaj");
		btnAddSearched.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!textFieldQuantity.getText().isBlank()) {
					
				
				
				if(tableSearch.getSelectedRow() != -1) {
		               
			            int i = tableSearch.getSelectedRow();   
			            float kolicina1 = Float.parseFloat(textFieldQuantity.getText());
			              model.addRow(new Object[] {modelSearch.getValueAt(i, 0),modelSearch.getValueAt(i, 1),kolicina1,modelSearch.getValueAt(i, 2),modelSearch.getValueAt(i, 3),modelSearch.getValueAt(i, 4),modelSearch.getValueAt(i, 5)});
			              String sStavka=String.valueOf(modelSearch.getValueAt(i, 1));
			              float sCena= Float.parseFloat(String.valueOf(modelSearch.getValueAt(i, 2)));
			              String sJedMere=String.valueOf(modelSearch.getValueAt(i, 3));
			              String sPOznaka=String.valueOf(modelSearch.getValueAt(i, 4));
			              float sPStopa= Float.parseFloat(String.valueOf(modelSearch.getValueAt(i, 5)));
			              Purchase purchase2 = new Purchase(sStavka,sCena,kolicina1,sJedMere,sPOznaka,sPStopa);
			              lista.add(purchase2);
							
			              float total = 0;
			 			 for(i = 0; i < tableProducts.getRowCount(); i++){
			 			        
			 			       float cena =Float.parseFloat(tableProducts.getValueAt(i,3)+"");
			 			       float kolicina = Float.parseFloat(tableProducts.getValueAt(i, 2)+"");
			 			        total = cena*kolicina+total;
			 			     
			 			        
			 			    }
			 			textFieldCena.setText(String.valueOf(total));
			 			setTotal(Float.parseFloat(textFieldCena.getText()));
			              
			              
		               
			}
				else{JOptionPane.showMessageDialog(null, "Odaberite red koji zelite da dodate");}
				
				
			}
				else {JOptionPane.showMessageDialog(null,"Unesite kolicinu");}}
		});
		btnAddSearched.setBounds(908, 172, 89, 23);
		getContentPane().add(btnAddSearched);
		
		JButton btnPurchase = new JButton("Naplati");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Purchase purchase1= new Purchase(getStavka(),getKolicina());
				String kasir=textFieldKasir.getText();
				Naplata naplata = new Naplata(getTotal(),lista,kasir);
				naplata.setVisible(true);
				dispose();
				
				
			}
		});
		btnPurchase.setBounds(459, 599, 104, 36);
		getContentPane().add(btnPurchase);
		
		JButton btnBack = new JButton("Nazad");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				final POS pos = new POS();
				pos.frame.setVisible(true);
			}
		});
		btnBack.setBounds(87, 45, 89, 23);
		getContentPane().add(btnBack);
		
		JLabel lblNewLabel_4 = new JLabel("Kasir:");
		lblNewLabel_4.setBounds(412, 49, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		textFieldKasir = new JTextField();
		textFieldKasir.setBounds(450, 46, 86, 20);
		getContentPane().add(textFieldKasir);
		textFieldKasir.setColumns(10);
		textFieldKasir.setText("Ivan");
		
		JLabel lblNewLabel_5 = new JLabel("Ucitaj podatke iz fajla");
		lblNewLabel_5.setBounds(426, 136, 197, 30);
		getContentPane().add(lblNewLabel_5);
		
		JButton btnJSON = new JButton("JSON");
		btnJSON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser= new JFileChooser();
				fileChooser.showOpenDialog(null);
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				String jsonfile= String.valueOf(file);
				try {
					ReadJSON(jsonfile);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 float total = 0;
	 			 for(int i = 0; i < tableProducts.getRowCount(); i++){
	 			        
	 			       float cena =Float.parseFloat(tableProducts.getValueAt(i,3)+"");
	 			       float kolicina = Float.parseFloat(tableProducts.getValueAt(i, 2)+"");
	 			        total = cena*kolicina+total;
	 			     
	 			        
	 			    }
	 			textFieldCena.setText(String.valueOf(total));
	 			setTotal(Float.parseFloat(textFieldCena.getText()));
				
			}
		});
		btnJSON.setBounds(379, 165, 95, 36);
		getContentPane().add(btnJSON);
		
		JButton btnCSV = new JButton("CSV");
		btnCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser= new JFileChooser();
				fileChooser.showOpenDialog(null);
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				String CSVfile= String.valueOf(file);
				try {
					ReadCSV(CSVfile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				float total = 0;
	 			 for(int i = 0; i < tableProducts.getRowCount(); i++){
	 			        
	 			       float cena =Float.parseFloat(tableProducts.getValueAt(i,3)+"");
	 			       float kolicina = Float.parseFloat(tableProducts.getValueAt(i, 2)+"");
	 			        total = cena*kolicina+total;
	 			     
	 			        
	 			    }
	 			textFieldCena.setText(String.valueOf(total));
	 			setTotal(Float.parseFloat(textFieldCena.getText()));
				
			}
		});
		btnCSV.setBounds(491, 165, 95, 36);
		getContentPane().add(btnCSV);
		
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
	}


