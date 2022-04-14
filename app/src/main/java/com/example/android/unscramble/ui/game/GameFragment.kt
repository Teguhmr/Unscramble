/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shashank.sony.fancytoastlib.FancyToast
import com.thekhaeng.pushdownanim.PushDownAnim
import com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE
import java.util.*

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment.
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.skip.setOnClickListener { onSkipWord() }

        //for (String key : keys) {            addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));        }
        removeAllViews()

        PushDownAnim.setPushDownAnimTo(binding.buttonClear)
            .setScale(
                MODE_SCALE,
                1.2f
            )
            .setDurationPush(100)
            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
            .setOnClickListener {
                setErrorTextField(false)
                binding.textInputEditText.setText("")
                PRESS_COUNTER = 0
                shuffleArray(viewModel.currentWord.arrange)
                binding.layoutParent.removeAllViews()
                binding.layoutParent1.removeAllViews()
                //for (String key : keys) {                    }
                for (i in viewModel.currentWord.arrange.size - 1 downTo 0) {
                    val key: String = viewModel.currentWord.arrange[i]
                    if (i > 4) addView(
                        binding.layoutParent, key,
                        binding.textInputEditText
                    ) else addView(
                        binding.layoutParent1, key,
                        binding.textInputEditText
                    )
                }
            }

        binding.btnHint.setOnClickListener {
            hintWord()
        }
    }

    private fun hintWord() {
        viewModel.hintZero(binding.btnHint)
        if (viewModel.isHintUsed(binding.btnHint)){
            setHintVisible(true)
        }
    }
    private fun setHintVisible(hint: Boolean) {
        if (hint) {
            binding.textViewUnscrambledWord.visibility = View.VISIBLE
        } else {
            binding.textViewUnscrambledWord.visibility = View.GONE
        }
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    * After the last word, the user is shown a Dialog with the final score.
    */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setHintVisible(false)
            setErrorTextField(false)
            FancyToast.makeText(
                activity,
                "KAMU BENAR !",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
            ).show()

            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            shuffleArray(viewModel.currentWord.arrange)
            setErrorTextField(true)
        }
        removeAllViews()
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     * After the last word, the user is shown a Dialog with the final score.
     */
    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setHintVisible(false)
            binding.textInputEditText.setText("")
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
        removeAllViews()
    }

    private fun removeAllViews(){
        binding.layoutParent.removeAllViews()
        binding.layoutParent1.removeAllViews()
        for (i in viewModel.currentWord.arrange.size - 1 downTo 0) {
            val key: String = viewModel.currentWord.arrange[i]
            if (i > 4) addView(binding.layoutParent, key, binding.textInputEditText) else addView(
                binding.layoutParent1,
                key,
                binding.textInputEditText
            )
        }
    }

    /*
     * Creates and shows an AlertDialog with final score.
     */
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, viewModel.score.value))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.exit)) { _, _ ->
                    exitGame()
                }
                .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                    restartGame()
                }
                .show()
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        removeAllViews()
        viewModel.hintZero(binding.btnHint)
        setErrorTextField(false)
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addView(viewParent: LinearLayout, text: String, editText: EditText) {
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        linearLayoutParams.rightMargin = 10
        linearLayoutParams.leftMargin = 10


//		int _10 = R.dimen._10sdp;
//		linearLayoutParams.width = 100
//		linearLayoutParams.height = 100
        val textView = TextView(activity)
        textView.layoutParams = linearLayoutParams
        textView.isElegantTextHeight = true
        textView.background = ContextCompat.getDrawable(requireContext(), R.drawable.bgpink)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.indigo_500))
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.textSize = 28f
        textView.isClickable = true
        textView.isFocusable = true
        val typeface = Typeface.createFromAsset(requireActivity().assets, "fonts/FredokaOneRegular.ttf")
        editText.typeface = typeface
        textView.typeface = typeface
        textView.setOnClickListener {
            val smallBigForth = AnimationUtils.loadAnimation(
                activity, R.anim.smallbigforth
            )

            if (PRESS_COUNTER < viewModel.currentWord.answer.length) {
                if (PRESS_COUNTER == 0) {
                    editText.setText("")
                    setErrorTextField(false)
                }
                editText.setText(editText.text.toString() + text)
                textView.startAnimation(smallBigForth)
                textView.isEnabled = false
                textView.animate().alpha(0f).duration = 300
                PRESS_COUNTER++
                if (PRESS_COUNTER == viewModel.currentWord.answer.length) {
                    PRESS_COUNTER = 0
                    onSubmitWord()
                }
            }
        }
        viewParent.addView(textView)
    }

    private fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

}
