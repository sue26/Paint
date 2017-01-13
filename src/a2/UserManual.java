package a2;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserManual {
	public UserManual() {
		ImageIcon icon;
		try {
			icon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("pi.png")));
			JLabel label = new JLabel(icon);
			JFrame f = new JFrame();
			f.setTitle("Sketch Book User Manual");
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.getContentPane().add(label);
			f.pack();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			f.setLocation(dim.width ,  dim.height);
			f.setResizable(false);
		
			f.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		new UserManual();
	}
}