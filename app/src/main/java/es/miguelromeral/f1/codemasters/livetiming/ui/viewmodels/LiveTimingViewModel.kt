package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.LiveTimingFragment
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.runnables.SectorsUpdater
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
                                val lap = p.currentLap.value
                                newList.add(
                                    ItemLiveTiming.create(
                                        participant = p.participant.value,
                                        lap = p.currentLap.value,
                                        carStatus = p.carStatus.value,
                                        session = session.sessionData.value,
                                        game = session)
                                )
                            }
                            sortItemList(newList)
                        }else{

                        /*
                            val keepupdating = session.format != Format.F1_2017

                            if(keepupdating &&
                                currentFrame != session.frameId.value) {

                                if(keepupdating)
                                    currentFrame = session.frameId.value!!


                         */
                                _items.value?.let { myItems ->
                                    sortItemList()

                                    for (c in sessionItems.indices) {
                                        SectorsUpdater(
                                            myHandlerThread,
                                            myItems,
                                            session,
                                            c
                                        ).run()
                                    }

                                }
//                            }

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

