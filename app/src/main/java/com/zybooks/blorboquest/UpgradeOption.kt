package com.zybooks.blorboquest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zybooks.blorboquest.UpgradeOption
import com.zybooks.blorboquest.UpgradeOptionsAdapter

data class UpgradeOption(
    val name: String,
    val cost: Int,
    val description: String
)