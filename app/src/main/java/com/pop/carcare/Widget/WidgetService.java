package com.pop.carcare.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.pop.carcare.MyServicesWidget;
import com.pop.carcare.R;
import com.pop.carcare.Utilities.Constants;

import androidx.annotation.Nullable;

public class WidgetService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT_WIDGETS =
            "com.pop.carcare.Widget";


    public WidgetService() {
        super("name");
    }
    public static void startActionWaterPlant(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_WIDGETS);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENT_WIDGETS.equals(action)) {
                handleUpdateWidget();
            }
        }
    }

    private void handleUpdateWidget() {

        SharedPreferences prefs = getSharedPreferences(Constants.Recipe_ID_FOR_Widget, MODE_PRIVATE);
        String name = prefs.getString(Constants.Recipe_ID_FOR_Widget, getString(R.string.app_name));//"No name defined" is the default value.
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetids = appWidgetManager.getAppWidgetIds(new ComponentName(this, MyServicesWidget.class));
        MyServicesWidget.updatePlantWidgets(this, appWidgetManager, name, appWidgetids);
    }

}