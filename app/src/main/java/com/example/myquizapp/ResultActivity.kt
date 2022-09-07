package com.example.myquizapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private var tvName:TextView?=null
    private var tvScore:TextView?=null
    private var btnFinish: Button?=null


    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvName=findViewById(R.id.tv_name)
        tvScore=findViewById(R.id.tv_score)
        btnFinish=findViewById(R.id.btn_finish)

         tvName?.text=intent.getStringExtra(constants.USER_NAME)
        val totalQuestions=intent.getIntExtra(constants.TOTAL_QUESTIONS,0)
        val correctAns=intent.getIntExtra(constants.CORRECT_ANSWERS,0)
        tvScore?.text="Your score is ${correctAns}/$totalQuestions"

        btnFinish?.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}