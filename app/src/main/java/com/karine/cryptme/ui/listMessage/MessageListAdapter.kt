package com.karine.cryptme.ui.listMessage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karine.common.CommonUtils
import com.karine.common.EncryptedMessage
import com.karine.cryptme.R
import kotlinx.android.synthetic.main.layout_list_item.view.*

class MessageListAdapter(
    private val items: List<EncryptedMessage>,
    private val clickListener: ListMessageActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: EncryptedMessage, itemView: View)
    }

    /**
     * Creates view for each item in the list
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds view with item info
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position, items[position], clickListener)
    }

    /**
     * Returns the size to item list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * View for item, sets item info and click events
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(index: Int, message: EncryptedMessage, listener: OnItemClickListener) =
            with(itemView) {
                textViewTitle.text = "Secret Message ${(index + 1)}"
                textViewTime.text = "Saved on: ${CommonUtils.longToDateString(message.savedAt)}"

                // RecyclerView on item click
                setOnClickListener {
                    listener.onItemClick(message, it)
                }
            }
    }

}