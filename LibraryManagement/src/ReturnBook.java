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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ReturnBook extends JFrame implements ActionListener
{
    JTextField nameText = new JTextField();
    
    JTextField countText = new JTextField();
    
    JLabel nameLabel = new JLabel("所还书名:");
    
    JLabel countLabel = new JLabel("所还数量:");
    
    JButton yesButton = new JButton("确定");
    
    JButton noButton = new JButton("取消");
	
	
	public ReturnBook()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setVisible(true);
    	
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
    	nameLabel.setBounds(40, 50, 100	, 30);
    	nameText.setBounds(100, 50, 220, 30);
    	
    	countLabel.setBounds(40, 100, 100, 30);
    	countText.setBounds(100, 100, 220, 30);
    	
    	yesButton.addActionListener(this);
    	yesButton.setBounds(200, 170, 100, 30);
    	
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
 
         	DbTools  db = new DbTools ();
         	Connection conn = db.getConn();
         	int countBefore = 0;
         	int countAfter = 0;
         	int countLibrary = 0;
         	
         	//获取所借书的已借数目
    		try
    		{
        		String sql = "select count  from  borrowrecord  where name = " + "\""+Land.landUser+"\"" +   " and book = " + "\""+nameText.getText()+"\"" ;
        		System.out.println(sql);
        		ResultSet rs = null;
    			Statement st = conn.createStatement();
    			rs = st.executeQuery(sql);
    			rs.next();
                countBefore = rs.getInt("count");
    		}
            catch (SQLException e2) 
    		{
    			e2.printStackTrace();
    		}
    		
    		//获取书库中的库存数量
    		try
    		{
        		String sql = "select count  from  allbook  where bookName = " +"\""+nameText.getText()+"\"" ;
        		System.out.println(sql);
        		ResultSet rs = null;
    			Statement st = conn.createStatement();
    			rs = st.executeQuery(sql);
    			rs.next();
                countLibrary = rs.getInt("count");
    		}
            catch (SQLException e2) 
    		{
    			e2.printStackTrace();
    		}
         	
         	
    		//改借书记录		   		
    		try
    		{
    			String sql = "update borrowrecord set count = ? where name = ? and book = ?" ;   //String sql = "update Student set sname=?,sex=?,age=?,hobby=?,sclass=? where id=?";

    			PreparedStatement ps = conn.prepareStatement(sql);
    			
    			System.out.println( nameText.getText());
                
    			countAfter = countBefore - Integer.parseInt(countText.getText());
    			if(countAfter>=0)
    			{
        			ps.setInt(1,countAfter);
                    ps.setString(2, Land.landUser);
                    ps.setString(3,nameText.getText());
                    ps.executeUpdate();
                	JOptionPane.showMessageDialog(null, "还书成功");     
                	this.dispose();
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(null,"还书数量有误");
    			}   	
    			ps.close();
    		}
            catch (SQLException e2) 
    		{
    			e2.printStackTrace();
    		}
    		
            //删除借书数量为0的借书记录
			try 
			{
				ResultSet rs = null;
				String sql = "delete from borrowrecord where count = 0 ";
				Statement st = conn.createStatement();
				st.execute(sql);
			} 
			catch (SQLException e2) 
			{
				e2.printStackTrace();
			}
    		 		 		
    		//改库存记录
    		try
    		{
    			String sql = "update allbook set count = ? where bookName= ? " ;  

    			PreparedStatement ps = conn.prepareStatement(sql);
    			
    			System.out.println( nameText.getText());
    			countLibrary++;
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
    	
    	if(e.getSource() == noButton)
    	{
    		nameText.setText("");
    		countText.setText("");
    	}
    }
}
