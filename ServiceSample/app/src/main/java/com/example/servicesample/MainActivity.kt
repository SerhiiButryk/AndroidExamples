/**
 * Copyright 2022. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.servicesample

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.servicesample.MyApplication.Companion.APP_TAG
import com.example.servicesample.services.BackgroundService
import com.example.servicesample.services.BackgroundService.*
import com.example.servicesample.services.ForegroundService
import com.example.servicesample.theme.AppTheme

/**
 * Activity which displays UI and starts and stops Services.
 */

private const val TAG = "$APP_TAG-MainActivity"

class MainActivity : ComponentActivity() {

    private var isServiceBound = false
    private var isBindServiceRequested = false
    private var serviceHandler: Handler? = null
    private var serviceName: ComponentName? = null

    private val backgroundServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.i(TAG, "onServiceConnected: $name")

            val serviceBinder = service as ServiceBinder
            serviceHandler = serviceBinder.getServiceHandler()

            serviceName = name
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(TAG, "onServiceDisconnected: $name")
            isServiceBound = false
            serviceHandler = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

                val context = LocalContext.current

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    /**
                     * Launch background service
                     */
                    Button(onClick = {
                        val intent = BackgroundService.getIntentForServiceLaunch(context = context)
                        startService(intent)
                    }) {
                        Text("Launch background service")
                    }

                    /**
                     * Launch foreground service
                     */
                    Button(onClick = {
                        val intent = ForegroundService.getIntentForServiceLaunch(context = context)
                        val componentName = context.startForegroundService(intent)
                        Log.i(TAG, "Launched foreground service: $componentName")
                    }) {
                        Text("Launch foreground service")
                    }

                    /**
                     * Stop foreground service
                     */
                    Button(onClick = {
                        val intent = ForegroundService.getIntentForServiceLaunch(context = context)
                        val success = stopService(intent)
                        Log.i(TAG, "Stop foreground service: $success")
                    }) {
                        Text("Start foreground service")
                    }

                    /**
                     * Stop bound service
                     */
                    Button(onClick = {
                        val intent = BackgroundService.getIntentForServiceLaunch(context = context)
                        val result = bindService(intent, backgroundServiceConnection, Context.BIND_AUTO_CREATE)
                        if (result) {
                            Log.i(TAG, "Binding is successful")
                        } else {
                            Log.e(TAG, "Binding is failed")
                        }
                        // Telling that we need to call unbind. 'Unbind' must be called in any case (whether
                        // result is true or false)
                        isBindServiceRequested = true
                    }) {
                        Text("Stop bound service")
                    }

                    /**
                     * Unbind bound service
                     */
                    Button(onClick = {
                        // If Service was not bound, 'IllegalArgumentException' exception is thrown
                        // with message - "Service not registered: ..."
                        if (isBindServiceRequested) {
                            unbindService(backgroundServiceConnection)
                            Log.i(TAG, "Unbind is called")
                        } else {
                            Log.i(TAG, "Service is not bound")
                        }
                    }) {
                        Text("Unbind bound service")
                    }

                    /**
                     * Log info
                     */
                    Button(onClick = {
                        logRunningServicesInfo()
                    }) {
                        Text("Log info")
                    }


                }
            }
        }
        Log.i(TAG, "onCreate: created - $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: destroyed - $this")
    }

    // Log currently running Services on Device
    private fun logRunningServicesInfo() {
        val activityManager = getSystemService(ACTIVITY_SERVICE)
        if (activityManager is ActivityManager) {
            // Get list of running Services on Android. Param is max number of services to return
            val runningServices = activityManager.getRunningServices(50);
            
            for (runningServiceInfo in runningServices) {
                Log.i(TAG, "logRunningServicesInfo: ${runningServiceInfo.clientLabel} " +
                        ": ${runningServiceInfo.clientPackage} : ${runningServiceInfo.service}")
            }
            
            if (runningServices.size == 0) {
                Log.i(TAG, "logRunningServicesInfo: there are no running services")
            }
        }
    }

}