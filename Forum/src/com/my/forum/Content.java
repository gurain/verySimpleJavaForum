package com.my.forum;

/**
 * 内容类
 */
public class Content {
    private  String title;
    private  String publisher ;

    public String getTitle() {
        return title;
    }

    public int setTitle(String title) {
        if (title.length()<=30)
        {
            this.title = title;
            return  1;
        }
        else
        {
            return -1;
        }

    }

    public String getPublisher() {
        return publisher;
    }

    public int setPublisher(String publisher) {
        if (publisher.length()<=20)
        {
            this.publisher = publisher;
            return  1;
        }
        else
        {
            return -1;
        }
    }

    public String getCotent() {
        return cotent;
    }

    public int setCotent(String cotent) {
        if (cotent.length()<=500)
        {
            this.cotent = cotent;
            return  1;
        }
        else
        {
            return -1;
        }
    }

    public Content() {
    }

    private  String cotent;

    public Content(String title, String publisher, String cotent) {
        this.title = title;
        this.publisher = publisher;
        this.cotent = cotent;
    }
}
