package budyakova.rsue.droidquest

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "QuestActivity"
        private val REQUEST_CODE_DECEIT = 0
    }

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mDeceitButton: Button
    private lateinit var mPreviousButtonImage: ImageButton
    private lateinit var mNextButtonImage: ImageButton
    private lateinit var mNextButton: Button
    private lateinit var mQuestionTextView: TextView
    private val mQuestionBank = listOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true),
        Question(R.string.question_activity, true),
        Question(R.string.question_match, true),
        Question(R.string.question_find, true),
        Question(R.string.question_drawable, true),
        Question(R.string.question_toast, true)
    )
    private var mCurrentIndex = 0
    private var mIsDeceiter = false

    fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (mIsDeceiter) R.string.judgment_toast
        else if (userPressedTrue == answerIsTrue) {
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

        if (savedInstanceState != null) {
            mIsDeceiter = savedInstanceState.getBoolean("mIsDeceiter")
            mCurrentIndex = savedInstanceState.getInt("mCurrentIndex")
        }

        mQuestionTextView = findViewById(R.id.question_text_view)
        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            if (mCurrentIndex + 1 < mQuestionBank.size) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
                mIsDeceiter = false
                updateQuestion()
            } else {
                Toast.makeText(this, R.string.end, Toast.LENGTH_SHORT).show()
            }
        }

        mDeceitButton = findViewById(R.id.deceit_button)
        mDeceitButton.setOnClickListener {
            val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
            val intent = DeceitActivity.newIntent(this, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_DECEIT)
        }

        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null) {
                return;
            }
            mIsDeceiter = DeceitActivity.wasAnswerShown(result = data);
        }
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean("mIsDeceiter", mIsDeceiter)
            putInt("mCurrentIndex", mCurrentIndex)
        }

        super.onSaveInstanceState(outState)
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

