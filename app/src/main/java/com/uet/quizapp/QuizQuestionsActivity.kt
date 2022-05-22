package com.uet.quizapp

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    //! Id Variables.
    private lateinit var tvQuestion : TextView
    private lateinit var ivFlagImage : ImageView
    private lateinit var pbProgressbar : ProgressBar
    private lateinit var tvProgress : TextView
    private lateinit var tvOptionOne : TextView
    private lateinit var tvOptionTwo : TextView
    private lateinit var tvOptionThree : TextView
    private lateinit var tvOptionFour : TextView
    private lateinit var btnSubmit : Button

    //! Other variables
    private var mCurrentPosition = 1
    private lateinit var mQuestionsList : ArrayList<Question>
    private var mSelectedOptionPosition = 0

    //! On Create Method.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //! initializing Ids.
        tvQuestion = findViewById(R.id.tvQuestion)
        ivFlagImage = findViewById(R.id.ivFlagImage)
        pbProgressbar = findViewById(R.id.pbProgressbar)
        tvProgress = findViewById(R.id.tvProgress)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        //! fetching questionsList from Constants class
        mQuestionsList = Constants.getQuestions()

        //! setting questions.
        setQuestion()

        //! setting click listeners
        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener (this)
    }

    //! Setting Questions.
    private fun setQuestion() {
        val questionObject = mQuestionsList[mCurrentPosition - 1]

        tvQuestion.text = questionObject.question
        ivFlagImage.setImageResource(questionObject.image)
        pbProgressbar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition/${pbProgressbar.max}"
        tvOptionOne.text = questionObject.optionOne
        tvOptionTwo.text = questionObject.optionTwo
        tvOptionThree.text = questionObject.optionThree
        tvOptionFour.text = questionObject.optionFour
    }

    //! Default View Setter for all options.
    private fun defaultOptionsView() {
        val allOptionViews = ArrayList<TextView>()
        allOptionViews.add(0, tvOptionOne)
        allOptionViews.add(1, tvOptionTwo)
        allOptionViews.add(2, tvOptionThree)
        allOptionViews.add(3, tvOptionFour)

        for (optionViews in allOptionViews) {
            optionViews.setBackgroundResource(R.drawable.default_option_border_bg)
            optionViews.typeface = Typeface.DEFAULT
        }
    }

    //! Selected Option View Setter.
    private fun selectedOptionView(textView: TextView, selectionOptionNumber: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectionOptionNumber

        textView.setBackgroundResource(R.drawable.selected_option_border_bg)
        textView.setTypeface(textView.typeface, Typeface.BOLD)

    }

    //! On click Events
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tvOptionOne -> selectedOptionView(tvOptionOne, 1)
            R.id.tvOptionTwo -> selectedOptionView(tvOptionTwo, 2)
            R.id.tvOptionThree -> selectedOptionView(tvOptionThree, 3)
            R.id.tvOptionFour -> selectedOptionView(tvOptionFour, 4)
            R.id.btnSubmit -> {

            }
        }
    }
}
