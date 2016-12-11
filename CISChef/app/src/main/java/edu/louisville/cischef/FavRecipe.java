package edu.louisville.cischef;

/**
 * Created by MAX MAN on 12/11/2016.
 */

public class FavRecipe {

    private long id;
    private String title;
    private String picture;
    private String message;
    private String author;
    private String username;

    public FavRecipe(){};

    public FavRecipe(long id, String title, String picture, String username,String message, String author){

        this.id =id;
        this.title= title;
        this.picture= picture;
        this.message= message;
        this.author=author;
        this.username=username;
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
    public String getUsername(){return username;}
    public void setUsername(String username){this.username =username;}

    @Override
    public String toString(){
        return "edu.louisville.cischef.FavRecipe{" +
                "id=" + id +
                ", Title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", Message='" + message + '\'' +
                ", Username='" + username + '\'' +
                ", Author='" + author +
                '}';
    }

}

