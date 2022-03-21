package budyakova.rsue.droidquest

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "QuestActivity"
    }

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mPreviousButtonImage: ImageButton
    private lateinit var mNextButtonImage: ImageButton
    private lateinit var mNextButton: Button
    private lateinit var mQuestionTextView: TextView
    private val mQuestionBank = listOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true)
    )
    private var mCurrentIndex = 0

    fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate(Bundle) вызван")
        mQuestionTextView = findViewById(R.id.question_text_view)
        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        makeHandlersForNavigationButtons()
    }

    private fun makeHandlersForNavigationButtons() {
        if (resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
            mNextButton = findViewById(R.id.next_button)
            mNextButton.setOnClickListener {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
                updateQuestion()
            }

            return
        }

        mPreviousButtonImage = findViewById(R.id.previous_button_image)
        mPreviousButtonImage.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        mNextButtonImage = findViewById(R.id.next_button_image)
        mNextButtonImage.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() вызван")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() вызван")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() вызван")
    }

}

