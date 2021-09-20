package com.catnip.rockpaperscissorgame.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Window
import com.catnip.rockpaperscissorgame.R
import com.catnip.rockpaperscissorgame.databinding.ActivityMainBinding
import com.catnip.rockpaperscissorgame.utils.ButtonStatusUtils
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playerValue = -1
    private var comValue = -1
    private var isGameFinished: Boolean = false
    private var ButtonStatusUtils = ButtonStatusUtils()
    private lateinit var listBtn: Array<androidx.cardview.widget.CardView>
    private lateinit var listImageShowPosition: Array<android.widget.ImageView>
    private var timer: CountDownTimer? = null
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonSelectList()
        imageViewPositionList()
        setClickEvent()
    }

    private fun imageViewPositionList() {
        listImageShowPosition =
            arrayOf(
                binding.ivPlayerSelected,
                binding.ivComputerSelected
            )
    }

    private fun buttonSelectList() {
        listBtn =
            arrayOf(
                binding.cvBtnRock,
                binding.cvBtnPaper,
                binding.cvBtnScissor
            )
    }

    private fun setClickEvent() {
        listBtn.forEachIndexed { index, cardView ->
            cardView.setOnClickListener { check ->
                playerValue = index
                if (isGameFinished == false) {
                    listBtn.forEach {
                        if (check == it) {
                            binding.ivPlayerSelected.setImageResource(showImagePlayer(index))
                        }
                    }
                    ButtonStatusUtils.btnChangeColor(Color.LTGRAY, cardView)
                    Log.d(TAG, "Player select : $playerValue")
                    startGame()
                } else {
                    FancyToast.makeText(
                        this,
                        getString(R.string.text_suggest_restart),
                        FancyToast.LENGTH_LONG,
                        FancyToast.INFO,
                        true
                    ).show()
                }
            }
        }
        binding.ivResetGame.setOnClickListener {
            if (isGameFinished) {
                resetGame()
            } else {
                FancyToast.makeText(
                    this,
                    getString(R.string.text_suggest_play),
                    FancyToast.LENGTH_LONG,
                    FancyToast.INFO,
                    true
                ).show()
            }

        }
    }

    private fun startGame() {
        comValue = (0..2).random()
        loadingScreen(comValue)
        Log.d(TAG, "Computer select : $comValue")
        isGameFinished = true
    }

    private fun showImagePlayer(value: Int): Int {
        return when (value) {
            0 -> {
                R.drawable.ic_rock_selection
            }
            1 -> {
                R.drawable.ic_paper_selection
            }
            else -> {
                R.drawable.ic_scissor_selection
            }
        }
    }

    private fun resetGame() {
        isGameFinished = false
        listImageShowPosition.forEachIndexed { index, imageView ->
            imageView.setImageResource(0)
        }
        listBtn.forEachIndexed { index, cardView ->
            ButtonStatusUtils.btnChangeColor(Color.WHITE, cardView)
        }
        binding.tvResult.text = ""

    }

    private fun loadingScreen(value: Int) {
        val dialog = Dialog(this@MainActivity)
        dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.loading_screen)
        dialog.setCancelable(false)
        dialog.show()
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                dialog.cancel()
                binding.ivComputerSelected.setImageResource(showImagePlayer(value))
                showResult()
            }
        }
        timer?.start()
        return
    }

    private fun showResult() {
        when {
            playerValue - comValue == 0 -> {
                binding.tvResult.text = getString(R.string.text_result_draw)
                Log.d(TAG, "DRAW")
            }
            ((playerValue + 1) % 3) == comValue -> {
                binding.tvResult.text = getString(R.string.text_result_lose)
                Log.d(TAG, "USER LOSE")
            }
            else -> {
                binding.tvResult.text = getString(R.string.text_result_win)
                Log.d(TAG, "USER WIN")
            }
        }
    }

}
