import java.awt.*;
import javax.swing.*;
class MainWindow extends JFrame
{
	MainWindow()
	{
		setSize(300,300);
		getContentPane().add(new Main());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String args[])
	{
		new MainWindow();
	}
}