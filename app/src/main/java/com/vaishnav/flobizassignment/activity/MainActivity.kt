package com.vaishnav.flobizassignment.activity

import android.content.*
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaishnav.flobizassignment.databinding.ActivityMainBinding
import com.vaishnav.flobizassignment.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    // instantiated shared preference variable
    companion object {
        const val PREFS_KEY_IS_AD_CANCELLED = "isAdCancelled"
        const val PREFS_FILE_AUTH = "prefs_auth"
    }
    // variables
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var isAdCancelled: Boolean = false
    private lateinit var adapter: Adapter

    // declaring view bindings
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setting up views
        setUpListener()
        setUpPreferences()
        setUpBroadCastReceiver()
    }

    private fun setUpListener() {
        binding.cancelBtn.setOnClickListener {
            binding.cancelBtn.visibility = View.INVISIBLE
            binding.imageView.visibility = View.INVISIBLE
            sharedPreferences.edit {
                putBoolean(PREFS_KEY_IS_AD_CANCELLED, true)
            }
        }
    }

    private fun setUpPreferences() {
        sharedPreferences.getBoolean(PREFS_KEY_IS_AD_CANCELLED, false).let {
            isAdCancelled = it
        }

        if (isAdCancelled) {
            binding.cancelBtn.visibility = View.INVISIBLE
            binding.imageView.visibility = View.INVISIBLE
        }
    }
    // handling network errors
    private fun setUpBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val notConnected =
                    intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                if (notConnected) {
                    if(mainActivityViewModel.data.value == null){
                        disconnected()
                    }
                } else {
                    if(mainActivityViewModel.data.value == null){
                        connected()
                    }
                }
            }
        }
    }
    // called when connected to network
    private fun connected() {
        adapter = Adapter()
        mainActivityViewModel.getMyData()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        mainActivityViewModel.data.observe({ lifecycle }) {
            binding.progressBar.visibility = View.VISIBLE
            adapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }
    }
    // called when not connected to network
    private fun disconnected() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
    // important to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
