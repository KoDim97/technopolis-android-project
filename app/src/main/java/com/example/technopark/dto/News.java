package com.example.technopark.dto;

public class News {
    private final String name;
    private final String title;
    private final String section;
    private final String date;
    private final String userpic;
    private final String comments_number;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUserpic() {
        return userpic;
    }

    public String getComments_number() {
        return comments_number;
    }



    public News(String name, String title, String section, String date, String userpick, String comments_number) {
        this.name = name;
        this.title = title;
        this.section = section;
        this.date = date;
        this.userpic = userpick;
        this.comments_number = comments_number;
    }
}
