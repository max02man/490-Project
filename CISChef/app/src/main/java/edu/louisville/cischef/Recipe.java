package edu.louisville.cischef;

/**
 * Created by Max02man on 12/2/2016.
 */

public class Recipe {
    private long id;
    private String title;
    private String picture;
    private String message;
    private String author;

    public Recipe(){};

    public  Recipe (long id, String title, String picture, String message, String author){
        this.id =id;
        this.title= title;
        this.picture= picture;
        this.message= message;
        this.author=author;
    }

    public long getId(){return id;}
    public void setId(long id){this.id =id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title =title;}
    public String getPicture(){return picture;}
    public void setPicture(String picture){this.picture =picture;}
    public String getMessage(){return message;}
    public void setMessage(String message){this.message =message;}
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author =author;}

    @Override
    public String toString(){
        return "edu.louisville.cischef.Recipe{" +
                "id=" + id +
                ", Title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", Message='" + message + '\'' +
                ", Author='" + author +
                '}';
    }

}
