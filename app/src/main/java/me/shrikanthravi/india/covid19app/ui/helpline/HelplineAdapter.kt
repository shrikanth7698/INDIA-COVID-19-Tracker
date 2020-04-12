package me.shrikanthravi.india.covid19app.ui.helpline

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_helpline.view.*
import me.shrikanthravi.india.covid19app.R


class HelplineAdapter(private val states : ArrayList<Helpline>,
                      private val context: Context,
                      val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<HelplineAdapter.HelplineViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineViewHolder {
        return HelplineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_helpline, parent, false))
    }

    override fun getItemCount() = states.size

    override fun onBindViewHolder(holder: HelplineViewHolder, position: Int) {
        holder.bind(states[position],holder.adapterPosition,itemClickListener)
    }

    class HelplineViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val cardState = view.card_state
        val textState = view.text_state
        val textNumber = view.text_number
        val imageCall = view.image_call

        fun bind(helpline: Helpline, position: Int, itemClickListener: OnItemClickListener){
            textState.text = helpline.state
            textNumber.text = TextUtils.join(", ", helpline.phoneNumbers)
            cardState.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
            imageCall.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
            // Alternating row colors
            if((position+1)%2==0){
                cardState.setCardBackgroundColor(ContextCompat.getColor(cardState.context,R.color.grayBg))
            }else{
                cardState.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}