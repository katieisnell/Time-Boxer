package com.katiesnell.timeboxer.timeboxer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView

    internal var score = 0

    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer

    // 10000 milliseconds represents 10 seconds here
    internal val initialCountDown: Long = 10000
    internal val countdownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)

        resetGame()

        tapMeButton.setOnClickListener {
            view ->
            incrementScore()
        }
    }

    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())

        // We convert the milliseconds to seconds for our user to see
        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft.toString())

        // Create an instance of countDownTimer
        countDownTimer = object: CountDownTimer(initialCountDown, countdownInterval) {
            override fun onTick(msUntilFinished: Long) {
                val timeLeft = msUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score = score + 1
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore
    }


}
