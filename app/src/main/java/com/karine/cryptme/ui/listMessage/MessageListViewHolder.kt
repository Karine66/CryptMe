package com.karine.cryptme.ui.listMessage

import androidx.recyclerview.widget.RecyclerView
import com.karine.common.CommonUtils
import com.karine.common.EncryptedMessage
import com.karine.cryptme.databinding.LayoutListItemBinding
import kotlinx.android.synthetic.main.layout_list_item.view.*

class MessageListViewHolder(private val layoutListItemBinding: LayoutListItemBinding) : RecyclerView.ViewHolder(layoutListItemBinding.root) {

    fun bind(index: Int, message: EncryptedMessage, listener: ListMessageActivity) =
        with(itemView) {
            textViewTitle.text = "Secret Message ${(index + 1)}"
            textViewTime.text = "Saved on: ${CommonUtils.longToDateString(message.savedAt)}"

            // RecyclerView on item click
            setOnClickListener {
                listener.onItemClick(message, it)
            }
        }
}