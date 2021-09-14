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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playerSelected: Int? = null
    private var comSelected: Int? = null
    private lateinit var listSelection: Array<androidx.cardview.widget.CardView>
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start() {
        binding.ivResetGame.isClickable = false
        main()
    }

    private fun main() {
        listSelection =
            arrayOf(
                binding.cvRockSelection,
                binding.cvPaperSelection,
                binding.cvScissorSelection
            )

        listSelection.forEachIndexed { index, frameLayout ->
            frameLayout.setOnClickListener { check ->
                playerSelected = index
                listSelection.forEach {
                    frameLayout.setCardBackgroundColor(Color.LTGRAY)
                    if (check == it) {
                        binding.ivPlayerSelected.setImageResource(showImage(index, 0))
                    }
                }
                loadingScreen()
            }
        }
    }

    private fun chooseTheWinner(player: Int, com: Int) {
        val dialog = Dialog(this@MainActivity)
        dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.loading_screen)
        dialog.setCancelable(false)
        dialog.show()
        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                when {
                    player - com == 0 -> {
                        showResult("DRAW!!!")
                        Log.d(
                            "RESULT",
                            "DRAW_-"
                        )
                    }
                    ((player + 1) % 3) == com -> {
                        showResult("YOU LOSE :(")
                        Log.d(
                            "RESULT",
                            "USER LOSE"
                        )
                    }
                    else -> {
                        showResult("YOU WIN :v")
                        Log.d(
                            "RESULT",
                            "USER WIN"
                        )
                    }
                }
                dialog.cancel()
            }
        }
        timer?.start()
    }

    private fun showResult(s: String) {
        binding.tvResult.text = s
        binding.ivResetGame.isClickable = true
        binding.ivResetGame.setOnClickListener {
            restart()
        }
    }

    private fun restart() {
        binding.ivResetGame.isClickable = false
        binding.tvResult.text = ""
        playerSelected = null
        comSelected = null
        listSelection.forEachIndexed { _, frameLayout ->
            frameLayout.isClickable = true
            frameLayout.setCardBackgroundColor(Color.WHITE)
        }
        removeImage(binding)
        main()
    }

    private fun showImage(indexImage: Int, i: Int): Int {
        return when (indexImage) {
            0 -> {
                if (i == 0) {
                    Log.d(
                        "USER SELECT",
                        "USER SELECT ROCK"
                    )
                } else {
                    Log.d(
                        "COM SELECT",
                        "COM SELECT ROCK"
                    )
                }
                R.drawable.ic_rock_selection
            }
            1 -> {
                if (i == 0) {
                    Log.d(
                        "USER SELECT",
                        "USER SELECT PAPER"
                    )
                } else {
                    Log.d(
                        "COM SELECT",
                        "COM SELECT PAPER"
                    )
                }
                R.drawable.ic_paper_selection
            }
            else -> {
                if (i == 0) {
                    Log.d(
                        "USER SELECT",
                        "USER SELECT SCISSOR"
                    )
                } else {
                    Log.d(
                        "COM SELECT",
                        "COM SELECT SCISSOR"
                    )
                }
                R.drawable.ic_scissor_selection
            }
        }
    }

    private fun removeImage(binding: ActivityMainBinding) {
        binding.ivPlayerSelected.setImageResource(0)
        binding.ivComputerSelected.setImageResource(0)
    }

    private fun disableClickable() {
        listSelection.forEachIndexed { _, frameLayout ->
            frameLayout.isClickable = false
        }
    }

    private fun checkComputerSelect() {
        comSelected = (0..2).random()
        binding.ivComputerSelected.setImageResource(showImage(comSelected!!, 1))
        playerSelected?.let {
            chooseTheWinner(it, comSelected!!)
            disableClickable()
        }
    }

    private fun loadingScreen() {
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
                checkComputerSelect()
            }
        }
        timer?.start()
        return
    }
}
