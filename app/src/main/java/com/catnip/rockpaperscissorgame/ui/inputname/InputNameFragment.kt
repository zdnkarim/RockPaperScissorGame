package com.catnip.rockpaperscissorgame.ui.inputname

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.catnip.rockpaperscissorgame.databinding.FragmentInputNameBinding
import com.catnip.rockpaperscissorgame.ui.main.GameActivity

class InputNameFragment(
    private val player: Int,
    private var player1: String,
    private var player2: String,
    private val isSave: Boolean
) : DialogFragment() {

    private lateinit var binding: FragmentInputNameBinding
    private val TAG = InputNameFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputNameBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputName()
    }

    private fun inputName() {
        val intent = Intent(context, GameActivity::class.java)
        when (player) {
            0 -> {
                binding.tvTitleInputLayout.text = "INPUT PLAYER 1 NAME"
                binding.btnInputName.setOnClickListener {
                    if (isSave) {
                        player1 = binding.etInputNameText.text.toString()
                        intent.putExtra("player1", player1)
                        intent.putExtra("player2", player2)
                        startActivity(intent)
                    } else {
                        player1 = binding.etInputNameText.text.toString()
                        InputNameFragment(1, player1, "", true).show(parentFragmentManager, null)
                    }
                }
            }
            1 -> {
                binding.tvTitleInputLayout.text = "INPUT PLAYER 2 NAME"
                binding.btnInputName.setOnClickListener {
                    player2 = binding.etInputNameText.text.toString()
                    intent.putExtra("player1", player1)
                    intent.putExtra("player2", player2)
                    startActivity(intent)
                }
            }
        }
    }

    private fun saveData() {
    }
}