package raynal.ip_finder;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	JButton myIp, findIp, additionalDetails;
	JTextArea sourceCodeArea;
	JScrollPane scrollPane;
	
	InetAddress localIp;
	URL publicIpUrl, inputUrl;
	String myPublicIp;
	
	
	public IPFinder() {
		try {   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());   }
		catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}        //Refines the look of the ui
		
		try {
			localIp = InetAddress.getLocalHost();			//address of local host 
			
			publicIpUrl = new URL("http://bot.whatismyipaddress.com");			//this website displays the system' public IP address
			BufferedReader br = new BufferedReader(new InputStreamReader(publicIpUrl.openStream()));			//to read the url	
			
			myPublicIp = br.readLine().trim();					//gets the first line of the url
			
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
		setSize(300, 320);
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
		inputName.setBounds(112, 150, 160, 25);
		inputName.setHorizontalAlignment(SwingConstants.RIGHT);
		inputName.setToolTipText("Enter URL");
		add(inputName);
		
		findIp = new JButton("Find IP");
		findIp.setBounds(145, 180, 90, 25);
		findIp.addActionListener(this);
		add(findIp);
		
		labelFindHostName = new JLabel();
		labelFindHostName.setFont(new Font("Serif", Font.PLAIN, 14));
		labelFindHostName.setHorizontalAlignment(SwingConstants.LEFT);
		labelFindHostName.setBounds(105, 210, 250, 30);
		add(labelFindHostName);
		
		labelFindAddress = new JLabel();
		labelFindAddress.setFont(new Font("Serif", Font.PLAIN, 14));
		labelFindAddress.setHorizontalAlignment(SwingConstants.LEFT);
		labelFindAddress.setBounds(105, 230, 180, 30);
		add(labelFindAddress);
		
		additionalDetails = new JButton("Additional Details");
		additionalDetails.setBounds(105, 258, 115, 20);
		additionalDetails.setHorizontalAlignment(SwingConstants.LEFT);
		additionalDetails.addActionListener(this);
		additionalDetails.setVisible(false);
		add(additionalDetails);
		
		
		sourceCodeArea = new JTextArea();
		scrollPane = new JScrollPane(sourceCodeArea);
		scrollPane.setBounds(290, 5, 290, 270);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}
	
	
	public static void main(String[] args) {
		new IPFinder();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == myIp) {
			labelLocalHostName.setText(labelLocalHostName.getText() + " " + localIp.getHostName());		 	//display system host name
			labelLocalPrivate.setText(labelLocalPrivate.getText() + " " + localIp.getHostAddress());		//display system private IP address
//			labelLocalPrivate.setText(labelLocalPrivate.getText() + " 192.168.1.104");
			labelLocalPublic.setText(labelLocalPublic.getText() + " " + myPublicIp);						//display system public IP address
//			labelLocalPublic.setText(labelLocalPublic.getText() + " 202.186.87.96");
			myIp.setEnabled(false);
		}
		
		if(e.getSource() == findIp) {
			
			if(!inputName.getText().isEmpty()) {							//having empty field returns a runtime error when fetching the url
				try {
					try {
						inputUrl = new URL(inputName.getText());			
					}
					catch(MalformedURLException ex) {
						//if the user enters only the host name eg: www.google.com, url gives error, so when catching the error, we prefix a protocol to the host name
						inputUrl = new URL("http://" + inputName.getText());		
					}
					
					InetAddress iadd = InetAddress.getByName(inputUrl.getHost());
					labelFindHostName.setText("Host Name:  " + iadd.getHostName());				//displays host name of the entered url
					labelFindAddress.setText("Address:  " + iadd.getHostAddress());				//displays IP address of the entered url
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
				
				additionalDetails.setVisible(true);
			}
		}
		
		if(e.getSource() == additionalDetails) {
			setSize(600, 320);				//window size is increased, hence displaying the text area
			int i;
			StringBuilder sb = new StringBuilder();
			
			//displaying information of the entered url
			sourceCodeArea.setText("Protocol: " + inputUrl.getProtocol() + "\nHost Name: " + inputUrl.getHost() + 
									"\nPort Number: " + inputUrl.getPort() + "\nDefault Port Number: " + inputUrl.getDefaultPort() + 
									"\nQuery: " + inputUrl.getQuery() + "\nPath: " + inputUrl.getPath() + "\nFile: " + inputUrl.getFile() + "\n\nSource Code: \n");
			
			if(inputName.getText().isEmpty()) {
				sourceCodeArea.setText(sourceCodeArea.getText() + "Please enter a webpage URL!");
			}
			else {
				try {
					/*
					 * The request property is required because while fetching source code of a url, or it might throw an error
					 * java.io.IOException: Server returned HTTP response code: 403 for URL
					 * 
					 * This implies the Server doesn't have permission to display the source code
					 * The addRequestProperty Key-value pair, give access to the requested web page.
					 */
					HttpURLConnection httpCon = (HttpURLConnection) inputUrl.openConnection(); 
					httpCon.addRequestProperty("User-Agent", "Chrome/89.0.4389.90"); 
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
					
					while((i = reader.read()) != -1) {
						
//						sourceCodeArea.setText(sourceCodeArea.getText() + (char) i);
						sb.append((char)i);
					}
					
					String sourceText = sb.toString();
					sourceCodeArea.setText(sourceCodeArea.getText() + sourceText);
				}
				catch (MalformedURLException ex) {
					ex.printStackTrace();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
