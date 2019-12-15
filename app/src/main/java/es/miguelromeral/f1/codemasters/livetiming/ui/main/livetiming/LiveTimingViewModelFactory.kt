package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import java.lang.IllegalArgumentException

class LiveTimingViewModelFactory(
    private val session: Game,
    private val uiHandler: LiveTimingFragment.UiHandler
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LiveTimingViewModel::class.java)){
            return LiveTimingViewModel(session, uiHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}