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
    
    JLabel nameLabel = new JLabel("����");
    
    JLabel countLabel = new JLabel("����");
    
    JButton yesButton = new JButton("ȷ������");
    
    JButton noButton = new JButton("ȡ��");
	
	
	public AddBook()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setTitle("�����鼮");
    	
    	this.setVisible(true);
    	
    	this.setBounds(10, 10, 400, 300);
    	
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
    	//����
    	nameLabel.setBounds(50, 50, 100	, 30);
    	nameText.setBounds(100, 50, 220, 30);
    	
    	//����
    	countLabel.setBounds(50, 100, 100, 30);
    	countText.setBounds(100, 100, 220, 30);

    	//ȷ����ť
    	yesButton.addActionListener(this);
    	yesButton.setBounds(200, 170, 100, 30);
    	
    	//ȡ����ť
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
            	JOptionPane.showMessageDialog(null, "��ӳɹ�");     
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
