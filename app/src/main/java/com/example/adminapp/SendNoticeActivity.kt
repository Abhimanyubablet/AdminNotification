package com.example.adminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SendNoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_notice)

        var title=findViewById<EditText>(R.id.title)
        var descreiption=findViewById<EditText>(R.id.description)
        var  button=findViewById<Button>(R.id.sendNotification)

        val tokenList: List<String> =
            mutableListOf(
                "e4DxUA1EQUGPzUJwzT5tSs:APA91bHrIOi2q8yQwQp_DuZfOKlsOjQ72C7QfkS_Ec8m_fbUT7HzaCkZKXmaAwrhbhS2B7NQQZcbp5VudC3CL0iJT3IKwdCZcJwyuHSYXSiFA4IVHtUBWdglSxT1KW8n7_WvWEsN7pjF",
                "dB55XokqS_G5AtHAYWFtqw:APA91bH-4wKfXkx3T1f14gRnFoBLA09peFCHCd3K-9fWu_wNvO8mSxqKx34kL6LlB6BUCkQiOT8a9vX7Pn1LdisaG13Z6tVwsM-SPC5PHjZ1xznRvOQb3yMHVDOP8CktZkeGRpfd26AT"          )
        val headerMap =
            hashMapOf<String, String>("Authorization" to "key=AAAApd0otVk:APA91bF3qIbJe7pNMk25dJRpN2yIIUGE2n9aOpG1mOSVywU7RyZngtw6Ien_ABzbiugLuZfrdqUQ5zM-527ZxegYHPqHDx07qsLtcsttUrSq0eYJP1kWiYYThUCN9MSD53EU0zbJHMLy")
        button.setOnClickListener{

            val notificationData = NotificationData(
                Notification(
                    "bablet123",
                    title.text.toString(),
                    true,
                    descreiption.text.toString()
                ), tokenList
            )
            ApiCalling.Create().send(headerMap, notificationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val notification = it
                }

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()

        }
    }
}