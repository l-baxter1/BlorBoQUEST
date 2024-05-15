package com.zybooks.blorboquest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class Email : Fragment() {
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_email_fragment, container, false)
        val mailActivity = activity as Mail
        val textId = mailActivity.textId
        val emailArray = resources.getStringArray(R.array.emailTextArray)
        // Initialize TextView
        emailTextView = view.findViewById(R.id.emailText)

        emailTextView.text = emailArray[textId]

        return view

    }





}
