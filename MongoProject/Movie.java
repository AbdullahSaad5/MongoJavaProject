package MongoProject;

public class Movie {
    String id, url, title, plot, type, votes, year, runtime, imdbRating, directors, countries, genres;

    public Movie(String id, String url, String title, String plot, String type, String votes, String year, String runtime, String imdbRating, String directors,
                 String countries, String genres){
        this.id = id;
        this.url = url;
        this.title = title;
        this.plot = plot;
        this.type = type;
        this.votes = votes;
        this.year = year;
        this.runtime = runtime;
        this.directors = directors;
        this.countries = countries;
        this.genres = genres;
        this.imdbRating = imdbRating;
    }
}
