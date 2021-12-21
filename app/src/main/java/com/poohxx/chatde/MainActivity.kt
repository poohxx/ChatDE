package com.poohxx.chatde

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.poohxx.chatde.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: AdapterUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        setupActionBar()

        val database = Firebase.database
        val myRef = database.getReference("message")

        binding.btnSend.setOnClickListener{
            myRef.child(myRef.push().key?:"null"). setValue(User(auth.currentUser?.displayName, binding.edMessage.text.toString()))
        }
        onChangeListener(myRef)
        initRcView()
    }
    private fun initRcView() = with(binding){
        adapter = AdapterUser()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sign_out){
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun onChangeListener(dRef:DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                for(s in snapshot.children) {
                    val user = s.getValue(User::class.java)
                    if (user != null) list.add(user)
                    }
                    adapter.submitList(list)
                }

            override fun onCancelled(error: DatabaseError) {
            }

        })




    }

    private fun setupActionBar(){
        val ab = supportActionBar
        Thread {
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val dIcon = BitmapDrawable(resources, bMap)
            runOnUiThread {
            ab?.setDisplayHomeAsUpEnabled(true)
            ab?.setHomeAsUpIndicator(dIcon)
            ab?.title = auth.currentUser?.displayName
            }
        }.start()
    }
}