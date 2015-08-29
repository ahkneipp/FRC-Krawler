package com.team2052.frckrawler.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MATCH".
 */
public class Match implements java.io.Serializable {

    private Long id;
    private Integer number;
    private String key;
    private long event_id;
    private String data;
    private String type;

    public Match() {
    }

    public Match(Long id) {
        this.id = id;
    }

    public Match(Long id, Integer number, String key, long event_id, String data, String type) {
        this.id = id;
        this.number = number;
        this.key = key;
        this.event_id = event_id;
        this.data = data;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
