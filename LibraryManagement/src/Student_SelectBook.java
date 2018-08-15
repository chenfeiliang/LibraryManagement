import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

public class Student_SelectBook extends JFrame
{
	//�����洢��Ϣ�ı��
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//�洢�����Ϣ
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList = new ArrayList<>();
	
	//���
	JLabel bookLabel = new JLabel("�����鼮:");
	
	Font font = new Font("����",1, 30 );
	
     public Student_SelectBook()
     {
    	 init();   
    	 
    	 this.setTitle("��ѯ�鼮");
    	 
    	 this.setVisible(true);
    	 
    	 this.setBounds(10, 10, 900, 700);
    	 
  		//�ѳ��������Ļ�м�
  	    Dimension  frameSize = this.getSize();
  	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   
  	    int center_X = screenSize.width/2;
  	    int center_Y = screenSize.height/2;	    
  	    this.setLocation(center_X - frameSize.width/2,center_Y-frameSize.height/2 );
    	 
    	 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     public void init()
     {
    	 this.setLayout(null);
    	 
    	 //������
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(380, 5, 150,150);
    	 this.add(bookLabel);
    	 
    	 //�洢�����Ϣ�ı��
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
 public void selecttable() 
 {
 		table = new JTable();
 		
 		//��ȡ�����Ϣ
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
 		defaultModel.setColumnIdentifiers(new Object[] { "���", "����","����"});// �����ͷ

 		// �����û��Ƿ�����϶���ͷ���������������
 		table.getTableHeader().setReorderingAllowed(false);
 		
		table.setModel(defaultModel);// ���ñ��ģ��

 		for (int i = 0; i < idList.size(); i++) {
 			System.out.println(idList.get(i));
 			System.out.println(bookNameList.get(i));
 			defaultModel.addRow(new Object[] { idList.get(i), bookNameList.get(i),countList.get(i)});
 		}	
 		scrollPane.setViewportView(table);
 	}
}
