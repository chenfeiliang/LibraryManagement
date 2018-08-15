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
	//声明存储信息的表格
	private JTable table;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//存储书库信息
    ArrayList<Integer> idList = new ArrayList<>();
	ArrayList<String> bookNameList = new ArrayList<>();
	ArrayList<Integer> countList =  new ArrayList<>();
	
	//组件
	JLabel bookLabel = new JLabel("现有书籍:");
	JButton addButton = new JButton("增加书籍");
	JButton deleteButton = new JButton("删除书籍");
	JButton updateButton = new JButton("更改书籍");
	JButton returnButton = new JButton("返回主界面");
	JButton reflashButton = new JButton("刷新书库");
	
	
	Font font = new Font("宋体",1, 30 );
	
     public Management_Library()
     {
    	 init();   
    	 
    	 this.setTitle("管理书库");
    	 
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
    	 
    	 /********添加组件***************/
    	 bookLabel.setFont(font);
    	 bookLabel.setBounds(380, 5, 150,150);
    	 this.add(bookLabel);
    	 
    	 //刷新按钮
    	 reflashButton.setBounds(630, 85, 100,30);
    	 reflashButton.addActionListener(this);
    	 this.add(reflashButton);
    	 
    	 //增加按钮
    	 addButton.setBounds(170, 550, 100,50);
    	 addButton.addActionListener(this);
    	 this.add(addButton);
    	 
     	//删除按钮
     	deleteButton.setBounds(300, 550, 100,50);
     	deleteButton.addActionListener(this);
     	this.add(deleteButton);
     	
     	//更改按钮
     	updateButton.setBounds(430, 550, 100,50);
     	updateButton.addActionListener(this);
     	this.add(updateButton);
     	
     	//返回按钮
     	returnButton.setBounds(560, 550, 200,50);
     	returnButton.addActionListener(this);
     	this.add(returnButton);
     	
    	 
    	 /********存储书库信息的表格***************/
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
     	
     	this.idList.clear();
     	this.bookNameList.clear();
     	this.countList.clear();
    	
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
  public  void selecttable() 
  {
 		table = new JTable();
 		
 		//获取书库信息
 		this.getData();
 		
 		// JTable类并不负责储存表格中的数据，而是由表格模型DefaultTableModel负责储存
 		// DefaultTableModel是 TableModel 的一个实现，它使用一个 集合来存储单元格的值对象
 		this.defaultModel = (DefaultTableModel) table.getModel();// 获得表格模型
 			
 		defaultModel.setRowCount(0);// 清空表格模型中的数据
 		
 		defaultModel.setColumnIdentifiers(new Object[] { "序号", "书名","数量"});// 定义表头

 		// 设置用户是否可以拖动列头，以重新排序各列
 		table.getTableHeader().setReorderingAllowed(false);
 		
		table.setModel(defaultModel);// 设置表格模型

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
			{ // 如果用户没有选择任何行，则进行提示
				JOptionPane.showMessageDialog(this, "请选择要删除的学生", "",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (row >= 0) 
			{
				int n = JOptionPane.showConfirmDialog(getContentPane(), "确认删除吗？","删除对话框", JOptionPane.YES_NO_CANCEL_OPTION);
			
				if (n == JOptionPane.YES_OPTION) 
				{
					// 如果用户确认信息
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
						JOptionPane.showMessageDialog( null, "刪除成功");
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
			{ // 如果用户没有选择任何行，则进行提示
				JOptionPane.showMessageDialog(this, "请选择要更改的学生", "",JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (row >= 0) 
			{
				UpdateBook updateBook = new  UpdateBook ();
			    // 如果用户确认信息
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
