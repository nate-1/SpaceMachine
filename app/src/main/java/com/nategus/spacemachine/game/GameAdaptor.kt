package com.nategus.spacemachine.game

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.nategus.spacemachine.Element
import com.nategus.spacemachine.ElementType
import com.nategus.spacemachine.R

class SwitchAdaptor(private val element: List<Element>, private val onChange: (Int, Boolean) -> Unit): RecyclerView.Adapter<SwitchAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val label = view.findViewById<TextView>(R.id.switch_title)
        val switch = view.findViewById<SwitchCompat>(R.id.switch_element)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.switch_game_element, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val el = element[position]
        holder.label.text = el.name
        holder.switch.isChecked = el.value

        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            onChange(position, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return element.count()
    }
}

class ButtonAdaptor(private val element: List<Element>, private val onChange: (Int) -> Unit): RecyclerView.Adapter<ButtonAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val button = view.findViewById<TextView>(R.id.button_element)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.button_game_element, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.button.text = element[position].name

        holder.button.setOnClickListener{
            onChange(position)
        }

    }

    override fun getItemCount(): Int {
        return element.count()
    }
}
