import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Naplata extends JFrame{
	private JTextField textFieldToPay;
	private Purchase purchase = new Purchase();
	private JTextField textFieldPaid;
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	float porez=0;
	float ukupan_porez=0;
	float total_refund=0;
	public static String now() {
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	return sdf.format(cal.getTime());
	}
	private static class MyTextClass {
		PDDocument document;
		PDPageContentStream contentStream;
		
		public MyTextClass(PDDocument document, PDPageContentStream contentStream)  {
			this.document=document;
			this.contentStream=contentStream;
		}
		
		void AddSingleLineText(String text,int xPos, int yPos, PDFont font, float fontSize) throws IOException {
			
			
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(xPos, yPos);
			contentStream.showText(text);
			contentStream.endText();
			contentStream.moveTo(0, 0);
			
		}

		
void AddMultiLineText(String[] textArray,float leading,int xPos, int yPos, PDFont font, float fontSize) throws IOException  {
			
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.setLeading(leading);
			contentStream.newLineAtOffset(xPos, yPos);
			for(String text:textArray) {
				contentStream.showText(text);
				contentStream.newLine();
			}
			contentStream.endText();
			contentStream.moveTo(0, 0);
		/*	cs.close();
								pos=540;
								page = new PDPage(PDRectangle.A5);
								myPdf.addPage(page);
								cs=new PDPageContentStream(myPdf,page);
								klasa=new MyTextClass(myPdf,cs); */
			
			
		}


	}
	public void CheckPos(int pos,PDPage page, PDDocument myPdf,PDPageContentStream cs, MyTextClass klasa) throws IOException {
		
		if(pos<30) {
			cs.close();
			pos=540;
			page = new PDPage(PDRectangle.A5);
			myPdf.addPage(page);
			cs=new PDPageContentStream(myPdf,page);
			klasa=new MyTextClass(myPdf,cs);
		}
		
		
			}

	public Naplata(float total,ArrayList<Purchase> lista,String kasir) {
		new JFrame();
		setBounds(100, 100, 1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		textFieldToPay = new JTextField();
		textFieldToPay.setBounds(129, 64, 86, 20);
		getContentPane().add(textFieldToPay);
		textFieldToPay.setColumns(10);
		textFieldToPay.setText(String.valueOf(total));
		
		
		JLabel lblNewLabel = new JLabel("Za naplatu");
		lblNewLabel.setBounds(44, 67, 75, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Naplati");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PDDocument myPdf = new PDDocument();
				PDPage page= new PDPage(PDRectangle.A5);
			
				myPdf.addPage(page);
				PDPageContentStream cs;
				
				
				try {
					cs = new PDPageContentStream(myPdf,page);
				
				MyTextClass klasa=new MyTextClass(myPdf,cs);
				
				String text[]= new String[] {"========== ФИСКАЛНИ РАЧУН ==========","10000752342", "DRUSTVO ZA PROMET VALJEVO","1055965-MARKET 25","FILIPA VISNJICA 23 VALJEVO","------------------------"};
				String tableHeader= new String("Назив       Цена                     Кол.                   Укупно");
				String name= new String("KEKS JAFA KEKS JAFAKEKS JAFAKEKS JAFA ");
				String lower = new String("                          50                        5                              250");
				
					try {
						PDType0Font font = PDType0Font.load(myPdf, new File("Alice-Regular.ttf"));
						klasa.AddSingleLineText("========== ФИСКАЛНИ РАЧУН ==========", 30, 540, font, 9);
						klasa.AddSingleLineText("10000752342", 90, 532, font, 9);
						klasa.AddSingleLineText("DRUSTVO ZA PROMET VALJEVO", 30, 524, font, 9);
						klasa.AddSingleLineText("1055965-MARKET 25", 90, 516, font, 9);
						klasa.AddSingleLineText("FILIPA VISNJICA 23 VALJEVO", 80, 508, font, 9);
						klasa.AddSingleLineText("---------------------------------------------", 30, 500, font, 11);
						//klasa.AddMultiLineText(text,8, 30, 550, font, 9);
						klasa.AddSingleLineText(tableHeader, 30, 484, font, 9);
						
						int pos=476;
						for(int i=0;i<lista.size();i++) {
							
							klasa.AddSingleLineText(lista.get(i).stavka +"/"+lista.get(i).jedinica_mere+ " (" + lista.get(i).p_oznaka +")",  30, pos-8, font, 9);
							klasa.AddSingleLineText(String.valueOf(lista.get(i).cena),70,pos-16,font,9);
							klasa.AddSingleLineText(String.valueOf(lista.get(i).kolicina),135,pos-16,font,9);
							klasa.AddSingleLineText(String.valueOf(lista.get(i).cena*lista.get(i).kolicina),195,pos-16,font,9);
							
							pos=pos-30;
							
							if(pos<30) {
								cs.close();
								pos=540;
								page = new PDPage(PDRectangle.A5);
								myPdf.addPage(page);
								cs=new PDPageContentStream(myPdf,page);
								klasa=new MyTextClass(myPdf,cs);
							}
						}
						
						klasa.AddSingleLineText("---------------------------------------------", 30, pos-8, font, 11);
						klasa.AddSingleLineText("За уплату", 30, pos-16, font, 9);
						klasa.AddSingleLineText(String.valueOf(total), 195, pos-16, font, 9);
						klasa.AddSingleLineText("Уплаћено - Готовина", 30, pos-24, font, 9);
						klasa.AddSingleLineText(textFieldPaid.getText(), 195, pos-24, font, 9);
						klasa.AddSingleLineText("Повраћај", 30, pos-32, font, 9);
						klasa.AddSingleLineText(String.valueOf(Float.parseFloat(textFieldPaid.getText())-total), 195, pos-32, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-40, font, 9);
						klasa.AddSingleLineText("Ознака        Име                     Стопа                 Порез", 30, pos-48, font, 9);
						String oznaka= lista.get(0).p_oznaka;
						ArrayList<String> oznake = new ArrayList<String>();
						oznake.add(oznaka);
						if(lista.size()>1) {
						for(int j=1;j<lista.size();j++) {
							if(!oznake.contains(lista.get(j).p_oznaka)){
								oznake.add(lista.get(j).p_oznaka);
							}
						}
						}
							//System.out.println(oznake[1]);
						
						
						
						
						
						String stopa = "";
						ArrayList<Float> porezi= new ArrayList<Float>();
						pos=pos-56;
						for(int j=0;j<oznake.size();j++) {
						
						for(int i=0;i<lista.size();i++) {
							if(oznake.get(j).equals(lista.get(i).p_oznaka)) {
								porezi.add(lista.get(i).cena*lista.get(i).kolicina*(lista.get(i).p_stopa/100));
								
								//porez=porez+(lista.get(i).cena*lista.get(i).kolicina*(lista.get(i).p_stopa/100));
								stopa=String.valueOf(lista.get(i).p_stopa);
							}
							
							
						}
						for(int k=0;k<porezi.size();k++) {
							porez=porez+porezi.get(k);
							
						}
						
						
						ukupan_porez=ukupan_porez+porez;
						klasa.AddSingleLineText(oznake.get(j), 30, pos, font, 9);
						klasa.AddSingleLineText("P-PDV", 70, pos, font, 9);
						klasa.AddSingleLineText(stopa + "%", 137, pos, font, 9);
						klasa.AddSingleLineText(String.valueOf(porez).format("%.2f", porez), 195, pos, font, 9);
						pos=pos-8;
						porez=0;
						porezi.clear();
						}
						klasa.AddSingleLineText("---------------------------------------------", 30, pos-8, font, 11);
						klasa.AddSingleLineText("Укупан износ пореза", 30, pos-16, font, 9);
						klasa.AddSingleLineText(String.valueOf(ukupan_porez).format("%.2f",ukupan_porez), 195, pos-16, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-24, font, 9);
						pos=pos-32;
						klasa.AddSingleLineText("ПФР време", 30, pos, font, 9);
						klasa.AddSingleLineText(now(), 145, pos, font, 9);
						klasa.AddSingleLineText("ПФР број рачуна", 30, pos-8, font, 9);
						klasa.AddSingleLineText("231T321-3213214-32132", 135, pos-8, font, 9);
						klasa.AddSingleLineText("Бројач рачуна", 30, pos-16, font, 9);
						klasa.AddSingleLineText("Касир", 30, pos-24, font, 9);
						klasa.AddSingleLineText(kasir, 175, pos-24, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-32, font, 9);
						klasa.AddSingleLineText("ХВАЛА НА ПОСЕТИ", 85, pos-52, font, 9);
						
						
						cs.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					// TODO Auto-generated catch block
					
				
				//String filepath="C:\\Users\\Ivan\\ee\\myPdf.pdf";
					String filepath="racun.pdf";
				
					try {
						myPdf.save(filepath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				
				
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("INSERT INTO Racun(Kasir,Cena,Porez,Datum) VALUES ('"+kasir+"',"+total+","+ukupan_porez+",'"+now()+"')");
					st1.executeUpdate();
					
					PreparedStatement st3= connection.prepareStatement("SELECT * FROM Racun");
					ResultSet rs =st3.executeQuery();
					int racun_id=0;
					while(rs.next()) {
					racun_id=rs.getInt(1);}
					
				for(int i=0;i<lista.size();i++) {
					PreparedStatement st2= connection.prepareStatement("INSERT INTO Artikal(Naziv,Kolicina,Artikal_Cena,Jedinica_mere,RacunID,Poreska_oznaka,Poreska_stopa) VALUES ('"+lista.get(i).stavka+"',"+lista.get(i).kolicina+","+lista.get(i).cena+",'"+lista.get(i).jedinica_mere+"',"+racun_id+",'"+lista.get(i).p_oznaka+"',"+lista.get(i).p_stopa+");");
					st2.executeUpdate();
				
				}
				
				
			
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Greska");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(126, 220, 89, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Uplaceno");
		lblNewLabel_3.setBounds(44, 93, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		textFieldPaid = new JTextField();
		textFieldPaid.setBounds(129, 90, 86, 20);
		getContentPane().add(textFieldPaid);
		textFieldPaid.setColumns(10);
		
		JButton btnNewPurchase = new JButton("Nova kupovina");
		btnNewPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				purchase.setVisible(true);
			}
		});
		btnNewPurchase.setBounds(342, 220, 128, 23);
		getContentPane().add(btnNewPurchase);
		
		JButton btnRefund = new JButton("Storniraj");
		btnRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PDDocument myPdf = new PDDocument();
				PDPage page= new PDPage(PDRectangle.A5);
			
				myPdf.addPage(page);
				PDPageContentStream cs;
				for(int i=0;i<lista.size();i++) {
					total_refund=total_refund + lista.get(i).cena*lista.get(i).kolicina;
				}
				
				try {
					cs = new PDPageContentStream(myPdf,page);
				
				MyTextClass klasa=new MyTextClass(myPdf,cs);
				
				String text[]= new String[] {"========== ФИСКАЛНИ РАЧУН ==========","10000752342", "DRUSTVO ZA PROMET VALJEVO","1055965-MARKET 25","FILIPA VISNJICA 23 VALJEVO","------------------------"};
				String tableHeader= new String("Назив       Цена                     Кол.                   Укупно");
				String name= new String("KEKS JAFA KEKS JAFAKEKS JAFAKEKS JAFA ");
				String lower = new String("                          50                        5                              250");
				
					try {
						PDType0Font font = PDType0Font.load(myPdf, new File("C:\\Users\\Ivan\\Downloads\\alice\\Alice-Regular.ttf"));
						klasa.AddSingleLineText("========== ФИСКАЛНИ РАЧУН ==========", 30, 540, font, 9);
						klasa.AddSingleLineText("10000752342", 90, 532, font, 9);
						klasa.AddSingleLineText("DRUSTVO ZA PROMET VALJEVO", 30, 524, font, 9);
						klasa.AddSingleLineText("1055965-MARKET 25", 90, 516, font, 9);
						klasa.AddSingleLineText("ПРОМЕТ - РЕФУНДАЦИЈА", 80, 508, font, 9);
						klasa.AddSingleLineText("---------------------------------------------", 30, 500, font, 11);
						//klasa.AddMultiLineText(text,8, 30, 550, font, 9);
						klasa.AddSingleLineText(tableHeader, 30, 484, font, 9);
						
						int pos=476;
						for(int i=0;i<lista.size();i++) {
							
							klasa.AddSingleLineText(lista.get(i).stavka +"/"+lista.get(i).jedinica_mere+ " (" + lista.get(i).p_oznaka +")",  30, pos-8, font, 9);
							klasa.AddSingleLineText(String.valueOf(lista.get(i).cena),70,pos-16,font,9);
							klasa.AddSingleLineText(String.valueOf(lista.get(i).kolicina),135,pos-16,font,9);
							klasa.AddSingleLineText("-" + String.valueOf(lista.get(i).cena*lista.get(i).kolicina),195,pos-16,font,9);
							
							pos=pos-30;
							
							if(pos<30) {
								cs.close();
								pos=540;
								page = new PDPage(PDRectangle.A5);
								myPdf.addPage(page);
								cs=new PDPageContentStream(myPdf,page);
								klasa=new MyTextClass(myPdf,cs);
							}
						}
						
						klasa.AddSingleLineText("---------------------------------------------", 30, pos-8, font, 11);
						klasa.AddSingleLineText("За уплату", 30, pos-16, font, 9);
						klasa.AddSingleLineText(String.valueOf(-total_refund), 195, pos-16, font, 9);
						klasa.AddSingleLineText("Уплаћено - Готовина", 30, pos-24, font, 9);
						klasa.AddSingleLineText(textFieldPaid.getText(), 195, pos-24, font, 9);
						klasa.AddSingleLineText("Повраћај", 30, pos-32, font, 9);
						klasa.AddSingleLineText(String.valueOf(Float.parseFloat(textFieldPaid.getText())+total_refund), 195, pos-32, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-40, font, 9);
						klasa.AddSingleLineText("Ознака        Име                     Стопа                 Порез", 30, pos-48, font, 9);
						String oznaka= lista.get(0).p_oznaka;
						ArrayList<String> oznake = new ArrayList<String>();
						oznake.add(oznaka);
						if(lista.size()>1) {
						for(int j=1;j<lista.size();j++) {
							if(!oznake.contains(lista.get(j).p_oznaka)){
								oznake.add(lista.get(j).p_oznaka);
							}
						}
						}
							//System.out.println(oznake[1]);
						
						
						
						
						
						String stopa = "";
						ArrayList<Float> porezi= new ArrayList<Float>();
						pos=pos-56;
						for(int j=0;j<oznake.size();j++) {
						
						for(int i=0;i<lista.size();i++) {
							if(oznake.get(j).equals(lista.get(i).p_oznaka)) {
								porezi.add(lista.get(i).cena*lista.get(i).kolicina*(lista.get(i).p_stopa/100));
								
								//porez=porez+(lista.get(i).cena*lista.get(i).kolicina*(lista.get(i).p_stopa/100));
								stopa=String.valueOf(lista.get(i).p_stopa);
							}
							
							
						}
						for(int k=0;k<porezi.size();k++) {
							porez=porez+porezi.get(k);
							
						}
						
						
						ukupan_porez=ukupan_porez+porez;
						klasa.AddSingleLineText(oznake.get(j), 30, pos, font, 9);
						klasa.AddSingleLineText("P-PDV", 70, pos, font, 9);
						klasa.AddSingleLineText(stopa + "%", 137, pos, font, 9);
						klasa.AddSingleLineText(String.valueOf(porez).format("%.2f", porez), 195, pos, font, 9);
						pos=pos-8;
						porez=0;
						porezi.clear();
						}
						klasa.AddSingleLineText("---------------------------------------------", 30, pos-8, font, 11);
						klasa.AddSingleLineText("Укупан износ пореза", 30, pos-16, font, 9);
						klasa.AddSingleLineText(String.valueOf(ukupan_porez).format("%.2f",ukupan_porez), 195, pos-16, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-24, font, 9);
						pos=pos-32;
						klasa.AddSingleLineText("ПФР време", 30, pos, font, 9);
						klasa.AddSingleLineText(now(), 145, pos, font, 9);
						klasa.AddSingleLineText("ПФР број рачуна", 30, pos-8, font, 9);
						klasa.AddSingleLineText("231T321-3213214-32132", 135, pos-8, font, 9);
						klasa.AddSingleLineText("Бројач рачуна", 30, pos-16, font, 9);
						klasa.AddSingleLineText("Касир", 30, pos-24, font, 9);
						klasa.AddSingleLineText(kasir, 175, pos-24, font, 9);
						klasa.AddSingleLineText("=======================================", 30, pos-32, font, 9);
						klasa.AddSingleLineText("ХВАЛА НА ПОСЕТИ", 85, pos-52, font, 9);
						
						
						cs.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					// TODO Auto-generated catch block
					
				
					String filepath="racun.pdf";
				
					try {
						myPdf.save(filepath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
String databaseURL="jdbc:ucanaccess://Racuni.accdb";
				
				
				try {
					Connection connection = DriverManager.getConnection(databaseURL);
				
				
				
				try{PreparedStatement st1= connection.prepareStatement("INSERT INTO Racun(Kasir,Cena,Porez,Datum) VALUES ('"+kasir+"',"+-total_refund+","+ukupan_porez+",'"+now()+"')");
					st1.executeUpdate();
					
					PreparedStatement st3= connection.prepareStatement("SELECT * FROM Racun");
					ResultSet rs =st3.executeQuery();
					int racun_id=0;
					while(rs.next()) {
					racun_id=rs.getInt(1);}
					
				for(int i=0;i<lista.size();i++) {
					PreparedStatement st2= connection.prepareStatement("INSERT INTO Artikal(Naziv,Kolicina,Artikal_Cena,Jedinica_mere,RacunID,Poreska_oznaka,Poreska_stopa) VALUES ('"+lista.get(i).stavka+"',"+lista.get(i).kolicina+","+lista.get(i).cena+",'"+lista.get(i).jedinica_mere+"',"+racun_id+",'"+lista.get(i).p_oznaka+"',"+lista.get(i).p_stopa+");");
					st2.executeUpdate();
				
				}
				
				
			
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Greska");
				}}
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnRefund.setBounds(126, 300, 89, 23);
		getContentPane().add(btnRefund);
		
	
	}
}
