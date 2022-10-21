package com.yusufcevizci.serpilyakala

import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess
import kotlin.random.Random as Random

class MainActivity : AppCompatActivity() {
    var score: Int = 0;
    var imageArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable{}
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("package com.yusufcevizci.serpilyakala",Context.MODE_PRIVATE)
        val scoreFromPreferences = sharedPreferences.getInt("score",-1)
        if(scoreFromPreferences == -1){
            highScore.text = "High Score :"
        }else{
            highScore.text = "High Score : $scoreFromPreferences"
        }

        imageArray.add(imageView1)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)
        for(image in imageArray)
        {
            image.visibility = View.INVISIBLE
        }
    }
    fun increaseScore(view: View){
            score++
            scoreText.text = "Score : $score"
    }
    fun saveHighScore()
    {
        val highScoreInt = score
        if(score > sharedPreferences.getInt("score",score)){
            highScore.text = "High Score: "+ score
            sharedPreferences.edit().putInt("score",score).apply()
        }else{}

    }

    fun start(view: View){
        hideImages()
        button.isClickable = false
        object : CountDownTimer(5000,1000){
            override fun onFinish() {

                button.isClickable = true
                for(image in imageArray)
                {
                    image.visibility = View.INVISIBLE
                }
                timeText.text = "Time : 0"
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Do you want to play again?")
                //alert.setMessage("Try Again?")
                alert.setPositiveButton("Yes"){dialog,which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No"){dialog,which ->
                    Toast.makeText(this@MainActivity,"Game Over!",Toast.LENGTH_LONG).show()
                    score = 0
                    scoreText.text = "Score : 0"
                }
                alert.show()
                handler.removeCallbacks(runnable)
                saveHighScore()
                score = 0
                scoreText.text = "Score : 0"
            }

            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Time :" + millisUntilFinished / 1000
            }
        }.start()
    }

    fun hideImages(){
        runnable = object : Runnable{
            override fun run() {
                for(image in imageArray)
                {
                    image.visibility = View.INVISIBLE
                }
                val random = java.util.Random()
                val randomIndex = random.nextInt(9)

                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,400)

            }
        }
        handler.post(runnable)
    }

}


