package com.example.myquizapp

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AdaptiveIconDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int=1
    private var mQuestionList:ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int=0

    private var progressBar:ProgressBar?=null
    private var tvProgress:TextView?=null
    private var tvQuestion:TextView?=null
    private var ivImage:ImageView?=null

    private var tvOption1:TextView?=null
    private var tvOption2:TextView?=null
    private var tvOption3:TextView?=null
    private var tvOption4:TextView?=null

    private var btnSubmit:Button?=null

    private var mUserName:String?=null
    private var mCorrectAns:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        progressBar=findViewById(R.id.progressBar)
        tvProgress=findViewById(R.id.tv_progress)
        tvQuestion=findViewById(R.id.tv_question)
        ivImage=findViewById(R.id.iv_image)

        tvOption1=findViewById(R.id.tv_option1)
        tvOption2=findViewById(R.id.tv_option2)
        tvOption3=findViewById(R.id.tv_option3)
        tvOption4=findViewById(R.id.tv_option4)
        btnSubmit=findViewById(R.id.btn_submit)

        tvOption1?.setOnClickListener(this)
        tvOption2?.setOnClickListener(this)
        tvOption3?.setOnClickListener(this)
        tvOption4?.setOnClickListener(this)
        btnSubmit?.setOnClickListener (this)
        mUserName=intent.getStringExtra(constants.USER_NAME)



        mQuestionList=constants.getQuestions()
        setQuestion()


    }

    private fun setQuestion() {
        defaultOptionsView()
        val i:Question=mQuestionList!![mCurrentPosition-1]
            ivImage?.setImageResource(i.image)
            progressBar?.progress = mCurrentPosition
            tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"

            tvQuestion?.text = i.question


            tvOption1?.text = i.optionOne
            tvOption2?.text = i.optionTwo
            tvOption3?.text = i.optionThree
            tvOption4?.text = i.optionFour

            if(mCurrentPosition==mQuestionList!!.size)
                btnSubmit?.text="Finish"
        else
                btnSubmit?.text="Submit"


        }


    private fun defaultOptionsView(){
        val options=ArrayList<TextView>()
        tvOption1?.let {
            options.add(0,it)
        }
        tvOption2?.let{
            options.add(1,it)
        }
        tvOption3?.let{
            options.add(2,it)
        }
        tvOption4?.let{
            options.add(3,it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView,selectedOptionNum:Int){

        mSelectedOptionPosition=selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(
            this,
            R.drawable.selected_option
        )

    }

    override fun onClick(view: View?) {

        when(view?.id){
             R.id.tv_option1 ->{
                 tvOption1?.let{
                     defaultOptionsView()
                     selectedOptionView(it,1)
                 }
             }
             R.id.tv_option2 ->{
                 tvOption2?.let{
                     defaultOptionsView()
                     selectedOptionView(it,2)
                 }
             }
             R.id.tv_option3 ->{
                 defaultOptionsView()
                 tvOption3?.let{
                     selectedOptionView(it,3)
                 }
             }
             R.id.tv_option4 ->{
                 defaultOptionsView()
                 tvOption4?.let{
                     selectedOptionView(it,4)
                 }
             }

             R.id.btn_submit ->{

                 if(mSelectedOptionPosition==0) {
                     allow()
                     mCurrentPosition++
                     when {
                         mCurrentPosition <= mQuestionList!!.size -> {
                             setQuestion()
                         }

                         else -> {
                             //QuestionList over
                            val intent= Intent(this,ResultActivity::class.java)
                             intent.putExtra(constants.USER_NAME,mUserName)
                             intent.putExtra(constants.CORRECT_ANSWERS,mCorrectAns)
                             intent.putExtra(constants.TOTAL_QUESTIONS,mQuestionList?.size)
                             startActivity(intent,
                                 ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                             finish()


                         }
                     }


                     }else{
                         val question=mQuestionList!![mCurrentPosition-1]
                     if(question!!.correctAns!=mSelectedOptionPosition){
                         answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                     }else{
                         mCorrectAns++
                     }
                         answerView(question.correctAns,R.drawable.correct_option_border_bg)

                     if(mCurrentPosition==mQuestionList!!.size)
                         btnSubmit?.text="FINISH"
                     else
                         btnSubmit?.text="Go to next question"


                     mSelectedOptionPosition=0
                     restrict()


                 }

             }


         }
    }

    private fun restrict(){
        tvOption1?.isEnabled=false
        tvOption2?.isEnabled=false
        tvOption3?.isEnabled=false
        tvOption4?.isEnabled=false
    }
    private fun allow(){
        tvOption1?.isEnabled=true
        tvOption2?.isEnabled=true
        tvOption3?.isEnabled=true
        tvOption4?.isEnabled=true
    }

    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1->{
                tvOption1?.background=ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2->{
                tvOption2?.background=ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3->{
                tvOption3?.background=ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4->{
                tvOption4?.background=ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

        }
    }
}