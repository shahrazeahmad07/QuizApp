package com.uet.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //! Initializing ids
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val btnFinish = findViewById<Button>(R.id.btnFinish)

        //! fetching data from intent
        tvUserName.text = intent.getStringExtra(Constants.USER_NAME).toString()
        val scoreString = "You Scored ${intent.getIntExtra(Constants.SCORE,0)} out of ${intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)}"
        tvScore.text = scoreString

        btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
