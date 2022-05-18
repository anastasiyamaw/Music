package com.example.music

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.music.databinding.SignInBinding


class SignIn : Fragment() {

    private var remember = false
    private lateinit var binding: SignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SignInBinding.inflate(inflater, container, false)
        val prefs = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        remember = prefs.getBoolean("CHECK", false)

        check()

        binding.textView.setOnClickListener {
            val savedlog = prefs.getString("login", "")
            val savedpass = prefs.getString("pass", "")
            val log = binding.login.text.toString()
            val pass = binding.password.text.toString()

            if ((checkLogin(log) == checkPassword(pass))  &&
                (log == savedlog) &&
                (pass == savedpass)) {
                val editor: SharedPreferences.Editor = prefs.edit()
                editor.putBoolean("CHECK", true).apply()
                Toast.makeText(context, "Вы успешно вошли", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signIn_to_secondFragment)
            }
            else {
                Toast.makeText(context, "Неверные введенные данные", Toast.LENGTH_SHORT).show()
            }
        }

        binding.sighUpButton.setOnClickListener{
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }
        return binding.root

    }

    private fun check(){
        if (remember) findNavController().navigate(R.id.action_signIn_to_secondFragment)
    }

    private fun checkLogin(log:String): Boolean {
        return if (log.isBlank()) {
            Toast.makeText(context, "Вы не ввели логин", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun checkPassword(pass: String): Boolean{
        if (pass.isBlank()) {
            Toast.makeText(context, "Вы не ввели пароль", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}