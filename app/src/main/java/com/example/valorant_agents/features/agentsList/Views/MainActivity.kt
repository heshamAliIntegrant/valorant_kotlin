package com.example.valorant_agents.features.agentsList.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorant_agents.R
import com.example.valorant_agents.application.ValorantApplication
import com.example.valorant_agents.databinding.ActivityMainBinding
import com.example.valorant_agents.features.agentsList.ViewModel.AgentListViewModel
import com.example.valorant_agents.features.agentsList.ViewModel.AgentListViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val agentListViewModel: AgentListViewModel by viewModels {
        AgentListViewModelFactory((application as ValorantApplication).repository)
    }
    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CharacterGridAdapter()
        val recyclerView = binding.charactersGrid
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        agentListViewModel.observableAgents.subscribeBy(onNext = {
            adapter.submitList(it)
        }).addTo(disposables)
        agentListViewModel.loadAgents()

    }
}