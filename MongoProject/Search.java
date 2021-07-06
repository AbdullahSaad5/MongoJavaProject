package MongoProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search extends JFrame implements ActionListener {
    public static int selectedOption = 0;
    public static String searchText = null;
    JComboBox<String> selection;
    JButton confirm;
    JTextField keyword;
    public Search(){
        setTitle("Filters");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 400, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("Search ");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBounds(150, 20, 100, 20);
        getContentPane().add(label);


        JLabel keywordLabel = new JLabel("Keyword: ");
        keywordLabel.setHorizontalAlignment(JLabel.CENTER);
        keywordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        keywordLabel.setBounds(50, 100, 100, 20);
        getContentPane().add(keywordLabel);


        keyword = new JTextField();
        keyword.setBounds(180, 100, 150, 20);
        getContentPane().add(keyword);

        confirm = new JButton("Search");
        confirm.setBounds(150, 140, 100, 20);
        confirm.addActionListener(this);
        getContentPane().add(confirm);

        String[] options = new String[]{"No Filters", "Movie Title", "Actor Name", "Director Name", "Genres"};
        selection = new JComboBox<>(options);
        selection.setSelectedIndex(selectedOption);
        selection.setBounds(100, 60, 200, 20);
        getContentPane().add(selection);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm){
            if(selection.getSelectedItem() != null && !keyword.getText().isBlank()) {
                selectedOption = selection.getSelectedIndex();
                searchText = keyword.getText().strip();
                ViewMovies.pageNumber = 1;
            }
            else{
                selectedOption = 0;
                searchText = null;
                JOptionPane.showMessageDialog(null, "Invalid Search Parameters! Showing Movies in Normal Order");
            }
            dispose();
            Template.changePanel(new ViewMovies());
        }
    }
}
