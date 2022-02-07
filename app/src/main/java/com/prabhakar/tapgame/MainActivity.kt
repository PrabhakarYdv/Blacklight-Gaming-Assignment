package com.prabhakar.tapgame

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var timeCounter = 0
    private var index = 0
    private var currentColor = Color.GRAY
    private val list = arrayListOf<View>()
    var isGameOver = false
    var isClickable1 = false
    var isClickable2 = false
    var isClickable3 = false
    var isClickable4 = false
    var listOfClickable = arrayListOf(isClickable1, isClickable2, isClickable3, isClickable4)

    var score = 0;
    private val liveScore = LiveScore()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Adding Index inside list
        list.add(redBlock)
        list.add(blueBlock)
        list.add(yellowBlock)
        list.add(greenBlock)

//       Start Playing Game After Click on Play Button
        playButton.setOnClickListener {
            isGameOver = false
            timeCounter = 0
//            timeCounterIncrease()
            playGame()
            if (timeCounter > 1) {
                gameOver()
            }
//            while (!isGameOver){
//                playGame()
//            }
        }
        if (timeCounter > 1) {
            gameOver()
        }

        redBlock.setOnClickListener {
            timeCounter = 0
            if (listOfClickable[0]) {
                liveScore.incrementByOne()
            } else {
                gameOver()
            }
        }

        blueBlock.setOnClickListener {
            timeCounter = 0
            if (listOfClickable[1]) {
                liveScore.incrementByOne()
            } else {
                gameOver()
            }
        }

        yellowBlock.setOnClickListener {
            timeCounter = 0
            if (listOfClickable[2]) {
                liveScore.incrementByOne()
            } else {
                gameOver()
            }
        }

        greenBlock.setOnClickListener {
            timeCounter = 0
            if (listOfClickable[3]) {
                liveScore.incrementByOne()
            } else {
                gameOver()
            }
        }

//        Observing Live Data From Live Score Class
        liveScore.getScore().observe(this, Observer {
            score = it
            gameScore.text = "Score: $it"
        })

    }

    //Function For Play Game
    private fun playGame() {
        if (timeCounter > 1) {
            gameOver()
        }
//        timeCounter=0
        index = (0..3).random()

//       Making View Clickable and Gray
        when (index) {
            0 -> {
                currentColor = Color.parseColor("#FF1100")
//                listOfClickable=makeViewClickable(arrayListOf(isClickable1,isClickable2,isClickable3,isClickable4))
                listOfClickable[0] = true
                listOfClickable[1] = false
                listOfClickable[2] = false
                listOfClickable[3] = false
            }
            1 -> {
                currentColor = Color.parseColor("#1165A8")
//                listOfClickable=makeViewClickable(arrayListOf(isClickable2,isClickable1,isClickable3,isClickable4))
                listOfClickable[0] = false
                listOfClickable[1] = true
                listOfClickable[2] = false
                listOfClickable[3] = false
            }
            2 -> {
                currentColor = Color.parseColor("#FBBD00")
//                listOfClickable=makeViewClickable(arrayListOf(isClickable3,isClickable2,isClickable1,isClickable4))
                listOfClickable[0] = false
                listOfClickable[1] = false
                listOfClickable[2] = true
                listOfClickable[3] = false
            }
            3 -> {
                currentColor = Color.parseColor("#0BBD11")
//                listOfClickable=makeViewClickable(arrayListOf(isClickable4,isClickable2,isClickable3,isClickable1))
                listOfClickable[0] = false
                listOfClickable[1] = false
                listOfClickable[2] = false
                listOfClickable[3] = true
            }
        }

        if (!isGameOver) {
            list[index].setBackgroundColor(Color.GRAY)
            Handler().postDelayed(Runnable {
                list[index].setBackgroundColor(currentColor)
                timeCounter++
                playGame()
            }, 1000)
        }
    }

    //    Game Over Dialog Box
    private fun gameOver() {
        AlertDialog.Builder(this)
            .setTitle("Game Over !")
            .setMessage("Your score is: $score")
            .create()
            .show()
        isGameOver = true
        score = 0
        timeCounter = 0
        liveScore.resetScore()
    }

    private fun makeViewClickable(views: ArrayList<Boolean>): ArrayList<Boolean> {
        var temp = views
        views[0] = true
        views[1] = false
        views[2] = false
        views[3] = false
//        listOfClickable=views
        return temp
    }

    //    Function To Check If User is Clicking or not in every Seconds
    private suspend fun timeCounterIncrease() {
        timeCounter = 0
        Handler().postDelayed(Runnable {
            timeCounter++
//            timeCounterIncrease()
        }, 1000)
    }
}