package com.zybooks.blorboquest

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.view.View.INVISIBLE
import com.zybooks.blorboquest.UpgradeOption
import com.zybooks.blorboquest.UpgradeOptionsAdapter
import android.widget.ArrayAdapter
import android.widget.ListView


class UpgradeOptionsFragment : Fragment() {

    val viewModel: UpgradeOptionsViewModel by activityViewModels()

    override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_upgrade_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listView)

        val upgradeOptions = listOf(
            UpgradeOption("Disclaimer", 0, "Cost adds by itself each time a multiplier is bought!!"),
            UpgradeOption("Money Laundering Upgrade", 600, "Upgrades money multiplier by x10"),
            UpgradeOption("Weapon Upgrade", 5000, "Unlocks kill button"),
            UpgradeOption("Autoclicker Upgrade", 5, "Adds 1 automatic cps per upgrade")
        )

        val adapter = UpgradeOptionsAdapter(requireContext(), upgradeOptions)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedOption = upgradeOptions[position]
            viewModel.selectUpgradeOption(selectedOption)
            // Notify the activity about the selected upgrade option
            (requireActivity() as MainActivity).handleUpgradeOption(selectedOption)
        }
    }
    fun showAllViews() {
        val rootLayout = requireActivity().findViewById<ViewGroup>(R.id.main)

        // Iterate through all child views and set visibility to VISIBLE
        for (i in 0 until rootLayout.childCount) {
            val child = rootLayout.getChildAt(i)
            child.visibility = View.VISIBLE
        }
    }
}
