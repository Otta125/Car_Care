package com.pop.carcare.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pop.carcare.Database.model.Reservations;
import com.pop.carcare.Database.model.Services;
import com.pop.carcare.Database.model.Users;

import java.util.List;

import static com.pop.carcare.Utilities.Constants.ID_INTENT;
public class MainViewModel extends AndroidViewModel {

    LiveData<List<Users>> Users;
    LiveData<List<Services>> Services;
    LiveData<List<Reservations>> Reservations;


    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Users = database.taskDao().getAllUsers();
        Services = database.taskDao().getAllServices();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplication());
        Reservations = database.taskDao().getAllReservations(prefs.getInt(ID_INTENT,0));
    }
    public LiveData<List<Users>> getUsers() {
        return Users;
    }

    public LiveData<List<Services>> getServices() {
        return Services;
    }

    public LiveData<List<Reservations>> getReservations() {
        return Reservations;
    }
}
