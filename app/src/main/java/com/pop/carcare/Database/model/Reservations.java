package com.pop.carcare.Database.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservation")
public class Reservations {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String price;
    private String date;
    private String Phone;

    private String Lat;
    private String Long;
    private int user_id;

    public Reservations(int id, String name, String price, String date, String Phone, int user_id,
                        String Lat,String Long) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.Phone = Phone;
        this.Lat = Lat;
        this.Long = Long;
        this.user_id = user_id;
    }


    @Ignore
    public Reservations(String name, String price, String date, String Phone, int user_id,
                        String Lat,String Long ) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.Phone = Phone;
        this.Lat = Lat;
        this.Long = Long;
        this.user_id = user_id;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }


    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
