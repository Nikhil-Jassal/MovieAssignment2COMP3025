package ca.georgian.assignment2.model;

public class Item {
    private String title;
    private String studio;
    private String rating;
    private String year;
    private String poster;
    private String description;

    public Item(String title, String studio, String rating, String year, String poster, String description) {
        this.title = title;
        this.studio = studio;
        this.rating = rating;
        this.year = year;
        this.poster = poster;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getStudio() { return studio; }
    public String getRating() { return rating; }
    public String getYear() { return year; }
    public String getPoster() { return poster; }
    public String getDescription() { return description; }
}
