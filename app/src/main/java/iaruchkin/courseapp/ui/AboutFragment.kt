package iaruchkin.courseapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import iaruchkin.courseapp.R

import iaruchkin.courseapp.ui.MainActivity.Companion.ABOUT_TAG

class AboutFragment : Fragment() {

    private var mMessageEditText: EditText? = null
    private var listener: MessageFragmentListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.about_activity, container, false)

        setActionBar(view)
        findViews(view)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MessageFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    private fun findViews(view: View) {
        mMessageEditText = view.findViewById(R.id.et_message)
        val mSendButton = view.findViewById<TextView>(R.id.b_send)
        val mTelegramLogo = view.findViewById<ImageView>(R.id.telegram_link)
        val mInsagramLogo = view.findViewById<ImageView>(R.id.instagram_link)
        val mVkLogo = view.findViewById<ImageView>(R.id.vk_link)

        mSendButton.setOnClickListener { v -> composeEmail(mMessageEditText!!.text.toString()) }
        mTelegramLogo.setOnClickListener { v -> openURL(getString(R.string.telegram_link)) }
        mInsagramLogo.setOnClickListener { v -> openURL(getString(R.string.instagram_link)) }
        mVkLogo.setOnClickListener { v -> openURL(getString(R.string.vk_link)) }
    }

    private fun openURL(url: String) {
        val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(url))

        if (intent.resolveActivity(context!!.packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, getString(R.string.error_no_browser), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setActionBar(view: View) {
        setHasOptionsMenu(true)
        (context as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        val actionBar = (context as AppCompatActivity).supportActionBar
        actionBar!!.setTitle(getString(R.string.name_res))
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    fun composeEmail(messageEmail: String) {

        Log.i(ABOUT_TAG, "composeEmail")

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse(String.format("mailto:%s", getString(R.string.email_adress))) // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
        intent.putExtra(Intent.EXTRA_TEXT, messageEmail)
        if (intent.resolveActivity(context!!.packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, R.string.error_no_email_app, Toast.LENGTH_LONG).show()
        }
    }
}

