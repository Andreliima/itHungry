package ee.ut.math.tvt.itHungry;

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

//import com.jgoodies.forms.layout.CellConstraints;
//import com.jgoodies.forms.layout.FormLayout;

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
	}

	// Loading values from properties' files
	private void loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		InputStream input2 = null;
		try {
			input = new FileInputStream("application.properties");
			prop.load(input);

			team = prop.getProperty("team");
			leader = prop.getProperty("leader");
			email = prop.getProperty("email");
			member1 = prop.getProperty("member1");
			member2 = prop.getProperty("member2");
			logo = prop.getProperty("logo");
			
			input2 = new FileInputStream("version.properties");
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
			
			JPanel panel = new JPanel();
			
			add(panel);

			JLabel teamLabel = new JLabel("Team: " + team);
			JLabel leaderLabel = new JLabel("Leader: " + leader);
			JLabel emailLabel = new JLabel("E-mail: " + email);
			JLabel member1Label = new JLabel("Member 1: " + member1);
			JLabel member2Label = new JLabel("Member 2: " + member2);
			JLabel versionLabel = new JLabel("Version: " + version);
			
			//teamLabel.setAlignmentX(LEFT_ALIGNMENT);
			//leaderLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			BufferedImage logoPicture = ImageIO.read(new File(logo));
			JLabel logoLabel = new JLabel(new ImageIcon(logoPicture));

			
			
			//FormLayout layout = new FormLayout("right:pref, 7dlu","p, 1dlu");
			
//			panel.add(teamLabel, cc.xy(1,1));
//			panel.add(leaderLabel, cc.xy(2, 3));
//			panel.add(emailLabel, cc.xy(3, 3));
//			panel.add(membersLabel, cc.xy(4, 3));
//			panel.add(member1Label, cc.xy(5, 3));
//			panel.add(member2Label, cc.xy(3, 2));
//			panel.add(logoLabel, cc.xy(5,5));
			
			
			
			
			panel.add(teamLabel);
			panel.add(leaderLabel);
			panel.add(emailLabel);
			panel.add(member1Label);
			panel.add(member2Label);
			panel.add(logoLabel);
			panel.add(versionLabel);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
