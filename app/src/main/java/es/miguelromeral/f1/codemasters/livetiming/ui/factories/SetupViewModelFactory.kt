package es.miguelromeral.f1.codemasters.livetiming.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.SetupViewModel
import java.lang.IllegalArgumentException

class SetupViewModelFactory (
    private val session: Game
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetupViewModel::class.java)){
            return SetupViewModel(
                session
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}