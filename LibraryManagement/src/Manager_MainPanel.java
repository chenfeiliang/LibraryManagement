import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Manager_MainPanel extends JFrame implements ActionListener
{
    JLabel landLabel = new JLabel(new ImageIcon("./imag/Manager_MainPanel.jpg"));       
    
    JPanel landPanel = new JPanel();
    
    JButton overdueStudentButton = new JButton (""); //查询逾期学生
    JButton managerButton = new JButton("");         //管理书库
    
    public Manager_MainPanel()
    {
    	init() ;
    	
    	this.setTitle("管理员主界面");
    	
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
/*    	
    	//查询按钮
    	selectButton.setBounds(293, 162, 459, 75);
    	selectButton.setOpaque(false);
    	selectButton.setBackground(Color.black );
    	selectButton.addActionListener(this);
    	landPanel.add(selectButton);
    	
    	//添加按钮
    	addButton.setBounds(293, 273, 459, 75);
    	addButton.setOpaque(false);
    	addButton.setBackground(Color.black );
    	addButton.addActionListener(this);
    	landPanel.add(addButton);
    	
    	//删除按钮
    	deleteButton.setBounds(293, 387, 459, 75);
    	deleteButton.setOpaque(false);
    	deleteButton.setBackground(Color.black );
    	deleteButton.addActionListener(this);
    	landPanel.add(deleteButton);
 */ 	
    	//管理书库按钮
    	managerButton.setBounds(291, 211, 459, 75);
    	managerButton.setOpaque(false);
    	managerButton.setBackground(Color.black );
    	managerButton.addActionListener(this);
    	landPanel.add(managerButton);
    	
    	//查逾期未还书的同学按钮
    	overdueStudentButton.setBounds(293, 352, 459, 75);
    	overdueStudentButton.setOpaque(false);
    	overdueStudentButton.setBackground(Color.black );
    	overdueStudentButton.addActionListener(this);
    	landPanel.add(overdueStudentButton);

        //设主图位置
        landLabel.setBounds(0, 0, 900, 600);
        landPanel.setBounds(0, 0, 900, 600);
        
        //添加面板
    	landPanel.add(landLabel); 
    	this.add(landPanel);
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == managerButton)
    	{
    	    new Management_Library();
    		this.dispose();
    	}
    	
  	
    	if(e.getSource() == overdueStudentButton)
    	{
    		new SelectOverdueStudent();
    		this.dispose();
    	}
    }
}
