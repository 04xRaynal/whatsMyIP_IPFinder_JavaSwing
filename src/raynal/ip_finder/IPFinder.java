package raynal.ip_finder;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class IPFinder extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JLabel labelLocalHostName, labelLocalPrivate, labelLocalPublic;
	JLabel labelFindHostName, labelFindAddress;
	
	JTextField inputName;
	JButton myIp, findIp;
	
	InetAddress localIp;
	URL url;
	String myPublicIp;
	
	
	public IPFinder() {
		try {   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());   }
		catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}        //Refines the look of the ui
		
		try {
			localIp = InetAddress.getLocalHost();
			
			url = new URL("http://bot.whatismyipaddress.com");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			myPublicIp = br.readLine().trim();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Image icon = Toolkit.getDefaultToolkit().getImage("src\\resources\\connect-icon.png").getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		setIconImage(icon);
		setTitle("IP Finder");
		setLayout(null);
		setVisible(true);
		setSize(310, 320);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	
	public void createAndShowGUI() {
		labelLocalHostName = new JLabel("Your Host Name: ");
		labelLocalHostName.setFont(new Font("Serif", Font.PLAIN, 14));
		labelLocalHostName.setBounds(30, 25, 300, 20);
		add(labelLocalHostName);
		
		labelLocalPrivate = new JLabel("Your Private IP: ");
		labelLocalPrivate.setFont(new Font("Serif", Font.PLAIN, 14));
		labelLocalPrivate.setBounds(30, 45, 300, 20);
		add(labelLocalPrivate);
		
		labelLocalPublic = new JLabel("Your Public IP: ");
		labelLocalPublic.setFont(new Font("Serif", Font.PLAIN, 14));
		labelLocalPublic.setBounds(30, 65, 300, 20);
		add(labelLocalPublic); 
		
		myIp = new JButton("What's my IP ?");
		myIp.setFont(new Font("Arial", Font.ITALIC, 12));
		myIp.setBounds(30, 95, 120, 20);
		myIp.addActionListener(this);
		add(myIp);
		
		inputName = new JTextField(10);
		inputName.setBounds(100, 150, 160, 25);
		inputName.setHorizontalAlignment(SwingConstants.RIGHT);
		inputName.setToolTipText("Enter URL");
		add(inputName);
		
		findIp = new JButton("Find IP");
		findIp.setBounds(135, 180, 90, 25);
		findIp.addActionListener(this);
		add(findIp);
		
		labelFindHostName = new JLabel();
		labelFindHostName.setFont(new Font("Serif", Font.PLAIN, 14));
		labelFindHostName.setHorizontalAlignment(SwingConstants.LEFT);
		labelFindHostName.setBounds(85, 210, 250, 30);
		add(labelFindHostName);
		
		labelFindAddress = new JLabel();
		labelFindAddress.setFont(new Font("Serif", Font.PLAIN, 14));
		labelFindAddress.setHorizontalAlignment(SwingConstants.LEFT);
		labelFindAddress.setBounds(85, 230, 180, 30);
		add(labelFindAddress);
	}
	
	
	public static void main(String[] args) {
		new IPFinder();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == myIp) {
			labelLocalHostName.setText(labelLocalHostName.getText() + " " + localIp.getHostName());
			labelLocalPrivate.setText(labelLocalPrivate.getText() + " " + localIp.getHostAddress());
			labelLocalPublic.setText(labelLocalPublic.getText() + " " + myPublicIp);
			myIp.setEnabled(false);
		}
		
		if(e.getSource() == findIp) {
			String text = inputName.getText();
			try {
				InetAddress iadd = InetAddress.getByName(text);
				labelFindHostName.setText("Host Name:  " + iadd.getHostName());
				labelFindAddress.setText("Address:  " + iadd.getHostAddress());
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
