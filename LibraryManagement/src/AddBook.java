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

public class AddBook extends JFrame implements ActionListener
{
    JTextField nameText = new JTextField();
    
    JTextField countText = new JTextField();
    
    JLabel nameLabel = new JLabel("书名");
    
    JLabel countLabel = new JLabel("数量");
    
    JButton yesButton = new JButton("确定增加");
    
    JButton noButton = new JButton("取消");
	
	
	public AddBook()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setTitle("增加书籍");
    	
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
    	//书名
    	nameLabel.setBounds(50, 50, 100	, 30);
    	nameText.setBounds(100, 50, 220, 30);
    	
    	//数量
    	countLabel.setBounds(50, 100, 100, 30);
    	countText.setBounds(100, 100, 220, 30);

    	//确定按钮
    	yesButton.addActionListener(this);
    	yesButton.setBounds(200, 170, 100, 30);
    	
    	//取消按钮
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

         	
    		try
    		{
    			String sql = "insert into allbook(bookName,count)"+ " values(?,?)";
    			PreparedStatement ps = conn.prepareStatement(sql);  			
                ps.setString(1, nameText.getText());
                ps.setInt(2,Integer.parseInt(countText.getText()));
                ps.executeUpdate();
            	JOptionPane.showMessageDialog(null, "添加成功");     
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
