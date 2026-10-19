package com.example.clockwidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextClock
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private val bg = Color.rgb(16, 16, 18)
    private val orange = Color.rgb(255, 112, 35)
    private val muted = Color.rgb(166, 160, 156)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(30), dp(24), dp(24))
            setBackgroundColor(bg)
        }
        root.addView(TextView(this).apply {
            text = "DARK ORANGE"
            setTextColor(orange)
            textSize = 12f
            letterSpacing = .22f
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
        })
        root.addView(TextView(this).apply {
            text = "Clock"
            setTextColor(Color.WHITE)
            textSize = 34f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setPadding(0, dp(4), 0, dp(20))
        })

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(20), dp(28), dp(20), dp(28))
            background = GradientDrawable().apply {
                setColor(Color.rgb(29, 27, 27))
                cornerRadius = dp(24).toFloat()
                setStroke(dp(1), Color.rgb(67, 45, 35))
            }
        }
        card.addView(TextClock(this).apply {
            format24Hour = "HH:mm"
            format12Hour = "h:mm"
            textSize = 64f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            includeFontPadding = false
        }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        card.addView(TextClock(this).apply {
            format24Hour = "EEEE, dd MMMM"
            format12Hour = "EEEE, dd MMMM"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(muted)
            setPadding(0, dp(8), 0, 0)
        })
        root.addView(card, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            bottomMargin = dp(22)
        })

        root.addView(TextView(this).apply {
            text = "YOUR HOME SCREEN"
            setTextColor(orange)
            textSize = 11f
            letterSpacing = .16f
            typeface = Typeface.DEFAULT_BOLD
        })
        root.addView(TextView(this).apply {
            text = "Keep time in view. Add the matching clock widget to your home screen for a clean, dark look with a warm orange accent."
            setTextColor(muted)
            textSize = 15f
            setLineSpacing(dp(4).toFloat(), 1f)
            setPadding(0, dp(10), 0, dp(20))
        })
        val button = Button(this).apply {
            text = "Refresh clock widget"
            isAllCaps = false
            textSize = 15f
            setTextColor(Color.WHITE)
            background = GradientDrawable().apply {
                setColor(orange)
                cornerRadius = dp(14).toFloat()
            }
            setOnClickListener {
                val manager = AppWidgetManager.getInstance(this@MainActivity)
                val ids = manager.getAppWidgetIds(ComponentName(this@MainActivity, DarkOrangeClockWidgetProvider::class.java))
                if (ids.isNotEmpty()) {
                    val update = Intent(this@MainActivity, DarkOrangeClockWidgetProvider::class.java).apply {
                        action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                        putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                    }
                    sendBroadcast(update)
                }
            }
        }
        root.addView(button, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(54)))
        root.addView(TextView(this).apply {
            text = "Tip: long-press your home screen → Widgets → Dark Orange Clock."
            setTextColor(Color.rgb(126, 121, 118))
            textSize = 12f
            setPadding(0, dp(18), 0, 0)
        })
        setContentView(root)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}