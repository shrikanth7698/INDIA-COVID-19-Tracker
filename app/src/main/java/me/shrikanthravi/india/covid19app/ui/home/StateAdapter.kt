package me.shrikanthravi.india.covid19app.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_state.view.*
import me.shrikanthravi.india.covid19app.R
import me.shrikanthravi.india.covid19app.data.model.Statewise


class StateAdapter(private val states : ArrayList<Statewise>,
                   private val context: Context,
                   val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<StateAdapter.StateViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        return StateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_state, parent, false))
    }

    override fun getItemCount() = states.size

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(states[position],holder.adapterPosition,itemClickListener)
    }

    class StateViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val cardState = view.card_state
        val textState = view.text_state
        val textConfirmedValue = view.text_confirmed_value
        fun bind(state: Statewise, position: Int, itemClickListener: OnItemClickListener){
            textState.text = state.state
            textConfirmedValue.text = state.confirmed
            cardState.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
            // Alternating row colors
            if((position+1)%2==0){
                textState.setBackgroundColor(ContextCompat.getColor(cardState.context,R.color.grayBgLight))
                textConfirmedValue.setBackgroundColor(ContextCompat.getColor(cardState.context,R.color.grayBgLight))
            }else{
                textState.setBackgroundColor(Color.TRANSPARENT)
                textConfirmedValue.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}