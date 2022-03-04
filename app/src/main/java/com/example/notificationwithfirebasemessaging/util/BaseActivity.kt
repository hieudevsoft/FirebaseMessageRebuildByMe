package com.example.notificationwithfirebasemessaging.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V:ViewBinding>: AppCompatActivity() {
    private var _binding:V?=null
    protected val binding get() = _binding!!
    abstract fun onSetupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getBinding()
        setContentView(binding!!.root)
        onSetupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}