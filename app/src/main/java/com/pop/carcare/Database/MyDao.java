package com.pop.carcare.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.Database.model.Users;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    long insertUser(Users users);
    @Insert
    void insertServices(Services services);

    @Insert
    void insertReservation(Reservations reservations);


    @Query("SELECT * FROM user ")
    LiveData<List<Users>> getAllUsers();

    @Query("SELECT * FROM service ")
    LiveData<List<Services>> getAllServices();
    @Query("SELECT * FROM reservation WHERE user_id = :id ")
    LiveData<List<Reservations>> getAllReservations(int id);


    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<Users> loadUserByEmail(String email);

    @Query("SELECT * FROM user WHERE password = :password")
    LiveData<Users> loadUserByPassword(String password);


}
