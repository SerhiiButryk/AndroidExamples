package com.example.compose.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.test.di.DaggerEngineProvider
import com.example.compose.test.ui.SampleUI
import com.example.compose.test.ui.theme.AppTheme

/**
 * Sample Activity with Compose UI
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                SampleUI()
            }
        }
        /**
         * Uncomment to run Dagger generated code
         */
        //testDependencyInjection()
    }

    /**
     * Dependency injection testing code
     */
    fun testDependencyInjection() {

        val provider = DaggerEngineProvider.create()
        val engine = provider.getEngine()
        engine.work()

        val engine2 = provider.getComplexEngine()
        engine2.work()
    }

}