package com.example.bowlingkata

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.bowlingkata.databinding.ActivityMainBinding
import com.example.bowlingkata.databinding.BowlingLastBinding
import com.example.bowlingkata.databinding.BowlingNormalBinding

class MainActivity : AppCompatActivity() {
    private var player: Player? = null
    private lateinit var binding: ActivityMainBinding
    private val normalBindingList = mutableListOf<BowlingNormalBinding>()
    private var lastBinding: BowlingLastBinding? = null
    private var scoreLimit = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListener()
    }

    private fun initListener() {
        binding.apply {
            etSet.addTextChangedListener {
                val text = it?.toString()?.toIntOrNull() ?: return@addTextChangedListener
                if(text < 1) {
                    etSet.setText("1")
                } else if(text > 10) {
                    etSet.setText("10")
                }
            }
            etScore.addTextChangedListener {
                val text = it?.toString()?.toIntOrNull() ?: return@addTextChangedListener
                if(text < 0) {
                    etScore.setText("0")
                } else if(text > scoreLimit) {
                    etScore.setText(scoreLimit.toString())
                }
            }
            buttonSet.setOnClickListener {
                scoreLimit = 10
                normalBindingList.clear()
                lastBinding = null
                llContent.removeAllViews()
                setSetLayout()
            }
            buttonScore.setOnClickListener {
                setScoreLayout()
            }
            buttonRandom1.setOnClickListener {
                setRandom1Score()
            }
            buttonRandomAll.setOnClickListener {
                setRandomAllScore()
            }
        }
    }

    private fun setSetLayout() {
        binding.apply {
            val number = etSet.text.toString().toInt()
            for (item in 1..number) {
                val binding = if(item == number) {
                    val lastBinding = BowlingLastBinding.inflate(layoutInflater)
                    this@MainActivity.lastBinding = lastBinding
                    lastBinding
                } else {
                    val binding = BowlingNormalBinding.inflate(layoutInflater)
                    normalBindingList.add(binding)
                    binding
                }
                llContent.addView(binding.root)
            }

            player = Player(number)
            player?.totalScoreListener = getTotalScoreListener
            player?.scoreListener = getScoreListener
        }
    }

    private fun setScoreLayout() {
        binding.apply {
            val number = etScore.text.toString().toIntOrNull() ?: 0
            scoreLimit -= number

            player?.sendData(number)
            etScore.setText("")
        }
    }

    private fun setRandom1Score() {
        val random = (Math.random() * scoreLimit).toInt() + 1
        Log.d("setRandom1Score", "random = $random")
        binding.etScore.setText(random.toString())
        setScoreLayout()
    }

    private fun setRandomAllScore() {
        while (player?.isLastSetFinish() == false) {
            setRandom1Score()
        }
    }

    private val getTotalScoreListener: (set: Int, totalScore: Int?) -> Unit
        get() = { set, totalScore ->
            val number = binding.llContent.childCount
            if(set == number) {
                lastBinding?.tvTotalScore?.text = totalScore?.toString() ?: ""
            } else {
                normalBindingList[set - 1].tvTotalScore.text = totalScore?.toString() ?: ""
            }
        }

    private val getScoreListener: (set: Int, bowlingType: BowlingType?, time: Int, score: Int?, isResetLimit: Boolean) -> Unit
        get() = { set, bowlingType, time, score, isResetLimit ->
            val number = binding.llContent.childCount
            if(set == number) {
                lastBinding?.apply {
                    when(time) {
                        1 -> tvScore1.text = getScoreString(bowlingType, score)
                        2 -> tvScore2.text = getScoreString(bowlingType, score)
                        3 -> tvScore3.text = getScoreString(bowlingType, score)
                    }
                }
            } else {
                normalBindingList[set - 1].apply {
                    when(time) {
                        1 -> tvScore1.text = getScoreString(bowlingType, score)
                        2 -> tvScore2.text = getScoreString(bowlingType, score)
                    }
                }
            }

            if(isResetLimit) {
                scoreLimit = 10
            }
        }

    private fun getScoreString(bowlingType: BowlingType?, score: Int?): String {
        return if(bowlingType == BowlingType.STRIKE) {
            "X"
        } else if(bowlingType == BowlingType.SPARE) {
            "/"
        } else if(score == 0) {
            "-"
        } else {
            score?.toString() ?: ""
        }
    }
}