package com.example.mft12.pocket

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.member.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var intent = Intent(this, AddMember::class.java)
            startActivity(intent)
        }

        // this adapter to show the blocks which made in member layout
        var adapter:MembersAdapter?=null
        val dbMembers = DbManager(this)
        var Members = ArrayList<Member>()
        Members.add(Member(1,"Sara",2500.0))
        Members.add(Member(2,"Lamar",1200.0))
        Members.add(Member(3,"Abdulmalik",800.0))

        //insertion in database..
//        val ID = dbMembers.insert("FirstDB", 2500.0)
//        Toast.makeText(this, "The ID: $ID", Toast.LENGTH_LONG).show()
//        dbMembers.insert("LamarDB", 1200.0, this)
//        dbMembers.insert("AbdulmalikDB", 800.0)


        adapter = MembersAdapter(this, dbMembers.read(this))

        MembersGrid.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class MembersAdapter:BaseAdapter {

        var Members = ArrayList<Member>()
        var context: Context?=null

        constructor(context:Context, Members:ArrayList<Member>):super(){
            this.Members = Members
            this.context = context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val member = Members[p0]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var membersView = inflater.inflate(R.layout.member, null)
            membersView.Name.text = member.Name
            membersView.Money.text = member.Money.toString()

            // Action to add money
            membersView.AddMoney.setOnClickListener {
                member.Money += membersView.MoneyField.text.toString().toDouble()
                membersView.Money.text = member.Money.toString()
                membersView.MoneyField.text = null

            }
            // Action to Decrease Money
            membersView.DeleteMoney.setOnClickListener {
                if(member.Money >= membersView.MoneyField.text.toString().toDouble()) {
                    member.Money -= membersView.MoneyField.text.toString().toDouble()
                    membersView.Money.text = member.Money.toString()
                    membersView.MoneyField.text = null
                } else {
                    Toast.makeText(context, "Enter correct information", Toast.LENGTH_LONG).show()
                }

            }

            return membersView
        }

        override fun getItem(p0: Int): Any {
            return Members[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return Members.size
        }



    }
}
