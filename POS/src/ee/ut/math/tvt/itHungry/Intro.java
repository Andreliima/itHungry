package ee.ut.math.tvt.itHungry;

import org.apache.log4j.Logger;


public class Intro {
	
	private static final Logger log = Logger.getLogger(Intro.class);
	
	public static void main(String args[]) {
		final IntroUI userInterface = new IntroUI();
		userInterface.setResizable(false);
		userInterface.setVisible(true);
		
		log.info("Intro started");
	}
}
