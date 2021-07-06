package MongoProject;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Template {

    public static JFrame mainFrame;
    private static JPanel currentPanel;

    public Template() {

        mainFrame = new JFrame();
        mainFrame.setTitle("IMDB");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setBounds(0, 0, 1280, 750);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));


        currentPanel = new ViewMovies();
        mainFrame.getContentPane().add(currentPanel);
        mainFrame.setVisible(true);


    }

    public static void changePanel(JPanel panel) {
        mainFrame.getContentPane().remove(currentPanel);
        currentPanel = panel;
        mainFrame.getContentPane().add(currentPanel);
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    public static void main(String[] args) {
        new Template();
    }

}

