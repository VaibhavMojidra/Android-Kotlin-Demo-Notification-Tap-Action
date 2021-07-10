package com.vaibhavmojidra.tapactionnotificationdemokotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.vaibhavmojidra.tapactionnotificationdemokotlin.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID="com.vaibhavmojidra"
    private val CHANNEL_NAME="com.vaibhavmojidra"
    private lateinit var notificationManagerCompat: NotificationManagerCompat
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.showNotification.setOnClickListener {
            notificationManagerCompat= NotificationManagerCompat.from(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManagerCompat.createNotificationChannel(channel)
            }
            val tapResultIntent=Intent(this,SecondActivity::class.java)//This Line for the target activity
            val pendingIntent:PendingIntent= PendingIntent.getActivity(this,0,tapResultIntent,PendingIntent.FLAG_CANCEL_CURRENT)//for setting it as pending activity
            val notification: Notification =
                NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_menu_camera)
                    .setContentTitle("Notification Title")
                    .setContentText("Notification Content Text")
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build()
            notificationManagerCompat.notify(getRandomNumber(), notification)
        }
    }

    private fun getRandomNumber(): Int {
        val dd = Date()
        val ft = SimpleDateFormat("mmssSS")
        val s: String = ft.format(dd)
        return s.toInt()
    }
}