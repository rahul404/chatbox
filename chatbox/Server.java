import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
class Record
{
	String name="";
	Socket s;
	Record(Socket s,String name)
	{
		this .s=s;
		this.name=name;
	}
	String getName()
	{
		return name;
	}	
	Socket getSocket()
	{	
		return s;
	}		
}
class Server
{
	Vector v;
	ServerSocket ss;
	Server()
	{
		v=new Vector();
		try
		{
			ss=new ServerSocket(6969);
			init();
		}
		catch (Exception e)
		{
			System.out.println("ERROR in Server construtor "+e);
		}
	}
	String sqlQuery(String name)
	{
		String friend="";
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con=DriverManager.getConnection("jdbc:odbc:chatbox");
			PreparedStatement ps=con.prepareStatement("update userlog set status='online' where username='"+name+"';");
			ps.executeUpdate();
			ps=con.prepareStatement("select * from dbo."+name+";");
			System.out.println("\n\nafter select statement \n\n");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				friend=friend+rs.getString("friends")+";";
				System.out.println(friend);
			}
			
		}
		catch (Exception e)
		{
			System.out.println("ERROR in sqlQuery "+e);
		}
		finally
		{
			return friend;
		}
	}
	void init()
	{
		Record r;
		String name="";
		BufferedReader br;
		Socket s;
		while(true)
		{
			try
			{
				s=ss.accept();
				br=new BufferedReader(new InputStreamReader(s.getInputStream()));
				name=br.readLine();
				System.out.println("name= "+name);
				String friends=sqlQuery(name);
				r=new Record(s,name);
				v.add(r);
				PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
				pw.println(friends);
				new SocketThread(s,v);
			}
			catch (Exception e)
			{
				System.out.println("ERROR in init of Server "+e);
			}
		}
	}
	public static void main(String args[])
	{
		new Server();
	}
}
class SocketThread extends Thread
{
	BufferedReader br;
	PrintWriter pw;
	Vector v;
	Socket s;
	SocketThread(Socket s,Vector v)
	{
		this.v=v;
		this.s=s;
		try
		{
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch (Exception e)
		{
			System.out.println("ERROR in ReadThread constructor startReading "+e);
		}
		System.out.println("inside init of SocketThread");
		start();
	}
	public void run()
	{
		startReading();
	}
	void startReading()
	{
		PrintWriter pw;
		Socket s;
		Record r;
		String to="";
		String msg="";
		while(true)
		{

			try
			{
				msg=br.readLine();
				System.out.println(msg);
				to=msg.substring(0,msg.indexOf(':'));
				System.out.println("to: "+to);
				System.out.println("vector size= "+v.size());
				for(int i=0;i<v.size();i++)
				{
					r=(Record)v.elementAt(i);
					System.out.println("test r.getName()= "+r.getName());
					System.out.println("test of if= "+to.equalsIgnoreCase(r.getName()));
					if(to.equalsIgnoreCase(r.getName()))
					{
						s=r.getSocket();
						pw=new PrintWriter(s.getOutputStream(),true);
						pw.println(msg.substring(msg.indexOf(':')+1,msg.length()));
						break;
					}
				}
			}
			catch (IOException ie)
			{
				System.out.println("IO ERROR in ReadThread startReading "+ie);
				//throw e;
			}
			catch(Exception e)
			{
				System.out.println("ERROR in ReadThread startReading "+e);
				throw e;
			}
		}
	}
	/*
	void init()
	{
		try
		{
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			new ReadThreadServer(br,v);
			pw=new PrintWriter(s.getOutputStream(),true);
		}
		catch (Exception e)
		{
			System.out.println("ERROR in SocketThread init "+e);
		}
	}*/
}
class ReadThreadServer extends Thread
{
	Vector v;
	BufferedReader br;
	ReadThreadServer(BufferedReader br,Vector v)
	{
		this.v=v;
		this.br=br;
		start();
	}
	public void run()
	{
		startReading();
	}
	void startReading()
	{
		PrintWriter pw;
		Socket s;
		Record r;
		String to="";
		String msg="";
		while(true)
		{

			try
			{
				msg=br.readLine();
				System.out.println(msg);
				to=msg.substring(msg.indexOf(':'));
				System.out.println("to: "+to);
				for(int i=0;i<v.size();i++)
				{
					r=(Record)v.elementAt(i);
					if(to.equalsIgnoreCase(r.getName()))
					{
						s=r.getSocket();
						pw=new PrintWriter(s.getOutputStream(),true);
						pw.write(msg.substring(msg.indexOf(':')+1,msg.length()));
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("ERROR in ReadThread startReading "+e);
			}
		}
	}
}