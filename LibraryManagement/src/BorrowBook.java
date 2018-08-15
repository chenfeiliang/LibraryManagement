import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BorrowBook extends JFrame implements ActionListener
{
    JTextField nameText = new JTextField();
    
    JTextField countText = new JTextField();
    
    JLabel nameLabel = new JLabel("所借书名 :");
    
    JLabel countLabel = new JLabel("所借数量 :");
    
    JButton yesButton = new JButton("确定");
    
    JButton noButton = new JButton("取消");
	
	
	public BorrowBook()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setVisible(true);
    	
    	this.setTitle("借书");
    	
    	this.setBounds(10, 10, 400, 300);
    	
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
    	//书名
    	nameLabel.setBounds(40, 50, 100	, 30);
    	nameText.setBounds(100, 50, 220, 30);
    	
    	//数量
    	countLabel.setBounds(40, 100, 100, 30);
    	countText.setBounds(100, 100, 220, 30);
    		
    	//确定
    	yesButton.addActionListener(this);
    	yesButton.setBounds(200, 170, 100, 30);
    	
    	//取消
    	noButton.addActionListener(this);
    	noButton.setBounds(70, 170, 100, 30);
    	
    	this.add(nameLabel);
    	this.add(nameText);
    	this.add(countLabel);
    	this.add(countText);
    	this.add(yesButton);
    	this.add(noButton);
    	
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == yesButton)
    	{
           //声明链接数据库所需资源
         	DbTools  db = new DbTools ();
         	Connection conn = db.getConn();
         	Calendar now = Calendar.getInstance();
         	
         	//获取系统的年月日，（注意，Calendar获取的月份会比实际少一）
         	int nowYear = now.get(Calendar.YEAR);
         	int nowMonth = now.get(Calendar.MONTH) +1;
         	int nowDay = now.get(Calendar.DAY_OF_MONTH);
         	
         	String ReturnDate = "";
         	int countBefore = 0;
         	int countAfter = 0;
         	int countLibrary = 0 ; //要借的那种书在书库中剩余地数量
         	
         	//借书记录标记，看本来是否有借过此书
         	boolean is_borrow = true;
         	boolean have_book = true;

    	    //获取系统的年月日，注意，获取的月份会比实际少一 

          	//规定还书期限为一个月
          	nowMonth= nowMonth + 1;
          	
          	if(nowMonth > 12)
          	{
          		nowYear++;
          	}
          	else
          	{
              	if (nowMonth > 9)
              	{
              		ReturnDate = String.valueOf(nowYear) +  String.valueOf(nowMonth) ;
              		if(nowDay>9)
              		{
              			ReturnDate += String.valueOf(nowDay);
              		}
              		else
              		{
              			ReturnDate ="0" + String.valueOf(nowDay);
              		}
              	} 
              	else
              	{
              		ReturnDate = String.valueOf(nowYear) + "0" +  String.valueOf(nowMonth) ;
              		if(nowDay>9)
              		{
              			ReturnDate += String.valueOf(nowDay);
              		}
              		else
              		{
              			ReturnDate += "0"+String.valueOf(nowDay);
              		}
              	}
          		
          	}
          	
             
         	
    		//获取书库中的库存数量
    		try
    		{
        		String sql = "select count  from  allbook  where bookName = " +"\""+nameText.getText()+"\"" ;
        		System.out.println(sql);
        		ResultSet rs = null;
    			Statement st = conn.createStatement();
    			rs = st.executeQuery(sql);
    			if(rs.next())
    			{
                    countLibrary = rs.getInt("count");
    			}
    			else
    			{
    				have_book = false;
    			}


    		}
            catch (SQLException e2) 
    		{
    			e2.printStackTrace();
    		}
    		
    		if(have_book) // 如果书库中有这本书
    		{		
    			countLibrary = countLibrary - Integer.parseInt(this.countText.getText());
    			
    			if(countLibrary>0) // 如果有足够的书籍
    			{
                 	//获取借书记录中的所借数量
            		try
            		{
                		String sql = "select count  from  borrowrecord  where name = " + "\""+Land.landUser+"\"" +   " and book = " + "\""+nameText.getText()+"\"" ;
                		System.out.println(sql);
                		ResultSet rs = null;
            			Statement st = conn.createStatement();
            			rs = st.executeQuery(sql);
            			if(rs.next())
            			{
                            countBefore = rs.getInt("count");	
            			}    			
            			else
            			{
            				is_borrow = false;
            			}
            			System.out.println("System.out.println(countBefore);" + countBefore);

            		}
                    catch (SQLException e2) 
            		{
            			e2.printStackTrace();
            		}
             		    		
                 	if(is_borrow)
                 	{		
                 		//更改借书记录
                		try
                		{
                			String sql = "update borrowrecord set count = ? ,date = ? where name = ? and book= ? " ;   //String sql = "update Student set sname=?,sex=?,age=?,hobby=?,sclass=? where id=?";

                			PreparedStatement ps = conn.prepareStatement(sql);
                			
                			System.out.println( nameText.getText());
                            
                			countAfter = countBefore + Integer.parseInt(countText.getText());
                			
                			ps.setInt(1,countAfter);
                			ps.setString(2,ReturnDate);
                            ps.setString(3, Land.landUser);
                            ps.setString(4,nameText.getText());
                            ps.executeUpdate();
                        	JOptionPane.showMessageDialog(null, "借书成功");           	
                			ps.close();

                		}
                        catch (SQLException e2) 
                		{
                			e2.printStackTrace();
                		}
                 	}
                 	else
                 	{
                 		//if是没有该书的借书记录，则插入一条新记录
                		try
                		{
                			String sql = "insert into borrowrecord(name,book,count,date)"+ " values(?,?,?,?)";
                			PreparedStatement ps = conn.prepareStatement(sql);
                			ps.setString(1, Land.landUser);
                            ps.setString(2, nameText.getText());
                            ps.setInt(3,Integer.parseInt(countText.getText()));
                            ps.setString(4, ReturnDate);
                            ps.executeUpdate();
                        	JOptionPane.showMessageDialog(null, "借书成功");     
                        	this.dispose();        	
                			ps.close();
                		}
                        catch (SQLException e2) 
                		{
                			e2.printStackTrace();
                		}
                 	}
                 	
            		//改库存记录
            		try
            		{
            			String sql = "update allbook set count = ? where bookName = ? " ;  

            			PreparedStatement ps = conn.prepareStatement(sql);
            			
            			System.out.println( nameText.getText());
                		ps.setInt(1,countLibrary);
                        ps.setString(2,nameText.getText());
                        ps.executeUpdate();   
                        this.dispose();          	
                	    ps.close();
                		conn.close();
            		}
                    catch (SQLException e2) 
            		{
            			e2.printStackTrace();
            		}
    			}
    			else
    			{
                	JOptionPane.showMessageDialog(null, "所借的书籍数量太多，书库没有足够书籍"); 
    			}

    		}
    		else
    		{
            	JOptionPane.showMessageDialog(null, "书库里无此书");  
    		}
    	}
    	
    	if(e.getSource() == noButton)
    	{
    		nameText.setText("");
    		countText.setText("");
    	}
    }
}
