import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Management_Library extends JFrame implements ActionListener
{
	//�����洢��Ϣ�ı��
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//�洢�����Ϣ
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList =  new ArrayList<>();
	
	//���
	JLabel bookLabel = new JLabel("�����鼮:");
	JButton addButton = new JButton("�����鼮");
	JButton deleteButton = new JButton("ɾ���鼮");
	JButton updateButton = new JButton("�����鼮");
	JButton returnButton = new JButton("����������");
	JButton reflashButton = new JButton("ˢ�����");
	
	
	Font font = new Font("����",1, 30 );
	
     public Management_Library()
     {
    	 init();   
    	 
    	 this.setTitle("�������");
    	 
    	 this.setVisible(true);
    	     	 	
    	 this.setBounds(10, 10, 900, 700);
    	 
  		//�ѳ��������Ļ�м�
  	    Dimension  frameSize = this.getSize();
  	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
  	    int center_X = screenSize.width/2;
  	    int center_Y = screenSize.height/2;	    
  	    this.setLocation(center_X - frameSize.width/2,center_Y-frameSize.height/2 );
    	 
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     
     public void init()
     {
    	 this.setLayout(null);
    	 
    	 /********������***************/
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(380, 5, 150,150);
    	 this.add(bookLabel);
    	 
    	 //ˢ�°�ť
    	 reflashButton.setBounds(630, 85, 100,30);
    	 reflashButton.addActionListener(this);
    	 this.add(reflashButton);
    	 
    	 //���Ӱ�ť
    	 addButton.setBounds(170, 550, 100,50);
    	 addButton.addActionListener(this);
    	 this.add(addButton);
    	 
     	//ɾ����ť
     	deleteButton.setBounds(300, 550, 100,50);
     	deleteButton.addActionListener(this);
     	this.add(deleteButton);
     	
     	//���İ�ť
     	updateButton.setBounds(430, 550, 100,50);
     	updateButton.addActionListener(this);
     	this.add(updateButton);
     	
     	//���ذ�ť
     	returnButton.setBounds(560, 550, 200,50);
     	returnButton.addActionListener(this);
     	this.add(returnButton);
     	
    	 
    	 /********�洢�����Ϣ�ı��***************/
    	 this.selecttable();
    	 this.scrollPane.setBounds(150, 130, 600, 400);	 
    	 this.add(scrollPane);
    	 
    	 
    	 
     }
     
//��ȡ�����Ϣ
 public void getData()
   {
     	String sql ;
     	DbTools  db = new DbTools ();
     	Connection conn = db.getConn();
     	ResultSet rs = null;
     	
     	this.idList.clear();
     	this.bookNameList.clear();
     	this.countList.clear();
    	
    	//��ȡ�������Ϣ
		try
		{
			sql = "select id, bookName,count from allbook";
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String bookName = rs.getString("bookName");		
				int count = rs.getInt("count");
				idList.add(id);
				bookNameList.add(bookName);
				countList.add(count);
			}
		}
        catch (SQLException e2) 
		{
			e2.printStackTrace();
		}
		
   }
     
     
     
  // ��ʾ�����Ϣ
  public  void selecttable() 
  {
 		table = new JTable();
 		
 		//��ȡ�����Ϣ
 		this.getData();
 		
 		// JTable�ಢ�����𴢴����е����ݣ������ɱ��ģ��DefaultTableModel���𴢴�
 		// DefaultTableModel�� TableModel ��һ��ʵ�֣���ʹ��һ�� �������洢��Ԫ���ֵ����
 		this.defaultModel = (DefaultTableModel) table.getModel();// ��ñ��ģ��
 			
 		defaultModel.setRowCount(0);// ��ձ��ģ���е�����
 		
 		defaultModel.setColumnIdentifiers(new Object[] { "���", "����","����"});// �����ͷ

 		// �����û��Ƿ�����϶���ͷ���������������
 		table.getTableHeader().setReorderingAllowed(false);
 		
		table.setModel(defaultModel);// ���ñ��ģ��

 		for (int i = 0; i < idList.size(); i++) {			
 			defaultModel.addRow(new Object[] { idList.get(i), bookNameList.get(i),countList.get(i)});
 		}	
 		scrollPane.setViewportView(table);
 }
  
  public void actionPerformed(ActionEvent e)
  {
	  if(e.getSource() == addButton)
	  {
		  new AddBook();
	  }
	  
	  if(e.getSource() == deleteButton)
	  {
			int row = table.getSelectedRow();
			
			if (row == -1) 
			{ // ����û�û��ѡ���κ��У��������ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ����ѧ��", "",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (row >= 0) 
			{
				int n = JOptionPane.showConfirmDialog(getContentPane(), "ȷ��ɾ����","ɾ���Ի���", JOptionPane.YES_NO_CANCEL_OPTION);
			
				if (n == JOptionPane.YES_OPTION) 
				{
					// ����û�ȷ����Ϣ
					int id = Integer.parseInt(table.getValueAt(row, 0)
							.toString() );
					String sql = "delete from allbook where id =" + id;
					DbTools db = new DbTools();
					Connection conn = db.getConn();
					ResultSet rs = null;
					try 
					{
						Statement st = conn.createStatement();
						st.execute(sql);
						JOptionPane.showMessageDialog( null, "�h���ɹ�");
						this.selecttable();

					} 
					catch (SQLException e2) 
					{
						e2.printStackTrace();
					}

				}
			}
		  
			
	  }
	  
	  if(e.getSource() == updateButton)
	  {
			int row = table.getSelectedRow();
			
			if (row == -1) 
			{ // ����û�û��ѡ���κ��У��������ʾ
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���ĵ�ѧ��", "",JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (row >= 0) 
			{
				UpdateBook updateBook = new  UpdateBook ();
			    // ����û�ȷ����Ϣ
			    int id = Integer.parseInt(table.getValueAt(row, 0).toString() );
				String bookName = table.getValueAt(row, 1).toString();
				int count = Integer.parseInt(table.getValueAt(row, 2).toString());
				System.out.println(id + " " + bookName + " " + count );
				updateBook.setId(id);
				updateBook.setBookName(bookName);
				updateBook.setCount(count);
				updateBook.init();
			}

	  }
	  
	  if(e.getSource() == returnButton)
	  {
		  new Manager_MainPanel();
		  this.dispose();
	  }
	  
	  if(e.getSource() == this.reflashButton)
	  {
		  this.selecttable();
	  }
  }
}
