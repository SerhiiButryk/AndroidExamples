package com.example.myapp2

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.myapp2.theme.AppTheme

class ExposedToWholeWorldActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Column {
                    Text(text = "ExposedToWholeWorldActivity", fontSize = 20.sp)
                }
            }
        }

        Log.i("LifecycleSample", "ExposedToWholeWorldActivity.onCreate()")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()

        isMyProcessInUIForeground()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun isMyProcessInUIForeground() {

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val appProcesses = activityManager.runningAppProcesses

        val listTasks = activityManager.appTasks
        for (task in listTasks) {

            val isRunning = task.taskInfo.isRunning
            Log.i("LifecycleSample", "isMyProcessInUIForeground: isRunning = $isRunning")
        }

        if (appProcesses != null && appProcesses.size > 0) {
            val iter = appProcesses.iterator()

            while (iter.hasNext()) {
                val appProcess = iter.next() as ActivityManager.RunningAppProcessInfo
                Log.i("LifecycleSample", "isMyProcessInUIForeground: ${appProcess.processName} ${appProcess.importance} ${appProcess.pid}")
            }
        }

    }

}