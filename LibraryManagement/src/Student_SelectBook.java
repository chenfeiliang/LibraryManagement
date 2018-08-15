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
	//声明存储信息的表格
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//存储书库信息
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList = new ArrayList<>();
	
	//组件
	JLabel bookLabel = new JLabel("现有书籍:");
	
	Font font = new Font("宋体",1, 30 );
	
     public Student_SelectBook()
     {
    	 init();   
    	 
    	 this.setTitle("查询书籍");
    	 
    	 this.setVisible(true);
    	 
    	 this.setBounds(10, 10, 900, 700);
    	 
  		//把程序放在屏幕中间
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
    	 
    	 //添加组件
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(380, 5, 150,150);
    	 this.add(bookLabel);
    	 
    	 //存储书库信息的表格
    	 this.selecttable();
    	 this.scrollPane.setBounds(150, 130, 600, 400);	 
    	 this.add(scrollPane);
    	 
    	 
    	 
     }
     
//获取书库信息
 public void getData()
   {
     	String sql ;
     	DbTools  db = new DbTools ();
     	Connection conn = db.getConn();
     	ResultSet rs = null;
     	
    	//获取书库中信息
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
     
     
     
 // 显示书库信息
 public void selecttable() 
 {
 		table = new JTable();
 		
 		//获取书库信息
 		this.getData();
 		
 		// JTable类并不负责储存表格中的数据，而是由表格模型DefaultTableModel负责储存
 		// DefaultTableModel是 TableModel 的一个实现，它使用一个 集合来存储单元格的值对象
 		
 		this.defaultModel = (DefaultTableModel) table.getModel();// 获得表格模型
 /*		
 		for (int i = 0; i < idList.size(); i++) {
             defaultModel.removeRow(i);
 		}
 */		
 		defaultModel.setRowCount(0);// 清空表格模型中的数据
 		defaultModel.setColumnIdentifiers(new Object[] { "序号", "书名","数量"});// 定义表头

 		// 设置用户是否可以拖动列头，以重新排序各列
 		table.getTableHeader().setReorderingAllowed(false);
 		
		table.setModel(defaultModel);// 设置表格模型

 		for (int i = 0; i < idList.size(); i++) {
 			System.out.println(idList.get(i));
 			System.out.println(bookNameList.get(i));
 			defaultModel.addRow(new Object[] { idList.get(i), bookNameList.get(i),countList.get(i)});
 		}	
 		scrollPane.setViewportView(table);
 	}
}
