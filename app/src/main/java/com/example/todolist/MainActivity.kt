package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var todoTask : EditText
    private lateinit var addTaskButton : Button
    private lateinit var tasklist : ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoTask = findViewById(R.id.TodoET)
        addTaskButton = findViewById(R.id.addTaskBtn)
        tasklist = findViewById(R.id.todoListItems)

        val itemList : ArrayList<String>

        val fileHelper = FileSaver()
        itemList = fileHelper.readData(this)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        tasklist.adapter = arrayAdapter

        addTaskButton.setOnClickListener {

            val task = todoTask.text.toString()
            if (task.isNotEmpty()) {
                itemList.add(task)
                todoTask.setText("")
                fileHelper.writeData(itemList, applicationContext)
                arrayAdapter.notifyDataSetChanged()
            }else
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()

        }

        tasklist.setOnItemClickListener { _, _, pos, _ ->

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you really want to delete this Task")
            alert.setIcon(R.drawable.baseline_delete_24)
            alert.setCancelable(false)
            alert.setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            alert.setPositiveButton("Yes") { _, _ ->

                itemList.removeAt(pos)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)

            }
            alert.create().show()
        }


    }
}