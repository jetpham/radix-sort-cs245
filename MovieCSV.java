// MovieCSV.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Movie.java
class Movie {
    private String title;
    private String year;
    private String certificate;
    private String duration;
    private String genre;
    private double rating;
    private String description;
    private int votes;

    public Movie(String title, String year, String certificate, String duration, 
                String genre, double rating, String description, int votes) {
        this.title = title;
        this.year = year;
        this.certificate = certificate;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", certificate='" + certificate + '\'' +
                ", duration='" + duration + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                // ", description='" + description + '\'' +
                ", votes=" + votes +
                '}';
    }
}


public class MovieCSV {
    public static List<Movie> readMovies(String filename) {
        List<Movie> movies = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            String header = br.readLine();
            String line;
            
            while ((line = br.readLine()) != null) {
                Movie movie = parseMovieLine(line);
                if (movie != null) {
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return movies;
    }
    
    private static Movie parseMovieLine(String line) {
        try {
            List<String> fields = new ArrayList<>();
            StringBuilder currentField = new StringBuilder();
            boolean inQuotes = false;
            
            for (char c : line.toCharArray()) {
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    fields.add(currentField.toString().trim());
                    currentField = new StringBuilder();
                } else {
                    currentField.append(c);
                }
            }
            fields.add(currentField.toString().trim());

            // Remove commas from the votes string before parsing
            String votesStr = fields.get(7).replaceAll(",", "");
            
            // Skip the stars field (index 7) and adjust the votes index accordingly
            return new Movie(
                fields.get(0),                    // title
                fields.get(1),                    // year
                fields.get(2),                    // certificate
                fields.get(3),                    // duration
                fields.get(4),                    // genre
                Double.parseDouble(fields.get(5)), // rating
                fields.get(6),                    // description
                Integer.parseInt(votesStr)            // votes (index 8 since we're skipping stars)
            );
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            System.err.println("Error details: " + e.getMessage());
            return null;
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        // Test with a sample file
        List<Movie> movies = readMovies("IMDB.csv");
        System.out.println(movies.size());
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}