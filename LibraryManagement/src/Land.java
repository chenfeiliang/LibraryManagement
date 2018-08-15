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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Land extends JFrame implements ActionListener
{
	public static String landUser;
	
    JLabel landLabel = new JLabel(new ImageIcon("./imag/Land.jpg"));       
    
    JPanel landPanel = new JPanel();
    
    Font font = new Font("宋体",1, 30 );
	
    JTextField userText = new JTextField(9);
    
    JPasswordField passwordText = new JPasswordField(9);  //密文文本框
    
    ButtonGroup grop = new ButtonGroup();
	JRadioButton student = new JRadioButton("");
	JRadioButton manager = new JRadioButton("");
    
    JButton yes = new JButton ("");
    
    JButton no = new JButton ("");
    
    
       
    public Land()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setTitle("登陆界面");
    	
    	this.setVisible(true);
    	
    	this.setBounds(20, 20, 919, 647);
    	
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
        landPanel.setLayout(null);
        
        //用户名文本框
        userText.setBounds(285, 192, 250, 35);
        userText.setFont(font);
     	landPanel.add(userText);
    	
     	//密码文本框
     	passwordText.setBounds(285, 270, 250, 35);
     	passwordText.setFont(font);
    	landPanel.add(passwordText);
    	
    	//确定按钮
    	yes.setBounds(430, 345, 70, 35);
    	yes.setOpaque(false);
    	yes.setBackground(Color.black );
    	yes.addActionListener(this);
    	landPanel.add(yes);
    	
    	//取消按钮
    	no.setBounds(333, 345, 70, 35);
    	no.setOpaque(false);
    	no.setBackground(Color.black);
    	no.addActionListener(this);
        landPanel.add(no);
        
        //学生单选
        student.setBounds(712, 136, 30, 30);
        student.setOpaque(false);
        student.setBackground(Color.black);
        landPanel.add(student);
        
        //管理员单选
        manager.setBounds(712, 182, 30, 30);
        manager.setOpaque(false);
        manager.setBackground(Color.black);
        landPanel.add(manager);
        
        //设定只能单选
        grop.add(student);
        grop.add(manager);
        
        //设主图位置
        landLabel.setBounds(0, 0, 900, 600);
        landPanel.setBounds(0, 0, 900, 600);
        
        //添加面板
    	landPanel.add(landLabel); 
    	this.add(landPanel);
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	
    	ArrayList<String> studentUserList = new ArrayList<>();
    	ArrayList<String> studentPasswordList = new ArrayList<>();
    	ArrayList<String> managerUserList = new ArrayList<>();
    	ArrayList<String> managerPasswordList = new ArrayList<>();

    	String sql ;
    	DbTools  db = new DbTools ();
    	Connection conn = db.getConn();
    	ResultSet rs = null;
  	 	
    	if(e.getSource() == yes)
    	{
    		this.landUser = userText.getText();
    		
    		if(!student.isSelected()&& !manager.isSelected())
    		{
    			JOptionPane.showMessageDialog(null, "请选择身份");
    		}
    		else
    		{ 
    		    String users = userText.getText();
    			String passwords = passwordText.getText();
    			boolean userFlag = false;
    			boolean passwordFlag = false;
    			
    			//如果是学生
				if(student.isSelected())
				{		
			    	//获取学生数据库中的用户名，密码
					try
					{
						sql = "select user, password from studentLand";
						Statement st = conn.createStatement();
						rs = st.executeQuery(sql);
						while (rs.next()) 
						{
							String user = rs.getString("user");
							String password = rs.getString("password");		
							studentUserList.add(user);
							studentPasswordList.add(password);
						}
					}
			        catch (SQLException e2) 
					{
						e2.printStackTrace();
					}
					
					//对比学生数据库
	    			for(int i = 0 ; i< studentUserList.size(); i++)
	    			{ 				
	    				if(users.equals(studentUserList.get(i)))
	            		{
	    					userFlag = true;    
	            		}
	    				if(passwords.equals(studentPasswordList.get(i)))
	    				{
	    					passwordFlag = true;
	    				}
	    			}
	    			
	    			//是否存在该用户名
	        		if(userFlag)
	        		{
	        			if(passwordFlag)//是否存在该密码
	        			{
	        				JOptionPane.showMessageDialog(null, "登陆成功");
	        				new Student_MainPanel();   //打开学生界面
	        				this.dispose();
	        			}
	        			else
	        			{
	        				JOptionPane.showMessageDialog(null, "密码错误");
	        			}
	        		}
	        		else
	        		{
	        			JOptionPane.showMessageDialog(null, "账号错误");
	        		}
	    			
	    			
				}
				else if (manager.isSelected())
				{
					//获取管理员数据库中的的用户名，密码
					try
					{
						sql = "select user, password from managerLand";
						Statement st = conn.createStatement();
						rs = st.executeQuery(sql);
						while (rs.next()) 
						{
							String user = rs.getString("user");
							String password = rs.getString("password");		
							managerUserList.add(user);
							managerPasswordList.add(password);
						}
					}
			         catch (SQLException e2) 
					 {
						e2.printStackTrace();
					 }
					
					//对比管理员数据库
	    			for(int i = 0 ; i< managerUserList.size(); i++)
	    			{ 				
	    				if(users.equals(managerUserList.get(i)))
	            		{
	    					userFlag = true;    
	            		}
	    				if(passwords.equals(managerPasswordList.get(i)))
	    				{
	    					passwordFlag = true;
	    				}
	    			}
	    			
					//看是否存在该用户名
	        		if(userFlag)
	        		{
	        			if(passwordFlag)//看是否存在该密码
	        			{
	        				    JOptionPane.showMessageDialog(null, "登陆成功");
	        					new Manager_MainPanel(); //打开管理员界面
	        					this.dispose();	        				
	        			}
	        			else
	        			{
	        				JOptionPane.showMessageDialog(null, "密码错误");
	        			}
	        		}
	        		else
	        		{
	        			JOptionPane.showMessageDialog(null, "账号错误");
	        		}
				}
    		}
    	}
    	
    	if(e.getSource() == no)
    	{
    		userText.setText("");
    		passwordText.setText("");
    	}
    }
	
}
