package com.kelompok5.ecotech
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EditText : AppCompatEditText {
    constructor(context: Context):super(context){
        emailEdit()
    }

    constructor(context: Context, attributeSet:AttributeSet): super(context,attributeSet){
        emailEdit()
    }
    constructor(context: Context, attributeSet: AttributeSet, styleAttributset:Int): super(context,attributeSet,styleAttributset){
        emailEdit()
    }

    private fun emailEdit() {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(eText: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(eText: CharSequence?, start: Int, before: Int, count: Int) {
                if(eText.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(eText ?: "").matches()){
                    error = resources.getString(R.string.invalidEmail)
                }
            }

            override fun afterTextChanged(eText: Editable?) {
            }

        })
    }
}