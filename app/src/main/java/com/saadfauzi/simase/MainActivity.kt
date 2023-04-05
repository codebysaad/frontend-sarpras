package com.saadfauzi.simase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener OnCompleteListener@{ task ->
                if (!task.isSuccessful) {
                    Log.w("Main_Activity", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result

                Log.d("Main_Activity", token)
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            }
    }
}