package com.example.dbmkotlin.Model

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dbmkotlin.DetailActivity
import com.example.dbmkotlin.R
import org.jetbrains.anko.db.select

class Myfragment: DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View=inflater.inflate(R.layout.dialog_fragment, container,false)
        var buscarButton= rootView.findViewById<Button>(R.id.btnBuscar)
        var buscarEdt= rootView.findViewById<EditText>(R.id.edtBuscar)

        buscarButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(buscarEdt.text.toString().equals("")){
                    Toast.makeText(rootView.context,"llene la casilla buscar", Toast.LENGTH_SHORT).show()

                }else{
                    val db=DBOpenHelper.getInstance(rootView.context)
                    db?.use {
                        select("Popular").exec {
                            if(this.count!=0){
                                this.moveToFirst()
                                do{
                                    if(buscarEdt.text.toString().equals(this.getString(1))){
                                        val ubi="detail"
                                        val intent= Intent(rootView.context, DetailActivity::class.java)
                                        intent.putExtra("titulo", this.getString(1))
                                        intent.putExtra("año", this.getString(2))
                                        intent.putExtra("votes", this.getDouble(5))
                                        intent.putExtra("desc", this.getString(3))
                                        intent.putExtra("imagen", this.getString(4))
                                        intent.putExtra("id", getInt(0))
                                        intent.putExtra("ubicacion", ubi)
                                        startActivity(intent)
                                        dismiss()


                                    }
                                }while(moveToNext())

                            }
                        }
                    }

                }
            }

        })
        return rootView
    }

}