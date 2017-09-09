import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
class FriendList extends JPanel
{
	JList list;
	String friends;
	Main main;
	FriendList(String f,Main m)
	{
		main=m;
		friends=f;
		addToList();
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse)
			{
				doValueChanged(lse);
			}
		});
	}
	void addToList()
	{
		ChatPanel t;
		//v=new Vector();
		String temp="";
		int i=0;
		while(i!=-1  && i<friends.length()-1)
		{
			if(friends.length()==1)
				break;
			temp=friends.substring(0,friends.indexOf(';',i));
			i=i+temp.length()+1;
			main.client_Obj.v.add(temp);
			t=new ChatPanel(temp,main.client_Obj,main);
			main.add(temp,t);
			main.chatPanelList.add(t);
		}
		list=new JList(main.client_Obj.v);
		//list.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
	}
	void doValueChanged(ListSelectionEvent lse)
	{
		JList src=(JList)lse.getSource();
		String item;
		int ind=src.getSelectedIndex();
		if(ind==-1)
			return;
		else
		{
			item=(String)main.client_Obj.v.elementAt(ind);
			main.cardLo.show(main,item);
		}	
	}
}