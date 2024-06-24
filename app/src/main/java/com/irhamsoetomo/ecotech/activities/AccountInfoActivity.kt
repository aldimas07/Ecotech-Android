package com.irhamsoetomo.ecotech.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.irhamsoetomo.ecotech.EcotechApp
import com.irhamsoetomo.ecotech.R
import com.irhamsoetomo.ecotech.databinding.ActivityAccountInfoBinding

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val sharedPref = EcotechApp.context.getSharedPreferences("ecoTechPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("emailShort", null)

        binding.tvNama.text = email

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    with(sharedPref.edit()) {
                        putString("emailShort", null)
                        putString("email", null)
                        putString("password", null)
                        apply()
                    }
                    val intent = Intent(this@AccountInfoActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}