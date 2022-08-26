package com.appdeviser.tictactoe.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.databinding.RecentresultSingleMatchLayoutBinding
import com.appdeviser.tictactoe.ui.viewmodel.RecentResultViewModel

//class RecentResultAdapter(val resultList : ArrayList<Match>?) : RecyclerView.Adapter<RecentResultAdapter.ViewHolder>() {
//
//    val TAG = "TAG_RecentResultAdapter"
//
//    //this method is returning the view for each item in the list
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        Log.d(TAG, "onCreateViewHolder()")
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.recentresult_single_match_layout, parent, false)
//        return ViewHolder(v)
//    }
//
//    //this method is binding the data on the list
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d(TAG, "onBindViewHolder()")
//        Log.d("Recycle: " , resultList?.get(position)?.toString())
//        resultList?.get(position)?.let { holder.bindItems(it) }
//    }
//
//    //this method is giving the size of the list
//    override fun getItemCount(): Int {
//        if (resultList != null) {
//            return resultList.size
//        }
//        return 0
//    }
//
//    //the class is hodling the list view
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bindItems(match: Match) {
//            Log.d("RecentResultAdapter-ViewHolder", "bindItems()")
//
//
//            println(
//                " RecentResultAdapter-ViewHolder Match:" + "id : " + match.id +
//                        " p1 : " + match?.playerone +
//                        " p2 : " + match.playertwo +
//                        " mode : " + match.mode +
//                        " result : " + match.result +
//                        " date : " + match.date
//            )
//
//            var textMatchMode = itemView.findViewById(R.id.textMatchMode) as TextView
//            var textMatchPlayerOne = itemView.findViewById(R.id.textMatchPlayerOne) as TextView
//            var textMatchPlayerTwo = itemView.findViewById(R.id.textMatchPlayerTwo) as TextView
//            var textMatchWinner = itemView.findViewById(R.id.textMatchWinner) as TextView
//            var textMatchDate = itemView.findViewById(R.id.textMatchDate) as TextView
//
//
//
//            textMatchMode.text = match.mode
//            textMatchPlayerOne.text = match.playerone
//            textMatchPlayerTwo.text = match.playertwo
//            textMatchWinner.text = match.result
//            textMatchDate.text = match.date
//        }
//    }
//}


class RecentResultAdapter(private val items: ArrayList<Match>) : RecyclerView.Adapter<RecentResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecentresultSingleMatchLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: RecentresultSingleMatchLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Match) {
            binding.match = item
            binding.executePendingBindings()
        }
    }
}
