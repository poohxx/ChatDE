package com.poohxx.chatde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.poohxx.chatde.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("message")

        binding.btnSend.setOnClickListener{
            myRef.setValue(binding.edMessage.text.toString())
        }
        onChangeListener(myRef)


    }
    private fun onChangeListener(dRef:DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    rcView.append("\n")
                    rcView.append("Ayrat: ${snapshot.value.toString()}")
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}