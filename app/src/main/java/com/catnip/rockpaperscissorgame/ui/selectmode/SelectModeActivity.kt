package com.catnip.rockpaperscissorgame.ui.selectmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.rockpaperscissorgame.databinding.ActivitySelectModeBinding
import com.catnip.rockpaperscissorgame.ui.inputname.InputNameFragment

class SelectModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.cvPlayerVsPlayer.setOnClickListener {
            InputNameFragment(0,"","",false).show(supportFragmentManager,null)
        }
        binding.cvPlayerVsComputer.setOnClickListener {
            InputNameFragment(0,"","COMPUTER",true).show(supportFragmentManager,null)
        }
    }

}