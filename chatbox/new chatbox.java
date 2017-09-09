import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Chat extends JPanel
{
	String name;
	JTextArea history_TextArea;
	JTextField send_TextField;
	JButton send_Button;
	Chat(String n;)
	{
		name=n;
		setSize(300,300);
		initGUI();
	}
	void initGUI()
	{
		send_TextField=new JTextField();
		history_TextArea=new JTextArea();
		send_Button =new JButton("SEND");
		send_Button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				doAction();
			}
		});
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		p.add(send_Button,BorderLayout.EAST);
		p.add(send_TextField);
		setLayout(new BorderLayout());
		add(p,BorderLayout.SOUTH);
		JScrollPane jsp=new JScrollPane(history_TextArea);
		add(jsp);
	}
	void doAction()
	{
		String msg=send_TextField.getText();
		if(msg.length()==0)
			return;
		appendToHistory("ME:\n"+msg+"\n\n");
		//TODO add the sender field here
		client_Obj.send(n+":"+msg);
	}
	synchronized void appendToHistory(String msg)
	{
		history_TextArea.append(msg);
	}
}