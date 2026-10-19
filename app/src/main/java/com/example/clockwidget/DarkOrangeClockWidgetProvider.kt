package com.example.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class DarkOrangeClockWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, manager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { id ->
            val views = RemoteViews(context.packageName, R.layout.widget_clock)
            views.setTextViewText(R.id.widget_date, java.text.SimpleDateFormat("EEEE, dd MMM", java.util.Locale.getDefault()).format(java.util.Date()))
            manager.updateAppWidget(id, views)
        }
    }

    override fun onReceive(context: Context, intent: android.content.Intent) {
        super.onReceive(context, intent)
        if (intent.action == IntentActions.TIME_CHANGED || intent.action == IntentActions.TIMEZONE_CHANGED) {
            val manager = AppWidgetManager.getInstance(context)
            onUpdate(context, manager, manager.getAppWidgetIds(android.content.ComponentName(context, DarkOrangeClockWidgetProvider::class.java)))
        }
    }

    private object IntentActions {
        const val TIME_CHANGED = "android.intent.action.TIME_CHANGED"
        const val TIMEZONE_CHANGED = "android.intent.action.TIMEZONE_CHANGED"
    }
}