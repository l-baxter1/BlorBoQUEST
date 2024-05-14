package com.zybooks.blorboquest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zybooks.blorboquest.UpgradeOption

class UpgradeOptionsAdapter(
    context: Context,
    private val upgradeOptions: List<UpgradeOption>
) : ArrayAdapter<UpgradeOption>(context, 0, upgradeOptions) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_upgrade_option, parent, false)
        }

        val option = upgradeOptions[position]

        val nameTextView: TextView = itemView!!.findViewById(R.id.optionName)
        val costTextView: TextView = itemView.findViewById(R.id.optionCost)
        val descriptionTextView: TextView = itemView.findViewById(R.id.optionDescription)

        nameTextView.text = option.name
        costTextView.text = option.cost.toString()
        descriptionTextView.text = option.description

        return itemView
    }
}
