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
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SelectOverdueStudent extends JFrame implements ActionListener
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
	
    JButton returnMainPanelButton = new JButton("����������");
    JButton overdueStudentButton = new JButton("��ѯ����δ����ѧ��");
	
	//���
	JLabel bookLabel = new JLabel("�����¼:");
	
	Font font = new Font("����",1, 30 );
	
     public SelectOverdueStudent()
     {
    	 init();   
    	 
    	 this.setTitle("�����¼:");
    	 
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
    	 bookLabel.setBounds(360, 5, 150,150);
    	 this.add(bookLabel);
 	  
    	 //���������水ť
    	 returnMainPanelButton.setBounds(250, 550, 150, 50);
    	 this.returnMainPanelButton.addActionListener(this);
    	 this.add(returnMainPanelButton);
    	  
    	 //��ѯ����δ����ѧ����ť
    	 overdueStudentButton.setBounds(460, 550, 200, 50);
      	 this.overdueStudentButton.addActionListener(this);
      	 this.add(overdueStudentButton);
    	  
    	 
    	 //�洢�����Ϣ�ı��
   		 
      	  //��ȡ�����Ϣ
   		 this.getData();
   		 //��ʾ��Ϣ
    	 this.selectTable();
    	 
    	 this.scrollPane.setBounds(150, 130, 600, 400);	 
    	 this.add(scrollPane);
    	 
    	 
    	 
     }
     
//��ȡ���н����¼
 public void getData()
   {
     	String sql ;
     	DbTools  db = new DbTools ();
     	Connection conn = db.getConn();
     	ResultSet rs = null;
     	
     	//��ռ���
    	idList.clear();
    	userNameList.clear();
    	bookNameList.clear();
    	dateList.clear();
    	
    	//��ȡ���н����¼
		try
		{
			Calendar now = Calendar.getInstance();
         	     		
			sql = "select id, name,book,count,date from borrowrecord";
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
 
 //��ȡ����δ����ѧ�������¼
 public void selectOverdueStudent()
 {
  	String sql ;
  	DbTools  db = new DbTools ();
  	Connection conn = db.getConn();
  	ResultSet rs = null;
  	
  	//��ռ���
	idList.clear();
	userNameList.clear();
	bookNameList.clear();
	dateList.clear();
  	
	try
	{
			
	    Calendar now = Calendar.getInstance();   
			
	    //��ȡϵͳ�������գ�ע�⣬��ȡ���·ݻ��ʵ����һ 
      	int nowYear = now.get(Calendar.YEAR);
      	int nowMonth = now.get(Calendar.MONTH) + 1 ;
      	int nowDay = now.get(Calendar.DAY_OF_MONTH);
      	String nowDate = "";         	
      	if (nowMonth > 9)
      	{
      		nowDate = String.valueOf(nowYear) +  String.valueOf(nowMonth) ;
      		if(nowDay>9)
      		{
          		nowDate += String.valueOf(nowDay);
      		}
      		else
      		{
      			nowDate ="0" + String.valueOf(nowDay);
      		}
      	} 
      	else
      	{
      		nowDate = String.valueOf(nowYear) + "0" +  String.valueOf(nowMonth) ;
      		if(nowDay>9)
      		{
          		nowDate += String.valueOf(nowDay);
      		}
      		else
      		{
      			nowDate += "0"+String.valueOf(nowDay);
      		}
      	}
			
     	//��ȡ����δ����ѧ�������¼
		sql = "select id, name,book,count,date from borrowrecord ";
		Statement st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) 
		{
			int id = rs.getInt("id");
			String userName = rs.getString("name");
			String bookName = rs.getString("book");	
			int count = rs.getInt("count");
			String date = rs.getString("date");
			
			System.out.println(Integer.parseInt(nowDate));
			System.out.println(Integer.parseInt(date));
			if(Integer.parseInt(nowDate) > Integer.parseInt(date))
			{
				idList.add(id);
				userNameList.add(userName);
				bookNameList.add(bookName);
				countList.add(count);
				dateList.add(date);
			}
		}
	}
     
	catch (SQLException e2) 
	{
			e2.printStackTrace();
	}
 }
     
 // ��ʾ�����¼
 public void selectTable() 
 {
 		table = new JTable();
 		 		
 		// JTable�ಢ�����𴢴����е����ݣ������ɱ��ģ��DefaultTableModel���𴢴�
 		// DefaultTableModel�� TableModel ��һ��ʵ�֣���ʹ��һ�� �������洢��Ԫ���ֵ����
 		
 		this.defaultModel = (DefaultTableModel) table.getModel();// ��ñ��ģ��
	
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

public void actionPerformed(ActionEvent e)
{

	
	if(e.getSource() ==this.returnMainPanelButton)
	{
		new Manager_MainPanel();
		this.dispose();
	}
	
	if(e.getSource() == this.overdueStudentButton)
	{
		this.selectOverdueStudent();
		this.selectTable();
	}
	
}
}
