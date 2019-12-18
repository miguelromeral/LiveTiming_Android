package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.DataItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.LiveTimingFragment
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.runnables.MyRunnable
import kotlinx.coroutines.*
import timber.log.Timber

class LiveTimingViewModel (var session: Game) : ViewModel() {


    private var uiHandler: LiveTimingFragment.UiHandler? = null

    private var _items = MutableLiveData<List<ItemLiveTiming>>()
    val items : LiveData<List<ItemLiveTiming>>
        get() = _items

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private lateinit var myHandlerThread: MyHandlerThread



    fun startRefreshing(handler: LiveTimingFragment.UiHandler){
        uiHandler = handler
        viewModelJob = Job()
        uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
        uiScope.launch {
            refreshing()
        }
        initHandlerThread()
    }

    fun initHandlerThread(){
        uiHandler?.let {
            myHandlerThread =
                MyHandlerThread(it)
            myHandlerThread.start()
        }
    }

    fun sortItemList(lista: List<ItemLiveTiming>? = null){
        synchronized(_items){
            val tosort = lista ?: _items.value

            _items.postValue(tosort?.sortedWith(compareBy(nullsLast<UByte>()){ it.position}))
        }
    }

    private var currentFrame: Int = 0

    suspend fun refreshing(){
        withContext(Dispatchers.Default) {
            while (true) {
                session.players.value?.let{ sessionItems ->

                    val mySize = items.value?.size
                    if(mySize == null || mySize != sessionItems.size){
                            var newList: MutableList<ItemLiveTiming> = mutableListOf()
                            var count = 0
                            for (p in sessionItems) {
                                newList.add(
                                    ItemLiveTiming(
                                        position =  p.currentLap.value?.carPosition?.value,
                                        name = p.participant.value?.shortName(),
                                        driverId = p.participant.value?.driverId?.value,
                                        fullname = p.participant.value?.name?.value,
                                        team = p.participant.value?.teamId?.value,
                                        time = p.currentLap.value?.currentLapTime?.value,
                                        compound = p.carStatus.value?.tyreCompound?.value,
                                        era = session.sessionData?.era?.value,
                                        format = p.participant.value?.format ?: Format.UNKNOWN
                                    )
                                )
                            }
                            sortItemList(newList)
                        }else{


                            /*if(currentFrame != session.frameId.value) {
                                currentFrame = session.frameId.value!!*/

                                _items.value?.let { myItems ->
                                    sortItemList()

                                    for (c in sessionItems.indices) {
                                        MyRunnable(
                                            myHandlerThread,
                                            myItems,
                                            session,
                                            c
                                        ).run()
                                    }

                                }
                            //}

                    }
                }
                Timber.i("Testing - Escuchando...!")
                delay(MyApplication.getPreferenceIntervalUpdate())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopRefreshing()
    }

    fun stopHandlerThread(){
        myHandlerThread.quit()
    }

    fun stopRefreshing(){
        viewModelJob.cancel()
    }

    companion object{
        const val DEFAULT_DELAY_TIME = 100
    }
}

