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

public class Student_MainPanel extends JFrame implements ActionListener
{
    JLabel landLabel = new JLabel(new ImageIcon("./imag/Student_MainPanel.jpg"));       
    
    JPanel landPanel = new JPanel();
    
    JButton selectButton = new JButton("");
    JButton borrow_return_Button = new JButton("");
    JButton returnButton = new JButton("");
    
    public Student_MainPanel()
    {
    	init() ;
    	
    	this.setTitle("学生主界面");
    	
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
    	
    	//确定按钮
    	selectButton.setBounds(293, 162, 459, 75);
    	selectButton.setOpaque(false);
    	selectButton.setBackground(Color.black );
    	selectButton.addActionListener(this);
    	landPanel.add(selectButton);
    	
    	//借书按钮
    	borrow_return_Button.setBounds(295, 307, 459, 75);
    	borrow_return_Button.setOpaque(false);
    	borrow_return_Button.setBackground(Color.black );
    	borrow_return_Button.addActionListener(this);
    	landPanel.add(borrow_return_Button);
    	
        //设主图位置
        landLabel.setBounds(0, 0, 900, 600);
        landPanel.setBounds(0, 0, 900, 600);
        
        //添加面板
    	landPanel.add(landLabel); 
    	this.add(landPanel);
    }
    
    
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == selectButton)
    	{
    		new Student_SelectBook();
    	//	this.dispose();	
    	}
    	
    	if(e.getSource() == borrow_return_Button)
    	{
    		new UserBorrowRecord();
    		this.dispose();
    	}
    	
    }
}
