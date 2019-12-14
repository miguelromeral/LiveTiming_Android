package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import kotlinx.coroutines.*

class LiveTimingViewModel (var session: Game) : ViewModel() {


    private var _items = MutableLiveData<List<ItemLiveTiming>>()
    val items : LiveData<List<ItemLiveTiming>>
        get() = _items


    private var _modifiedItems = MutableLiveData<Boolean>(false)
    val modifiedItems : LiveData<Boolean>
        get() = _modifiedItems

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun startRefreshing(){
        viewModelJob = Job()
        uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        uiScope.launch {
            refreshing()
        }
    }

    suspend fun refreshing(){
        withContext(Dispatchers.Default) {
            while (true) {
                session.players.value?.let{ sessionItems ->

                    val mySize = items.value?.size
                    if(mySize == null || mySize != sessionItems.size){
                            var newList: MutableList<ItemLiveTiming> = mutableListOf()
                            var count = 0
                            for(p in sessionItems){
                                newList.add(ItemLiveTiming(
                                    p.currentLap.value?.carPosition?.value,
                                    p.participant.value?.name?.value,
                                    p.participant.value?.teamId?.value,
                                    p.currentLap.value?.currentLapTime?.value))
                            }
                            _items.postValue(newList)
                        }else{
                            var count = 0
                            for(item in _items.value!!){
                                item.time = session.players.value?.get(count)?.currentLap?.value?.currentLapTime?.value
                                count++
                            }

                            // Comprobar si esto lo reordena
                            _items.postValue(items.value?.sortedBy { it -> it.position })

                            _modifiedItems.postValue(true)

                    }
                }
                delay(DELAY_TIME)
            }
        }
    }


    fun endUpdate(){
        _modifiedItems.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        stopRefreshing()
    }


    fun stopRefreshing(){
        viewModelJob.cancel()
    }

    companion object{
        const val DELAY_TIME = 50L
    }
}