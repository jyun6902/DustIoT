package com.zzj.semicircleview;

import java.util.Date;
import java.util.List;

/**
 * Created by JY on 2018-02-08.
 */

public class ResponseData {

    List<Feeds> feeds;
}

class Channel{
    int id;
    String name;
    String descpription;
    String latitude;
    String longitude;
    String field1;
    Date created_at;
    Date updated_at;
    int last_entry_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescpription() {
        return descpription;
    }

    public void setDescpription(String descpription) {
        this.descpription = descpription;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getLast_entry_id() {
        return last_entry_id;
    }

    public void setLast_entry_id(int last_entry_id) {
        this.last_entry_id = last_entry_id;
    }
}


class Feeds{
    //String created_at;
   int entry_id;
   String field1;
    // @SerializedName("entry_id") int entry_id;
    // @SerializedName("field1") String field1;

    // public int getEntry_id() {return entry_id;}
    //  public String getField1() {return field1;}
}
