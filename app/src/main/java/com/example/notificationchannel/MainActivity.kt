package com.example.notificationchannel

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    // Define channel IDs
    private val CHANNEL_ID_SEVERE_WEATHER = "channel_severe_weather"
    private val CHANNEL_ID_DAILY_FORECASTS = "channel_daily_forecasts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create notification channels
        createNotificationChannels()

        // Example usage: sending notifications
        val myButton = findViewById<View>(R.id.button)
        myButton.setOnClickListener {
            sendSevereWeatherNotification("Severe Weather Alert", "A tornado warning has been issued!")
        }
        val myButton2 = findViewById<View>(R.id.button2)
        myButton2.setOnClickListener {
            sendDailyForecastNotification("Daily Forecast", "Expect sunny skies with a high of 75°F.")
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Create Severe Weather Alerts channel
            val channelSevereWeather = NotificationChannel(
                CHANNEL_ID_SEVERE_WEATHER,
                "Severe Weather Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Receive alerts for severe weather conditions."
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500) // Custom vibration pattern
                setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI, null)
            }

            // Create Daily Forecasts channel
            val channelDailyForecasts = NotificationChannel(
                CHANNEL_ID_DAILY_FORECASTS,
                "Daily Forecasts",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Receive daily weather forecasts."
                enableVibration(false) // No vibration
                setSound(null, null) // No sound
            }

            // Register the channels with the system
            notificationManager.createNotificationChannel(channelSevereWeather)
            notificationManager.createNotificationChannel(channelDailyForecasts)
        }
    }

    private fun sendSevereWeatherNotification(title: String, message: String) {

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID_SEVERE_WEATHER)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notificationBuilder)
    }

    private fun sendDailyForecastNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID_DAILY_FORECASTS)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(2, notificationBuilder.build())
    }

}