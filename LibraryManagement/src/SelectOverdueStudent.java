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
	//声明存储信息的表格
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//存储书库信息
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> userNameList = new ArrayList<>();   
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList = new ArrayList<>();
	ArrayList<String> dateList = new ArrayList<>();
	
    JButton returnMainPanelButton = new JButton("返回主界面");
    JButton overdueStudentButton = new JButton("查询逾期未还书学生");
	
	//组件
	JLabel bookLabel = new JLabel("借书记录:");
	
	Font font = new Font("宋体",1, 30 );
	
     public SelectOverdueStudent()
     {
    	 init();   
    	 
    	 this.setTitle("借书记录:");
    	 
    	 this.setVisible(true);
    	 
    	 this.setBounds(10, 10, 900, 700);
    	 
  		//把程序放在屏幕中间
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
    	 
    	 //添加组件
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(360, 5, 150,150);
    	 this.add(bookLabel);
 	  
    	 //返回主界面按钮
    	 returnMainPanelButton.setBounds(250, 550, 150, 50);
    	 this.returnMainPanelButton.addActionListener(this);
    	 this.add(returnMainPanelButton);
    	  
    	 //查询逾期未还书学生按钮
    	 overdueStudentButton.setBounds(460, 550, 200, 50);
      	 this.overdueStudentButton.addActionListener(this);
      	 this.add(overdueStudentButton);
    	  
    	 
    	 //存储书库信息的表格
   		 
      	  //获取书库信息
   		 this.getData();
   		 //显示信息
    	 this.selectTable();
    	 
    	 this.scrollPane.setBounds(150, 130, 600, 400);	 
    	 this.add(scrollPane);
    	 
    	 
    	 
     }
     
//获取所有借书记录
 public void getData()
   {
     	String sql ;
     	DbTools  db = new DbTools ();
     	Connection conn = db.getConn();
     	ResultSet rs = null;
     	
     	//清空集合
    	idList.clear();
    	userNameList.clear();
    	bookNameList.clear();
    	dateList.clear();
    	
    	//获取所有借书记录
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
 
 //获取逾期未还书学生借书记录
 public void selectOverdueStudent()
 {
  	String sql ;
  	DbTools  db = new DbTools ();
  	Connection conn = db.getConn();
  	ResultSet rs = null;
  	
  	//清空集合
	idList.clear();
	userNameList.clear();
	bookNameList.clear();
	dateList.clear();
  	
	try
	{
			
	    Calendar now = Calendar.getInstance();   
			
	    //获取系统的年月日，注意，获取的月份会比实际少一 
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
			
     	//获取逾期未还书学生借书记录
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
     
 // 显示借书记录
 public void selectTable() 
 {
 		table = new JTable();
 		 		
 		// JTable类并不负责储存表格中的数据，而是由表格模型DefaultTableModel负责储存
 		// DefaultTableModel是 TableModel 的一个实现，它使用一个 集合来存储单元格的值对象
 		
 		this.defaultModel = (DefaultTableModel) table.getModel();// 获得表格模型
	
 		defaultModel.setRowCount(0);// 清空表格模型中的数据
 		
 		defaultModel.setColumnIdentifiers(new Object[] { "序号","学生", "书名","数量","规定还书时间"});// 定义表头

 		// 设置用户是否可以拖动列头，以重新排序各列
 		table.getTableHeader().setReorderingAllowed(false);
 		 
		table.setModel(defaultModel);// 设置表格模型

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
