package ee.ut.math.tvt.itHungry;

import java.awt.BorderLayout;
import javax.swing.BorderFactory; 
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroUI extends JFrame {

	private String team;
	private String leader;
	private String email;
	private String member1;
	private String member2;
	private String logo;
	private String version;


	public IntroUI() {
		int width = 600;
		int height = 400;
		setTitle("itHungry POS");
		setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadProperties();
		drawUI();
		this.setLocationRelativeTo(null);
	}

	// Loading values from properties' files
	private void loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		InputStream input2 = null;
		try {
			input = getClass().getResourceAsStream("/application.properties");
			if(input == null){
				input = new FileInputStream("application.properties");
			}
			prop.load(input);

			team = prop.getProperty("team");
			leader = prop.getProperty("leader");
			email = prop.getProperty("email");
			member1 = prop.getProperty("member1");
			member2 = prop.getProperty("member2");
			logo = prop.getProperty("logo");
			
			input2 = getClass().getResourceAsStream("/version.properties");
			if(input2 == null){
				input2 = new FileInputStream("version.properties");
			}
			prop.load(input2);
			
			version = prop.getProperty("build.number");
			

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (input != null || input2 != null) {
				try {
					input.close();
					input2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void drawUI() {
		try {

//			 FormLayout layout = new FormLayout(
//				      "right:pref, 6dlu, 50dlu, 4dlu, default",  // columns
//				      "pref, 3dlu, pref, 3dlu, pref");   
			
			//CellConstraints cc = new CellConstraints();
			this.setLayout(new BorderLayout());

			JLabel teamLabel = new JLabel("Team: " + team);
			teamLabel.setHorizontalAlignment(JLabel.CENTER);
			JLabel leaderLabel = new JLabel("Leader: " + leader);
			leaderLabel.setHorizontalAlignment(JLabel.CENTER);
			JLabel emailLabel = new JLabel("E-mail: " + email);
			emailLabel.setHorizontalAlignment(JLabel.CENTER);
			JLabel member1Label = new JLabel("Member 1: " + member1);
			member1Label.setHorizontalAlignment(JLabel.CENTER);
			JLabel member2Label = new JLabel("Member 2: " + member2);
			member2Label.setHorizontalAlignment(JLabel.CENTER);
			JLabel versionLabel = new JLabel("Version: " + version);
			versionLabel.setHorizontalAlignment(JLabel.CENTER);
			
			versionLabel.setSize(this.getWidth(), 16);
			
			versionLabel.setVerticalAlignment(JLabel.CENTER);
			JPanel sisu = new JPanel(new GridLayout(1,1));
			sisu.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
			JPanel panel = new JPanel();
			
			this.add(sisu, BorderLayout.CENTER);
		    this.add(versionLabel, BorderLayout.SOUTH);
		    panel.setSize(400, this.getHeight() - versionLabel.getHeight());
		    panel.setLayout(new GridLayout(0,1));
			

			ImageIcon ikoon;
			if(getClass().getClassLoader().getResource(logo) != null){
				ikoon = new ImageIcon(getClass().getClassLoader().getResource(logo));
			}
			else{
				BufferedImage logoPicture = ImageIO.read(new File(logo));
				ikoon = new ImageIcon(logoPicture);
			}
			JLabel logoLabel = new JLabel(ikoon);
			
			logoLabel.setMaximumSize(new Dimension(200, this.getHeight() - versionLabel.getHeight()));
			logoLabel.setHorizontalAlignment(JLabel.CENTER);
			logoLabel.setVerticalAlignment(JLabel.CENTER);
			
			panel.add(teamLabel);
			panel.add(leaderLabel);
			panel.add(emailLabel);
			panel.add(member1Label);
			panel.add(member2Label);
			
			sisu.add(panel);
			sisu.add(logoLabel);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
