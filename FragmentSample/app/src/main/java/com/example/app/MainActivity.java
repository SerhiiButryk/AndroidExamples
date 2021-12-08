/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app;

import static com.example.app.ui.GreetingsFragment.TITLE_NAME_ARG;
import static com.example.app.utils.AppUtils.APP;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.model.UserInfo;
import com.example.app.ui.EnterInfoFragment;
import com.example.app.ui.GreetingsFragment;
import com.example.app.ui.UserActionListener;
import com.example.app.ui.factory.AppViewModelFactory;
import com.example.app.ui.factory.AppFragmentFactory;
import com.example.app.viewmodel.AppViewModel;

/**
 *  App main activity.
 *
 *  1. Handles Fragment transaction
 *  2. Performs initialization
 *  3. Observes for Live Data updates
 *
 *  See Activity / Fragments lifecycle diagram - https://github.com/xxv/android-lifecycle
 */
public class MainActivity extends AppCompatActivity implements UserActionListener {

    private static final String TAG = APP + MainActivity.class.getSimpleName();

    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate() IN");

        // Prepare fragment factory.
        UserInfo userInfo = new UserInfo();
        FragmentFactory appFragmentFactory = new AppFragmentFactory(userInfo, this);

        // Factory should be set before the state of fragments are going to be restored
        // in super.onCreate() call.
        getSupportFragmentManager().setFragmentFactory(appFragmentFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure that the fragment is added only once when the activity
        // is launched the first time. When configuration is changed the fragment
        // doesn't need to be added as it is restored from the savedInstanceState Bundle.
        if (savedInstanceState == null) {

            Log.i(TAG, "onCreate() savedInstanceState == null, add fragment");

            // Prepare arguments for fragment
            Bundle arguments = new Bundle();
            arguments.putString(TITLE_NAME_ARG, getString(R.string.greetings_title));

            Log.i(TAG, "onCreate() Begin transaction");

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true) // Required by Google's docs (needed for optimization)
                    .add(R.id.fragment_container, GreetingsFragment.class, arguments, GreetingsFragment.FRAGMENT_TAG)
                    .commit();

            Log.i(TAG, "onCreate() Fragment is committed");
        }

        // Create ViewModel with state factory
        appViewModel = new ViewModelProvider(this, new AppViewModelFactory(this)).get(AppViewModel.class);

        // Attach observer to the ViewModels LiveData objects
        setObservers();

        Log.i(TAG, "onCreate() OUT");
    }

    /**
     * no-op, logging only
     *
     * Looks like it is called only the first time when activity is created
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.i(TAG, "onContentChanged()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
    }

    /**
     * no-op, logging only
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    private void setObservers() {
        // Observe when UserInfo has updated and handle notification of it
        appViewModel.getUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                // Got notification
                Log.i(TAG, "onChanged() UserInfo has updated");
            }
        });

        // Observe when User set user name and and handle notification of it.
        // Close EnterInfoFragment and reset flag.
        appViewModel.getActionFinishFlag().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    // Got notification
                    Log.i(TAG, "onChanged() User clicked OK button");

                    // Close EnterInfoFragment and go back to previous fragment
                    getSupportFragmentManager().popBackStack();

                    // Reset flag
                    appViewModel.resetActionFinishFlag();
                }

            }
        });
    }

    // Called by GreetingsFragment
    @Override
    public void onEnterInfoClicked() {

        Log.i(TAG, "onEnterInfoClicked() Add EnterInfoFragment fragment");

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true) // Required by Google's docs (needed for optimization)
                .replace(R.id.fragment_container, EnterInfoFragment.class, null, EnterInfoFragment.FRAG_TAG) // With flag
                .addToBackStack(null) // Save to fragment back stack
                .commit();
    }

}