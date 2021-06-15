package com.karine.cryptme.ui.listMessage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karine.common.EncryptedMessage
import com.karine.cryptme.R
import com.karine.cryptme.databinding.ActivityListMessageBinding
import com.karine.cryptme.ui.Decrypt.DecryptActivity
import com.karine.utils.PreferenceUtil
import kotlinx.android.synthetic.main.content_list.*

class ListMessageActivity : AppCompatActivity() {

    private lateinit var listMessageBinding: ActivityListMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       listMessageBinding = ActivityListMessageBinding.inflate(layoutInflater)
        val view = listMessageBinding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
    override fun onResume() {
        super.onResume()

        // Set Adapter
        showMessageList()
    }

    override fun onItemClick(item: EncryptedMessage, itemView: View) {
        val decryptionIntent = (Intent(this, DecryptActivity::class.java))
        decryptionIntent.putExtra(getString(R.string.parcel_message), item)
        startActivity(decryptionIntent)
    }

    private fun showMessageList() {
        val messageList = PreferenceUtil.getMessageList(applicationContext)
        if (!messageList.isNullOrEmpty()) {
            textViewNoMessage.visibility = View.GONE
            recyclerView.adapter = MessageListAdapter(messageList, this)
        }
    }

}