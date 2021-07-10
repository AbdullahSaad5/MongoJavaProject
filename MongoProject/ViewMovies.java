package MongoProject;

import com.mongodb.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;

public class ViewMovies extends JPanel implements ActionListener {
    public static int pageNumber = 1, totalPages = 1, totalCount = 0;
    JLabel pageCount;
    static ArrayList<Movie> array = new ArrayList<>();
    JButton next, prev;
    private final JButton[] buttons = new JButton[2];
    JButton sort, search;

    public ViewMovies() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 1280, 650);
        setLayout(null);

        next = new JButton("");
        next.setFocusable(false);
        next.addActionListener(this);
        next.setIcon(new ImageIcon(Objects.requireNonNull(ViewMovies.class.getResource("/images/next.png"))));
        next.setBackground(null);
        next.setBounds(735, 10, 30, 30);
        add(next);

        prev = new JButton("");
        prev.setFocusable(false);
        prev.addActionListener(this);
        prev.setIcon(new ImageIcon(Objects.requireNonNull(ViewMovies.class.getResource("/images/prev.png"))));
        prev.setBackground(null);
        prev.setBounds(515, 10, 30, 30);
        add(prev);

        pageCount = new JLabel("");
        pageCount.setHorizontalAlignment(SwingConstants.CENTER);
        pageCount.setFont(new Font("SansSerif", Font.BOLD, 14));
        pageCount.setBounds(560, 5, 144, 40);
        add(pageCount);

        sort = new JButton("Sort");
        sort.setFocusable(false);
        sort.setBounds(20, 50, 100, 20);
        sort.addActionListener(this);
        add(sort);

        search = new JButton("Search");
        search.setFocusable(false);
        search.setBounds(20, 100, 100, 20);
        search.addActionListener(this);
        add(search);

        array.removeAll(array);
        BasicDBObject sort = null;
        if(Filters.selectedOption > 0){
            int option = Filters.selectedOption;
            if(option == 1){
                sort = new BasicDBObject("imdb.rating", 1);
            }
            else if(option == 2){
                sort = new BasicDBObject("awards.wins", 1);
            }
            else if(option == 3){
                sort = new BasicDBObject("year", 1);
            }
            else if(option == 4){
                sort = new BasicDBObject("runtime", 1);
            }
            else if(option == 5){
                sort = new BasicDBObject("votes", 1);
            }
        }

        BasicDBObject search = null;
        if(Search.selectedOption > 0 && Search.searchText != null){
            int option = Search.selectedOption;
            String parameters = Search.searchText;
            if(option == 1){
//                search = new BasicDBObject("title", "/" + parameters + "/");
                search = new BasicDBObject("title", new BasicDBObject("$regex", parameters + ".*").append("$options", "i"));
            }
            else if(option == 2){
                search = new BasicDBObject("cast", new BasicDBObject("$in", Arrays.asList(parameters)));
            }
            else if(option == 3){
                search = new BasicDBObject("directors", new BasicDBObject("$in", Arrays.asList(parameters)));
            }
            else if(option == 4){
                search = new BasicDBObject("genres", new BasicDBObject("$in", Arrays.asList(parameters)));
            }
        }

        try {
            MongoClient mongoclient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB myDB = mongoclient.getDB("testdb");
            DBCollection myCollection = myDB.getCollection("movies");
            DBCursor cursor = myCollection.find(search).sort(sort);
            while (cursor.hasNext()) {
                try {
                    DBObject curr = cursor.next();
                    getMovieDetails(curr);

                } catch (java.lang.NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        totalCount = array.size();
        if (totalCount % 2 != 0) {
            totalPages = (totalCount / 2) + 1;
        } else {
            totalPages = (totalCount / 2);
        }
        if(totalPages == 0){
            totalPages = 1;
        }
        pageCount.setText(( "Page: " + pageNumber + "/" + totalPages));
        viewMovies();
    }

    public JPanel createMoviePreview(String posterURL, String title, String year, String runtime, String plot, String type,
                                   String directors, String imdbRating, String votes, String countries,
                                   String genres) {

        JPanel moviePanel = new JPanel();
        moviePanel.setBackground(Color.WHITE);
        moviePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        moviePanel.setBounds(0, 0, 540, 640);
        add(moviePanel);
        moviePanel.setLayout(null);

        JLabel moviePosterLabel = new JLabel("");
        moviePosterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moviePosterLabel.setBackground(Color.LIGHT_GRAY);
        moviePosterLabel.setBounds(195, 12, 150, 250);
        moviePanel.add(moviePosterLabel);

        JLabel movieTitleLabel = new JLabel("<html><p style='text-align: center'>" + title + "</p></html>");
        movieTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        movieTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movieTitleLabel.setBounds(12, 282, 516, 50);
        moviePanel.add(movieTitleLabel);

        JLabel releaseYearLabel = new JLabel("Release Year: " + year);
        releaseYearLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        releaseYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        releaseYearLabel.setBounds(12, 343, 516, 15);
        moviePanel.add(releaseYearLabel);

        JLabel runTimeLabel = new JLabel("RunTime: " + runtime);
        runTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        runTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        runTimeLabel.setBounds(12, 583, 516, 15);
        moviePanel.add(runTimeLabel);

        JLabel plotLabel = new JLabel("<html><p style='text-align: center'> Plot: " + plot + "</p></html>");
        plotLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        plotLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        plotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        plotLabel.setBounds(12, 437, 516, 50);
        moviePanel.add(plotLabel);

        JLabel typeLabel = new JLabel("Type: " + type);
        typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeLabel.setBounds(12, 416, 516, 15);
        moviePanel.add(typeLabel);

        JLabel imdbRatingLabel = new JLabel("IMDB Rating: " + imdbRating);
        imdbRatingLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        imdbRatingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imdbRatingLabel.setBounds(12, 548, 260, 15);
        moviePanel.add(imdbRatingLabel);

        JLabel votesLabel = new JLabel("Votes: " + votes);
        votesLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        votesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        votesLabel.setBounds(268, 548, 260, 15);
        moviePanel.add(votesLabel);

        JLabel directorsLabel = new JLabel("Directors: " + directors);
        directorsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        directorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        directorsLabel.setBounds(12, 513, 516, 15);
        moviePanel.add(directorsLabel);

        JLabel countriesLabel = new JLabel("Countries: " + countries);
        countriesLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        countriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countriesLabel.setBounds(12, 618, 516, 15);
        moviePanel.add(countriesLabel);

        JLabel genresLabel = new JLabel("Genres: " + genres);
        genresLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        genresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        genresLabel.setBounds(12, 378, 516, 15);
        moviePanel.add(genresLabel);

        URL url;
        try {
            // Poster url
            url = new URL(posterURL);
            // Getting poster image using URL and resizing it to the size of JLabel
            Image image = ImageIO.read(url).getScaledInstance(moviePosterLabel.getWidth(),
                    moviePosterLabel.getHeight(), Image.SCALE_SMOOTH);
            moviePosterLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            System.out.println("Couldn't fetch Movie Poster");
        }
        return moviePanel;
    }

    public void viewMovies() {
        for (int i = 0; i < 2; i++) {
            int index = ((pageNumber - 1) * 2) + i;
            if (index < totalCount) {
                Movie curr = array.get(index);
                JPanel panel = createMoviePreview(curr.url, curr.title, curr.year, curr.runtime, curr.plot, curr.type,
                        curr.directors, curr.imdbRating, curr.votes, curr.countries,
                        curr.genres);
                if (index % 2 == 0) {
                    panel.setBounds(150, 50, 540, 640);
                    createButton(0);
                    panel.add(buttons[0]);
                }
                if (index % 2 == 1) {
                    panel.setBounds(720, 50, 540, 640);
                    createButton(1);
                    panel.add(buttons[1]);
                }
            } else {
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next || e.getSource() == prev) {
            if (e.getSource() == next && pageNumber < totalPages) {
                pageNumber++;
            } else if (e.getSource() == prev && pageNumber > 1) {
                pageNumber--;
            }
            Template.changePanel(new ViewMovies());
            viewMovies();
        } else if (e.getSource() == buttons[0]) {
            int index = (pageNumber - 1) * 2;
            System.out.println(array.get(index).id);
            new Comments(array.get(index).id);
        } else if (e.getSource() == buttons[1]) {
            int index = (pageNumber - 1) * 2 + 1;
            new Comments(array.get(index).id);
        } else if(e.getSource() == sort){
            new Filters();
        }else if(e.getSource() == search){
            new Search();
        }else {
            System.out.println(e.getID());
        }
    }

    public void getMovieDetails(DBObject curr){
        String id, url, title, year, runtime, plot, type, directors, imdbRating2, votes, countries, genres;
        Object temp;

        id = curr.get("_id").toString();
        temp = curr.get("poster");
        if(temp != null) {
            url =temp.toString();
        }
        else {
            url = "https://images.wondershare.com/pdfelement/forms-templates/medium/movie-poster-template-3.png";
        }

        temp = curr.get("title");
        if(temp != null) {
            title = temp.toString();
        }
        else {
            title = "N/A";
        }
        temp = curr.get("year");
        if(temp != null) {
            year = temp.toString();
        }
        else{
            year = "N/A";
        }

        temp = curr.get("runtime");
        if(temp != null){
        runtime = temp + " minutes";
        }
        else{
            runtime = "N/A";
        }

        temp = curr.get("plot");
        if(temp != null) {
            plot = temp.toString();
        }
        else{
            plot = "N/A";
        }

        temp = curr.get("type");
        if(temp != null) {
            type = temp.toString();
        }
        else{
            type = "N/A";
        }

        temp = curr.get("directors");
        if(temp != null) {
            directors = temp.toString();
            directors = directors.substring(2, directors.length() - 1);
        }
        else{
            directors = "N/A";
        }
        DBObject imdb = (DBObject) curr.get("imdb");
        if(imdb != null) {
            String rating = imdb.get("rating").toString();
            if(rating.length() > 1) {
                DBObject imdbRating = (DBObject) imdb.get("rating");
                imdbRating2 = imdbRating.get("$numberDouble").toString();
            } else {
                imdbRating2 = imdb.get("rating").toString();
            }
        }
        else{
            imdbRating2 = "N/A";
        }
        temp = curr.get("votes");
        if( temp != null) {
            votes = temp.toString();
        }
        else{
            votes = "N/A";
        }

        temp = curr.get("countries");
        if( temp != null) {
            countries = temp.toString();
        }
        else{
            countries = "N/A";
        }

        temp = curr.get("genres");
        if(temp != null) {
            genres = temp.toString();
        }else{
            genres = "N/A";
        }

        Movie movie = new Movie(id, url, title, plot, type, votes, year, runtime, imdbRating2, directors, countries, genres);
        array.add(movie);
    }


    public void createButton(int index){
        buttons[index] = new JButton("View Comments");
        buttons[index].setFocusable(false);
        buttons[index].addActionListener(this);
        buttons[index].setFont(new Font("SansSerif", Font.PLAIN, 10));
        buttons[index].setBounds(415, 612, 120, 23);
    }
}
