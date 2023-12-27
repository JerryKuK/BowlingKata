package com.example.bowlingkata

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private lateinit var maxBindingList: List<BowlingNormalBinding>
    private lateinit var lastBinding: BowlingLastBinding
    private val normalBindingList = mutableListOf<BowlingNormalBinding>()
    private var scoreLimit = 10
    private var isCancel = true
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
        initView()
        initListener()
    }

    private fun initView() {
        val list = mutableListOf<BowlingNormalBinding>()
        for (item in 1..10) {
            if(item == 10) {
                lastBinding = BowlingLastBinding.inflate(layoutInflater)
            } else {
                list.add(BowlingNormalBinding.inflate(layoutInflater))
            }
        }
        maxBindingList = list
    }

    private fun initListener() {
        binding.apply {
            etSet.addTextChangedListener {
                val text = it?.toString()?.toIntOrNull() ?: return@addTextChangedListener
                if(text < 1) {
                    etSet.setText("1")
                } else if(text > 10) {
                    etSet.setText(String.format("%d", 10))
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
                initSetLayout()
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
            buttonRandom200.setOnClickListener {
                setRandom200Score()
            }
            buttonRandomCancel.setOnClickListener {
                binding.tv200Time.text = ""
                isCancel = true
            }
        }
    }

    private fun initSetLayout() {
        scoreLimit = 10
        maxBindingList.forEach {
            it.tvScore1.text = ""
            it.tvScore2.text = ""
            it.tvTotalScore.text = ""
        }
        lastBinding.apply {
            tvScore1.text = ""
            tvScore2.text = ""
            tvScore3.text = ""
            tvTotalScore.text = ""
        }
        normalBindingList.clear()
        binding.tv200Time.text = ""
        binding.llContent.removeAllViews()
        setSetLayout()
    }

    private fun setSetLayout() {
        binding.apply {
            val number = etSet.text.toString().toInt()
            for (item in 1..number) {
                val binding = if(item == number) {
                    this@MainActivity.lastBinding
                } else {
                    val binding = maxBindingList[item - 1]
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

    private fun setRandom200Score() {
        binding.tv200Time.text = ""
        val standard = 200
        Thread{
            isCancel = false
            var count = 0L
            var totalScore = lastBinding.tvTotalScore.text.toString().toIntOrNull() ?: 0
            while(totalScore < standard && !isCancel) {
                runOnUiThread{
                    count ++
                    initSetLayout()
                    setRandomAllScore()
                    val score = lastBinding.tvTotalScore.text.toString().toIntOrNull()
                    if(score == null) {
                        isCancel = true
                    } else {
                        totalScore = score
                    }
                }
                Thread.sleep(50)
            }
            runOnUiThread{
                val score = lastBinding.tvTotalScore.text.toString().toIntOrNull() ?: return@runOnUiThread
                if(score >= standard) {
                    binding.tv200Time.text = String.format("隨機%d次才會超過200以上", count)
                    Toast.makeText(this, "隨機${count}次才會超過200以上", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private val getTotalScoreListener: (set: Int, totalScore: Int?) -> Unit
        get() = { set, totalScore ->
            val number = binding.llContent.childCount
            if(set == number) {
                lastBinding.tvTotalScore.text = totalScore?.toString() ?: ""
            } else {
                normalBindingList[set - 1].tvTotalScore.text = totalScore?.toString() ?: ""
            }
        }

    private val getScoreListener: (set: Int, bowlingType: BowlingType?, time: Int, score: Int?, isResetLimit: Boolean) -> Unit
        get() = { set, bowlingType, time, score, isResetLimit ->
            val number = binding.llContent.childCount
            if(set == number) {
                lastBinding.apply {
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