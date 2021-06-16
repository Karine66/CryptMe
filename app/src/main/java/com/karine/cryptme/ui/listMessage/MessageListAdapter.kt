package com.karine.cryptme.ui.listMessage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.karine.common.CommonUtils
import com.karine.common.EncryptedMessage
import com.karine.cryptme.databinding.LayoutListItemBinding
import kotlinx.android.synthetic.main.layout_list_item.view.*

class MessageListAdapter(
    private val items: List<EncryptedMessage>,
    private val clickListener: ListMessageActivity
) : RecyclerView.Adapter<MessageListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: EncryptedMessage, itemView: View)
    }

    /**
     * Creates view for each item in the list
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_list_item, parent, false)
        val layoutListItemBinding = LayoutListItemBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return MessageListViewHolder(layoutListItemBinding)
    }

//    /**
//     * Binds view with item info
//     */
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as ViewHolder).bind(position, items[position], clickListener)
//    }

    /**
     * Returns the size to item list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        (holder as MessageListViewHolder).bind(position, items[position], clickListener)
    }

    /**
     * View for item, sets item info and click events
     */
//    class ViewHolder() : RecyclerView.ViewHolder() {
//
//        fun bind(index: Int, message: EncryptedMessage, listener: ListMessageActivity) =
//            with(itemView) {
//                textViewTitle.text = "Secret Message ${(index + 1)}"
//                textViewTime.text = "Saved on: ${CommonUtils.longToDateString(message.savedAt)}"
//
//                // RecyclerView on item click
//                setOnClickListener {
//                    listener.onItemClick(message, it)
//                }
//            }
//    }

}