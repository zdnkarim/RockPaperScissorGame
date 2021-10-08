package com.catnip.rockpaperscissorgame.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.catnip.rockpaperscissorgame.R
import com.catnip.rockpaperscissorgame.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var player1Val = -1
    private var player2Val = -1
    private lateinit var listBtn: Array<androidx.cardview.widget.CardView>
    private lateinit var listImageShowPosition: Array<android.widget.ImageView>
    private val TAG = GameActivity::class.java.simpleName
    private var player1 = ""
    private var player2 = ""
    private var isGameFinish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPlayerName()
        buttonSelectList()
        imageViewPositionList()
        setClickEvent()
    }

    private fun getPlayerName() {
        intent.getStringExtra("player1")?.let {
            player1 = it
            binding.tvPlayer1.text = it
        }
        intent.getStringExtra("player2")?.let {
            player2 = it
            binding.tvPlayer2.text = it
        }
    }

    private fun imageViewPositionList() {
        listImageShowPosition =
            arrayOf(
                binding.ivPlayer1Selected,
                binding.ivPlayer2Selected
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
                player1Val = index
                listBtn.forEach {
                    if (check == it) {
                        binding.ivPlayer1Selected.setImageResource(showImagePlayer(index))
                    }
                }
                switch()
                showResult()
            }
        }

        binding.ivResetGame.setOnClickListener {
            resetGame()
        }
    }

    private fun switch() {
        if (player2 == "COMPUTER") {
            player2Val = (0..2).random()
            binding.ivPlayer2Selected.setImageResource(showImagePlayer(player2Val))
        } else {
            listBtn.forEachIndexed { index, cardView ->
                cardView.setOnClickListener { check ->
                    player2Val = index
                    binding.ivPlayer2Selected.setImageResource(showImagePlayer(player2Val))
                }
            }
        }
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
        listImageShowPosition.forEachIndexed { index, imageView ->
            imageView.setImageResource(0)
        }
        setClickEvent()

    }

    private fun showResult() {
        when {
            player1Val - player2Val == 0 -> {
                openSimpleAlertDialog(getString(R.string.text_result_draw))
            }
            ((player1Val + 1) % 3) == player2Val -> {
                openSimpleAlertDialog("${player2} ${getString(R.string.text_result_win)}")
            }
            else -> {
                openSimpleAlertDialog("$player1 ${getString(R.string.text_result_win)}")
            }
        }
    }

    private fun openSimpleAlertDialog(string: String) {
        AlertDialog.Builder(this)
            .apply {
                setMessage(string)
                create()
                show()
            }
    }

}
