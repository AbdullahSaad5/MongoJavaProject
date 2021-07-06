package MongoProject;

import com.mongodb.*;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;

public class Comments  {
    JFrame mainFrame;
    JTextArea textArea;
    JScrollPane mainWindow;
    public Comments(String id){
        mainFrame = new JFrame();
        mainFrame.setTitle("Filters");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setBounds(0, 0, 700, 700);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setLayout(null);

        JLabel label = new JLabel("Comments ");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBounds(0, 20, 700, 20);
        mainFrame.getContentPane().add(label);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(20, 0, 660, 650);

        mainWindow = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainWindow.getVerticalScrollBar().setBackground(new Color(0x1E214A));
        mainWindow.getBackground().darker();
        mainWindow.setBounds(0, 50, 700, 650);
        mainFrame.getContentPane().add(mainWindow);

        try {
            MongoClient mongoclient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB myDB = mongoclient.getDB("testdb");
            DBCollection myCollection = myDB.getCollection("comments");
            BasicDBObject query = new BasicDBObject("movie_id", new ObjectId(id));
            DBCursor cursor = myCollection.find(query);
            while (cursor.hasNext()) {
                try {
                    DBObject curr = cursor.next();
                    getComment(curr);
                } catch (java.lang.NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainFrame.setVisible(true);
    }

    public void getComment(DBObject curr){
        String comment = "";
        Object temp;

        temp = curr.get("name");
        if(temp != null){
            comment += "Name: " + temp.toString() + "\n";
        }
        else{
            comment += "Name: N/A \n";
        }

        temp = curr.get("email");
        if(temp != null){
            comment += "Email: " + temp.toString() + "\n";
        }
        else{
            comment += "Email: N/A \n";
        }

        temp = curr.get("text");
        if(temp != null){
            comment += "Comment: " + temp.toString() + "\n";
        }
        else{
            comment += "Comment: N/A \n";
        }

        comment += "\t\t\t-------------------------------------------------\n\n";

        textArea.append(comment);
    }
}
