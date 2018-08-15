import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UpdateBook extends JFrame implements ActionListener
{
    JTextField nameText = new JTextField();
    
    JTextField countText = new JTextField();
    
    JLabel idLabel = new JLabel("id :");
    
    JLabel idValueLabel = new JLabel();
    
    JLabel nameLabel = new JLabel("书名 :");
    
    JLabel countLabel = new JLabel("数量 :");
    
    JButton yesButton = new JButton("确定");
    
    JButton noButton = new JButton("取消");
    
    int id ;
    String bookName;
    int count ;
	
	public void setId(int i )
	{
	    this.id = i  ;	
	}
	
	public void setBookName(String b )
	{
	    bookName = b ;	
	}
	
	public void setCount(int c )
	{
	    count = c ;	
	}
	
	
	public UpdateBook()
    {
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
		idLabel.setBounds(66, 10, 100, 50);
		idValueLabel.setBounds(100, 10, 100, 50);
		
		idValueLabel.setText(String.valueOf(id));
		
    	nameLabel.setBounds(50, 50, 100	, 30);
    	nameText.setBounds(100, 50, 220, 30);
    	nameText.setText(bookName);
    	
    	countLabel.setBounds(50, 100, 100, 30);
    	countText.setBounds(100, 100, 220, 30);
    	countText.setText(String.valueOf(count));
    	
    	
    	
    	yesButton.addActionListener(this);
    	yesButton.setBounds(200, 170, 100, 30);
    	
    	noButton.addActionListener(this);
    	noButton.setBounds(70, 170, 100, 30);
    	
    	this.add(idLabel);
    	this.add(idValueLabel);
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
    		try
    		{
    			String sql = "update allbook set bookName=?,count=? where id=?";
    			PreparedStatement ps = conn.prepareStatement(sql);
    			ps.setString(1, nameText.getText());
    			ps.setInt(2, Integer.parseInt(countText.getText()));
    			ps.setInt(3, id);
    			ps.executeUpdate();
    			JOptionPane.showMessageDialog(null, "修改成功"); 
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
