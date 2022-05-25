package com.uet.quizapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var userName: String
    private var mCurrentPosition = 1
    private lateinit var mQuestionsList : ArrayList<Question>
    private var mSelectedOptionPosition = 0
    private var answerFlag = 0
    private var score = 0

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

        //! fetching user name from intent data
        userName  = intent.getStringExtra(Constants.USER_NAME).toString()

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
        defaultOptionsView()
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

    //! Submit Button Text View coloring.
    private fun submitButtonColor(question: Question) {
        if (mSelectedOptionPosition != question.correctOption) {
            when(mSelectedOptionPosition) {
                1 -> tvOptionOne.setBackgroundResource(R.drawable.wrong_option_border_bg)
                2 -> tvOptionTwo.setBackgroundResource(R.drawable.wrong_option_border_bg)
                3 -> tvOptionThree.setBackgroundResource(R.drawable.wrong_option_border_bg)
                4 -> tvOptionFour.setBackgroundResource(R.drawable.wrong_option_border_bg)
            }
        }
        else {
            score += 1
        }
        when(question.correctOption) {
            1 -> tvOptionOne.setBackgroundResource(R.drawable.right_option_border_bg)
            2 -> tvOptionTwo.setBackgroundResource(R.drawable.right_option_border_bg)
            3 -> tvOptionThree.setBackgroundResource(R.drawable.right_option_border_bg)
            4 -> tvOptionFour.setBackgroundResource(R.drawable.right_option_border_bg)
        }
    }

    //! On click Events
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tvOptionOne -> selectedOptionView(tvOptionOne, 1)
            R.id.tvOptionTwo -> selectedOptionView(tvOptionTwo, 2)
            R.id.tvOptionThree -> selectedOptionView(tvOptionThree, 3)
            R.id.tvOptionFour -> selectedOptionView(tvOptionFour, 4)
            R.id.btnSubmit -> {
                val question = mQuestionsList[mCurrentPosition-1]
                if (answerFlag == 0) {
                    if (mSelectedOptionPosition == 0) {
                        Toast.makeText(this, "Select an Option First", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        submitButtonColor(question)
                        answerFlag = 1
                        if (mCurrentPosition < mQuestionsList.size) {
                            mCurrentPosition++
                            mSelectedOptionPosition = 0
                            btnSubmit.text = getString(R.string.btnNextQuestion)
                        }
                        else {
                            btnSubmit.text = getString(R.string.finish)
                        }
                    }
                }
                else {
                    if (btnSubmit.text != getString(R.string.finish)) {
                        setQuestion()
                        answerFlag = 0
                        mSelectedOptionPosition = 0
                        btnSubmit.text = getString(R.string.submit)
                    }
                    else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, userName)
                        intent.putExtra(Constants.SCORE, score)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
