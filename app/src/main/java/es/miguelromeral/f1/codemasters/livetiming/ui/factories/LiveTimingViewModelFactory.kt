package es.miguelromeral.f1.codemasters.livetiming.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.LiveTimingViewModel
import java.lang.IllegalArgumentException

class LiveTimingViewModelFactory(
    private val session: Game
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LiveTimingViewModel::class.java)){
            return LiveTimingViewModel(
                session
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}