package com.poohxx.chatde

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.poohxx.chatde.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account =task.getResult(ApiException :: class.java)
                if(account!=null)
                    firebaseAuthByGoogle(account.idToken!!)
            } catch (e: ApiException){
                Toast.makeText(this,"catch", Toast.LENGTH_SHORT).show()

            }
        }

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnSignIn.setOnClickListener {
                signInByGoogle()
                checkAuthState()
            }
             }


        }
    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun signInByGoogle(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }
    private fun firebaseAuthByGoogle(idToken : String){
        val cridencial= GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(cridencial).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                checkAuthState()
            }
            else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkAuthState(){

        if(auth.currentUser != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

}