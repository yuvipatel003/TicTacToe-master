package com.appdeviser.tictactoe.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.adapter.RecentResultAdapter
import com.appdeviser.tictactoe.data.AppDatabase
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.data.MatchDao
import com.appdeviser.tictactoe.databinding.ActivityRecentResultBinding
import com.appdeviser.tictactoe.ui.viewmodel.RecentResultViewModel
import kotlinx.android.synthetic.main.activity_recent_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentResultActivity : AppCompatActivity() {

    private val TAG = RecentResultActivity::class.java.simpleName

    lateinit var mModel: RecentResultViewModel
    private lateinit var binding: ActivityRecentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate()")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recent_result)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        mModel = ViewModelProvider(this).get(RecentResultViewModel::class.java)
        mModel.recentResultList.observe(this, Observer {
            binding.recyclerView.adapter = RecentResultAdapter(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

        btnLoadAll.setOnClickListener {
           mModel.getAllMatches()
        }
    }
}
