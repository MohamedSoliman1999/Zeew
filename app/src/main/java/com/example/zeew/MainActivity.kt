package com.example.zeew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.zeew.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
@ActivityScoped
open class MainActivity : AppCompatActivity() {
    var navHostFragment: NavHostFragment? = null
    var _binding:ActivityMainBinding?=null
    val binding:ActivityMainBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_app_nav_host_fragment) as NavHostFragment?
    }
    fun getCurrentFragment(): Fragment {
        val currentFragment = navHostFragment!!.childFragmentManager.fragments[0]
        return currentFragment
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        navHostFragment=null
    }
}