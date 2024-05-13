package com.example.aidl_server

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aidl_server.databinding.ActivityMainBinding

const val KEY_SERVICE_STATE = "service_state_boolean"

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        pref = PreferenceManager.getDefaultSharedPreferences(this)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            startChangedStatus.setOnClickListener {
                val text = status.text
                if (text.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "status is null!", Toast.LENGTH_SHORT).show()
                } else {
                    IMyAidlInterfaceImpl.broadcastToCurrentStateToClients(text.toString().toInt())
                }
            }

            startChangedCustomRect.setOnClickListener {
                val text = status.text
                if (text.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "status is null!", Toast.LENGTH_SHORT).show()
                } else {
                    IMyAidlInterfaceImpl.broadcastToCustomRectToClients()
                }
            }
            switchServerState.setOnCheckedChangeListener(this@MainActivity)
        }
        setContentView(binding.root)

        // init bool value to MyData
        MyData.boolState = getServiceState()

        // init switch UI.
        binding.switchServerState.isChecked = getServiceState()
    }

    // saves bool value when switch state changes
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        saveServiceState(isChecked)
    }

    // save bool value in shared preference.
    private fun saveServiceState(state: Boolean) {
        MyData.boolState = state
        pref.edit().putBoolean(KEY_SERVICE_STATE, state).apply()
    }

    // get bool value from shared preference.
    private fun getServiceState(): Boolean {
        return pref.getBoolean(KEY_SERVICE_STATE, false)
    }
}