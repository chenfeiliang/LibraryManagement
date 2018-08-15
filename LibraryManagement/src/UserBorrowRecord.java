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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class UserBorrowRecord extends JFrame implements ActionListener
{
	//�����洢��Ϣ�ı��
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//�洢�����Ϣ
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> userNameList = new ArrayList<>();   
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList = new ArrayList<>();
	ArrayList<String> dateList = new ArrayList<>();

	JButton reflashButton = new JButton("ˢ�¼�¼");
	JButton borrowButton = new JButton("����");
    JButton returnButton = new JButton("����");
    JButton returnMainPanelButton = new JButton("����������");
	
	//���
	JLabel bookLabel = new JLabel("�����¼:");
	
	//��������
	Font font = new Font("����",1, 30 );
	
     public UserBorrowRecord()
     {
    	 init();   
    	 
    	 this.setTitle("�����¼");
    	 
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
    	 
    	 //������
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(380, 5, 150,150);
    	 this.add(bookLabel);
    	 
    	 
    	 //ˢ�¼�¼
    	 reflashButton.setBounds(170, 550, 100, 50);
    	 reflashButton.addActionListener(this);
   	     this.add(reflashButton); 
    	 
    	 
    	 //���鰴ť
    	  borrowButton.setBounds(440, 550, 100, 50);
    	  borrowButton.addActionListener(this);
    	  this.add(borrowButton);
    	  
    	  //����
    	  returnButton.setBounds(310, 550, 100, 50);
    	  returnButton.addActionListener(this);
    	  this.add(returnButton);
    	  
    	  //���������水ť
    	  returnMainPanelButton.setBounds(580, 550, 150, 50);
    	  this.returnMainPanelButton.addActionListener(this);
    	  this.add(returnMainPanelButton);
    	 
    	 //�洢�����Ϣ�ı��
    	 this.selecttable();
    	 this.scrollPane.setBounds(150, 130, 600, 400);	 
    	 this.add(scrollPane);
    	 
    	 
    	 
     }
     
//��ȡ��½�ߵĽ����¼��Ϣ
 public void getData()
   {
	   
     	String sql ;
     	DbTools  db = new DbTools ();
     	Connection conn = db.getConn();
     	ResultSet rs = null;
     	
 		//����б�
 		idList.clear();
 		userNameList.clear();
 		bookNameList.clear();
		countList.clear();
 		dateList.clear();
     	
    	////��ȡ��½�ߵĽ����¼��Ϣ
		try
		{
			sql = "select id, name,book,count,date from borrowrecord  where name = " + "\""+Land.landUser + "\"";
			System.out.println(sql);
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) 
			{
				int id = rs.getInt("id");
				String userName = rs.getString("name");
				String bookName = rs.getString("book");	
				int count = rs.getInt("count");
			
				String date = rs.getString("date");
				idList.add(id);
				userNameList.add(userName);
				bookNameList.add(bookName);
				countList.add(count);
				dateList.add(date);
			}
		}
        catch (SQLException e2) 
		{
			e2.printStackTrace();
		}
		
   }
     
     
     
 // ��ʾ��½�ߵĽ����¼��Ϣ
 public void selecttable() 
 {
 		table = new JTable();
 			
 		//��ʾ��½�ߵĽ����¼��Ϣ
 		this.getData();
 		
 		// JTable�ಢ�����𴢴����е����ݣ������ɱ��ģ��DefaultTableModel���𴢴�
 		// DefaultTableModel�� TableModel ��һ��ʵ�֣���ʹ��һ�� �������洢��Ԫ���ֵ����
 		
 		this.defaultModel = (DefaultTableModel) table.getModel();// ��ñ��ģ��
 /*		
 		for (int i = 0; i < idList.size(); i++) {
             defaultModel.removeRow(i);
 		}
 */		
 		defaultModel.setRowCount(0);// ��ձ��ģ���е�����
 		
 		defaultModel.setColumnIdentifiers(new Object[] { "���","ѧ��", "����","����","�涨����ʱ��"});// �����ͷ

 		// �����û��Ƿ�����϶���ͷ���������������
 		table.getTableHeader().setReorderingAllowed(false);
 		 
		table.setModel(defaultModel);// ���ñ��ģ��

 		for (int i = 0; i < idList.size(); i++) 
 		{
 			System.out.println(idList.get(i));
 			System.out.println(bookNameList.get(i));
 			defaultModel.addRow(new Object[] { idList.get(i), userNameList.get(i),bookNameList.get(i),countList.get(i),dateList.get(i)});
 		}	
 		scrollPane.setViewportView(table);
 	}

@Override
public void actionPerformed(ActionEvent e)
{
	if(e.getSource() == borrowButton)
	{
		new BorrowBook();
	}
	
	if(e.getSource() == returnButton)
	{
		new ReturnBook();
	}
	
	if(e.getSource() ==this.returnMainPanelButton)
	{
		new Student_MainPanel();
		this.dispose();
	}
	
	if(e.getSource() == this.reflashButton)
	{
   	    this.selecttable();
	}
	
}
}
