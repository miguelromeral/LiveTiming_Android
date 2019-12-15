package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.classes.Player
import kotlinx.coroutines.*
import timber.log.Timber

class LiveTimingViewModel (var session: Game, val uiHandler: LiveTimingFragment.UiHandler) : ViewModel() {


    private var _items = MutableLiveData<List<ItemLiveTiming>>()
    val items : LiveData<List<ItemLiveTiming>>
        get() = _items


    private var _modifiedItems = MutableLiveData<Boolean>(false)
    val modifiedItems : LiveData<Boolean>
        get() = _modifiedItems

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var uiScopeRepeat = CoroutineScope(Dispatchers.Default + viewModelJob)

    private lateinit var myHandlerThread: MyHandlerThread

    // ONLY DEBUG
    /*init{
        _items.postValue(listOf(
            ItemLiveTiming(name = "Miguel", position = 12.toUByte(), team = 1.toUByte(), time = 79.123f),
            ItemLiveTiming(name = "Chechu", position = 19.toUByte(), team = 1.toUByte(), time = 19.123f),
            ItemLiveTiming(name = "Romeral", position = 4.toUByte(), team = 1.toUByte(), time = 69.123f),
            ItemLiveTiming(name = "Javi", position = 9.toUByte(), team = 1.toUByte(), time = 49.123f)))

    }*/



    fun startRefreshing(){
        viewModelJob = Job()
        uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
        uiScope.launch {
            refreshing()
        }
        initHandlerThread()
    }

    fun initHandlerThread(){
        myHandlerThread = MyHandlerThread(uiHandler)
        myHandlerThread.start()
    }

    fun sortItemList(lista: List<ItemLiveTiming>? = null){
        synchronized(_items){
            val tosort = lista ?: _items.value

            _items.postValue(tosort?.sortedWith(compareBy(nullsLast<UByte>()){ it.position}))
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
                            for (p in sessionItems) {
                                newList.add(
                                    ItemLiveTiming(
                                        p.currentLap.value?.carPosition?.value,
                                        p.participant.value?.name?.value,
                                        p.participant.value?.teamId?.value,
                                        p.currentLap.value?.currentLapTime?.value
                                    )
                                )
                                Timber.i("Añadido: ${p.participant.value?.name?.value?.substring(3,12)} con carPosition a: ${p.currentLap.value?.carPosition?.value}")
                            }
                            sortItemList(newList)
                        }else{

                            _items.value?.let{ myItems ->
                                sortItemList()

                                var c = 0
                                for(p in sessionItems){
                                    var tmp = myItems?.get(c)
                                    tmp.let{ item ->
                                        MyRunnable(myHandlerThread, item, session).run()
                                    }
                                    c++
                                }

                            }


                        //_modifiedItems.postValue(true)
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

    fun stopHandlerThread(){
        myHandlerThread.quit()
    }

    fun stopRefreshing(){
        viewModelJob.cancel()
    }

    companion object{
        const val DELAY_TIME = 100L
    }
}