import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
class Main extends JPanel
{
	String name;
	Vector chatPanelList;
	MyClient client_Obj;
	CardLayout cardLo;
	Main()
	{
		chatPanelList=new Vector();
		name=getMyName();
		client_Obj=new MyClient(this,name);
		initGUI();
		setVisible(true);
	}
	String getMyName()
	{
		String n="";
		try
		{
			BufferedReader	br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter your name");
			n=br.readLine();
		}
		catch (Exception e)
		{
			System.out.println("ERROR in getName of main");
		}
		return n;
	}
	void initGUI()
	{	
		//String n=getMyName();
		//name=n;
		//setTitle(n);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(300,300);
		cardLo=new CardLayout();
		setLayout(cardLo);
		//client_Obj.send(n);
		String friends=client_Obj.receive();
		add("FriendList",new FriendList(friends,this));
		cardLo.show(this,"FriendList");
		client_Obj.startReadThread();
	}	
	public static void main(String args[])
	{
		new Main();
	}
}