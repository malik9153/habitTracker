package com.example.demo.Abstract;


public abstract class BaseEventEntity {
    protected String summary;
    protected String location;
    protected String description;

    public BaseEventEntity(String summary, String location, String description) {
        this.summary = summary;
        this.location = location;
        this.description = description;
    }
    public abstract void createEvent();
}
