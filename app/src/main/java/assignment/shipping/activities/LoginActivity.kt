package assignment.shipping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import assignment.shipping.R

class LoginActivity : AppCompatActivity() {

//    private val emailLiveData = MutableLiveData<String>()
//    private val passwordLiveData = MutableLiveData<String>()
//    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
//        this.value = false
//
//        addSource(emailLiveData){ email ->
//            val password = passwordLiveData.value
//            this.value = validateForm(email, password)
//        }
//
//        addSource(passwordLiveData) { password ->
//            val email = emailLiveData.value
//            this.value = validateForm(email, password)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById (R.id.login)

        loginButton.setOnClickListener() {
            val intent = Intent(this, VesselActivity::class.java)
            startActivity(intent)
        }

//        val emailLayout = findViewById<TextView>(R.id.email)
//        val passwordLayout = findViewById<TextView>(R.id.password)
//        val signInButton = findViewById<Button>(R.id.login)
//
//        emailLayout.doOnTextChanged { text, _, _, _ ->
//            emailLiveData.value = text?.toString()
//        }
//
//        passwordLayout.doOnTextChanged { text, _, _, _ ->
//            passwordLiveData.value = text?.toString()
//        }
//
//        isValidLiveData.observe(this){ isValid ->
//            signInButton.isEnabled = isValid
//            setContentView(R.layout.activity_login)
//        }
    }

//    private fun validateForm(email: String?,password: String?) :Boolean {
//        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
//        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
//        return isValidEmail && isValidPassword
//    }
}