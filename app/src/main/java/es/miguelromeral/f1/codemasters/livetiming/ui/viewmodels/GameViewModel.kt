package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.listener.Controller
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import kotlinx.coroutines.*
import timber.log.Timber

@kotlin.ExperimentalUnsignedTypes
class GameViewModel : ViewModel() {


    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var _controller: Controller

    var currentSession : Game

    //val ready = MutableLiveData<Boolean>(false)

    init{
        currentSession = Game()
        _controller = Controller(
            MyApplication.getPreferencePortUDP()
        ).apply {
            addCurrentSession(currentSession)
        }
    }

    @kotlin.ExperimentalUnsignedTypes
    fun startListeningUDP(){
        try {
            viewModelJob = Job()
            uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
            uiScope.launch {
                Timber.i("View Model - starting listening")
                _controller.listen()
            }
        }catch (e: Exception)
        {
            Timber.i("Exception while starting UDP: "+e.message)
        }
    }

    fun stopListeningUDP(){
        viewModelJob.cancel()
        Timber.i("View Model - Stop listening UDP")
    }

    override fun onCleared() {
        super.onCleared()
        stopListeningUDP()
    }
}