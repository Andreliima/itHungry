package ee.ut.math.tvt.itHungry;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument;

public class IntroUI extends JFrame {
	public IntroUI() {
		int width = 600;
		int height = 400;
		setTitle("itHungry POS");
		setSize(width, height);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screen.width - width) / 2, (screen.height - height) / 2);
	    drawText();
	}
	
	private void drawText() {
		try {
			FileReader reader = new FileReader("Intro.html");
			JEditorPane editorPane = new JEditorPane();
			editorPane.setContentType("text/html");
			try {
				editorPane.read(reader, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HTMLDocument HTMLDocument = (HTMLDocument) editorPane.getDocument();
			editorPane.setEditable(false);
		    getContentPane().add(editorPane);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
