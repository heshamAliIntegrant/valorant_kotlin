package com.example.valorant_agents.features.agentsList.ViewModel

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomwordsample.Entities.toDtoList
import com.example.valorant_agents.features.agentsList.Models.Agent
import com.example.valorant_agents.features.agentsList.Repo.AgentsRepo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AgentListViewModel(private val agentsRepo: AgentsRepo): ViewModel() {
    val agentsSubject: PublishSubject<List<Agent>> = PublishSubject.create()
    val observableAgents: Observable<List<Agent>> get() = agentsSubject.observeOn(AndroidSchedulers.mainThread()).hide()

    fun loadAgents() {
        agentsRepo.loadAgentsLocally().observeForever(Observer {
            if(it.isEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val agents: List<Agent> = agentsRepo.loadAgentsRemotely()
                    agentsRepo.saveAgents(agents)
                    agentsSubject.onNext(agents)
                }
            }
            else {
                agentsSubject.onNext(it.toDtoList())
            }
        })
    }


}

class AgentListViewModelFactory(private val repository: AgentsRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}