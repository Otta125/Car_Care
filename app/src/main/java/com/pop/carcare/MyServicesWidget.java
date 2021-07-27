package com.pop.carcare;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.pop.carcare.Widget.WidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class MyServicesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String ID,int appWidgetId) {

        CharSequence widgetText = ID;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.appwidget_text, ID);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionWaterPlant(context);
    }
    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          String ID, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ID, appWidgetId);
        }
        WidgetService.startActionWaterPlant(context);
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

