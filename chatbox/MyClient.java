import java.io.*;
import java.net.*;
import java.util.*;
class MyClient 
{
	Main main;
	Vector v;
	BufferedReader br;
	PrintWriter pw;
	Socket s;
	MyClient(Main m,String n)
	{
		main=m;
		v=new Vector();
		initCom(n);
	}
	void initCom(String n)
	{
		try
		{
			s=new Socket("127.0.0.1",6969);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw=new PrintWriter(s.getOutputStream(),true);
			pw.println(n);
		}
		catch (Exception e)
		{
			System.out.println("ERROR in initCom of MyClient");
		}
	}
	void send(String msg)
	{

		try
		{
			pw.println(msg+":"+main.name);
		}
		catch (Exception e)
		{
			System.out.println("ERROR in send of MyClient");
		}
	}
	String receive()
	{
		String temp="";
		try
		{
			temp = br.readLine();
		}
		catch (Exception e )
		{
			System.out.println("ERROR in receive of MyClient");
		}
		return temp;
	}
	void startReadThread()
	{
		new ReadThread(br,main.chatPanelList);
	}
}
class ReadThread extends Thread
{
	Vector chatPanelList;
	BufferedReader br;
	ReadThread(BufferedReader b,Vector v)
	{
		br=b;
		chatPanelList=v;
		start();
	}
	public void run()
	{
		startReading();
	}
	void startReading()
	{
		int i;
		ChatPanel t=null;;
		String msg="";
		String temp="";
		while (true)
		{
			try
			{
				msg=br.readLine();
				temp=msg.substring(msg.lastIndexOf(':')+1);
				msg=msg.substring(0,msg.lastIndexOf(':'));
				for(i=0;i<chatPanelList.size();i++)
				{
					t=(ChatPanel)chatPanelList.elementAt(i);
					if(t.name.equalsIgnoreCase(temp))
					{
						t.appendToHistory("Sender:\n"+msg+"\n\n");
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("ERROR in startReading of ReadThread");
			}
		}
	}
}