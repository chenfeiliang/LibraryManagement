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
    
    Font font = new Font("����",1, 30 );
	
    JTextField userText = new JTextField(9);
    
    JPasswordField passwordText = new JPasswordField(9);  //�����ı���
    
    ButtonGroup grop = new ButtonGroup();
	JRadioButton student = new JRadioButton("");
	JRadioButton manager = new JRadioButton("");
    
    JButton yes = new JButton ("");
    
    JButton no = new JButton ("");
    
    
       
    public Land()
    {
    	init();
    	
    	this.setLayout(null);
    	
    	this.setTitle("��½����");
    	
    	this.setVisible(true);
    	
    	this.setBounds(20, 20, 919, 647);
    	
		//�ѳ��������Ļ�м�
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
        
        //�û����ı���
        userText.setBounds(285, 192, 250, 35);
        userText.setFont(font);
     	landPanel.add(userText);
    	
     	//�����ı���
     	passwordText.setBounds(285, 270, 250, 35);
     	passwordText.setFont(font);
    	landPanel.add(passwordText);
    	
    	//ȷ����ť
    	yes.setBounds(430, 345, 70, 35);
    	yes.setOpaque(false);
    	yes.setBackground(Color.black );
    	yes.addActionListener(this);
    	landPanel.add(yes);
    	
    	//ȡ����ť
    	no.setBounds(333, 345, 70, 35);
    	no.setOpaque(false);
    	no.setBackground(Color.black);
    	no.addActionListener(this);
        landPanel.add(no);
        
        //ѧ����ѡ
        student.setBounds(712, 136, 30, 30);
        student.setOpaque(false);
        student.setBackground(Color.black);
        landPanel.add(student);
        
        //����Ա��ѡ
        manager.setBounds(712, 182, 30, 30);
        manager.setOpaque(false);
        manager.setBackground(Color.black);
        landPanel.add(manager);
        
        //�趨ֻ�ܵ�ѡ
        grop.add(student);
        grop.add(manager);
        
        //����ͼλ��
        landLabel.setBounds(0, 0, 900, 600);
        landPanel.setBounds(0, 0, 900, 600);
        
        //������
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
    			JOptionPane.showMessageDialog(null, "��ѡ�����");
    		}
    		else
    		{ 
    		    String users = userText.getText();
    			String passwords = passwordText.getText();
    			boolean userFlag = false;
    			boolean passwordFlag = false;
    			
    			//�����ѧ��
				if(student.isSelected())
				{		
			    	//��ȡѧ�����ݿ��е��û���������
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
					
					//�Ա�ѧ�����ݿ�
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
	    			
	    			//�Ƿ���ڸ��û���
	        		if(userFlag)
	        		{
	        			if(passwordFlag)//�Ƿ���ڸ�����
	        			{
	        				JOptionPane.showMessageDialog(null, "��½�ɹ�");
	        				new Student_MainPanel();   //��ѧ������
	        				this.dispose();
	        			}
	        			else
	        			{
	        				JOptionPane.showMessageDialog(null, "�������");
	        			}
	        		}
	        		else
	        		{
	        			JOptionPane.showMessageDialog(null, "�˺Ŵ���");
	        		}
	    			
	    			
				}
				else if (manager.isSelected())
				{
					//��ȡ����Ա���ݿ��еĵ��û���������
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
					
					//�Աȹ���Ա���ݿ�
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
	    			
					//���Ƿ���ڸ��û���
	        		if(userFlag)
	        		{
	        			if(passwordFlag)//���Ƿ���ڸ�����
	        			{
	        				    JOptionPane.showMessageDialog(null, "��½�ɹ�");
	        					new Manager_MainPanel(); //�򿪹���Ա����
	        					this.dispose();	        				
	        			}
	        			else
	        			{
	        				JOptionPane.showMessageDialog(null, "�������");
	        			}
	        		}
	        		else
	        		{
	        			JOptionPane.showMessageDialog(null, "�˺Ŵ���");
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
