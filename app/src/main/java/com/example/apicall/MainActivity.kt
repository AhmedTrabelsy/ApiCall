package com.example.apicall

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class MainActivity() : AppCompatActivity() , UsersAdapter.OnItemClickListener{
    private val url = "https://jsonplaceholder.typicode.com/users"
    private var requestQueue: RequestQueue? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    var data = ArrayList<User>()
    private var selectedElement = 0
    lateinit var saver: SharedPreference



    override fun onCreate(savedInstanceState: Bundle?) {
        saver = SharedPreference(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestQueue = Volley.newRequestQueue(this)
        getData()
    }

    private fun getData() {
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val user = response.getJSONObject(i)
                        val name = user.getString("name")
                        val username = user.getString("username")
                        val email = user.getString("email")
                        val newUser = User(name, username, email)
                        data.add(newUser)
                    }
                    // Update the adapter with the new data
                    usersAdapter = UsersAdapter(data, this)
                    recyclerView.adapter = usersAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue?.add(request)
        recyclerView = findViewById(R.id.UsersRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usersAdapter = UsersAdapter(data, this)
        recyclerView.adapter = usersAdapter
    }

    override fun selectItem(position: Int) {
        Toast.makeText(this , "Item $position is selected ! ", Toast.LENGTH_SHORT).show()
        selectedElement = position
    }



    class SharedPreference(private val context: Context) {
        private val prefsName = "Mon Fishier Shared"
        private val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        fun save(keyName: String, text:String, ){
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(keyName, text)
            editor.apply()
        }
        fun save(keyName: String, value:Int){
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putInt(keyName, value)
            editor.apply()
        }
        fun save(keyName: String, status:Boolean){
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putBoolean(keyName, status)
            editor.apply()
        }

        fun getValueString(keyName: String): String? {
            return sharedPref.getString(keyName, null)
        }

        fun getValueInt(keyName: String): Int{
            return sharedPref.getInt(keyName, 0)
        }

        fun getStatus(keyName: String): Boolean{
            return sharedPref.getBoolean(keyName, false)
        }

        fun clearSharedPreference(){
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.clear()
            editor.apply()
        }

        fun removeValue(keyName: String){
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.remove(keyName)
            editor.apply()
        }

    }

    fun saveItem(view: View) {
        saver.save("name", (data[selectedElement].name))
        saver.save("username", data[selectedElement].username)
        saver.save("email", data[selectedElement].email)
        Toast.makeText(this, "User saved !", Toast.LENGTH_SHORT).show()
    }

    fun restoreItems(view: View) {
        val name = saver.getValueString("name")
        val username = saver.getValueString("username")
        val email = saver.getValueString("email")
        val intent: Intent = Intent(this, savedUsers::class.java)
        intent.putExtra("name", name)
        intent.putExtra("username", username)
        intent.putExtra("email", email)
        startActivity(intent)
        Toast.makeText(this, "User restored !", Toast.LENGTH_SHORT).show()
    }
}
