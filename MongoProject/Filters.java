package MongoProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filters extends JFrame implements ActionListener {
    public static int selectedOption = 0;
    JComboBox<String> selection;
    JButton confirm;

    public Filters(){

        setTitle("Filters");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("Filters ");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBounds(150, 20, 100, 20);
        getContentPane().add(label);

        confirm = new JButton("Confirm");
        confirm.setBounds(150, 120, 100, 20);
        confirm.addActionListener(this);
        getContentPane().add(confirm);

        String[] options = new String[]{"No Filters", "IMBD Rating", "No. of Award Wins", "Year", "Runtime", "Votes"};
        selection = new JComboBox<>(options);
        selection.setSelectedIndex(selectedOption);
        selection.setBounds(50, 70, 300, 30);
        getContentPane().add(selection);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm){
            if(selection.getSelectedItem() != null) {
                selectedOption = selection.getSelectedIndex();
                dispose();
                Template.changePanel(new ViewMovies());
            }
        }
    }
}
