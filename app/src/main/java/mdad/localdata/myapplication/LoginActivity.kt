package mdad.localdata.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var loginEmail: EditText? = null
    private var loginPassword: EditText? = null
    private var signupRedirectText: TextView? = null
    private var loginButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById<TextView>(R.id.signUpRedirectText)
        loginButton?.setOnClickListener(View.OnClickListener {
            val email = loginEmail?.getText().toString()
            val pass = loginPassword?.getText().toString()
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!pass.isEmpty()) {
                    auth!!.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    loginPassword?.setError("Password cannot be empty")
                }
            } else if (email.isEmpty()) {
                loginEmail?.setError("Email cannot be empty")
            } else {
                loginEmail?.setError("Please enter a valid email")
            }
        })
        signupRedirectText?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        })
    }
}