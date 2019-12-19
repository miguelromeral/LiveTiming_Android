package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.classes.toplayer.Game
import es.miguelromeral.f1.codemasters.livetiming.classes.toplayer.Player
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import timber.log.Timber

class SetupViewModel(var session: Game) : ViewModel() {


    private var _names = MutableLiveData<MutableList<String>>(mutableListOf())
    val names : LiveData<MutableList<String>>
        get() = _names

    private var _selectedName = MutableLiveData<String?>(null)
    val selectedName : LiveData<String?>
        get() = _selectedName



    fun updateItems(list: List<Player>?): MutableList<String> {
        try {
            list?.let {
                if(_names.value == null || _names.value?.size != list.size){
                    createItems(list)
                }
                return _names.value!!
            }
        }catch(e: Exception){
            Timber.i("Exception creating spinner items")
        }
        _names.postValue(mutableListOf())
        return mutableListOf()
    }

    private fun createItems(list: List<Player>): Boolean{
        try {
            _names.value?.clear()
            for (p in list) {
                p.participant.value?.name?.value?.let{
                    _names.value?.add(it)
                }
            }
            return true
        }catch (e: Exception){
            _names.postValue(mutableListOf())
            return false
        }
    }

    fun setSelectedItem(name: String? = null){
        _selectedName.postValue(name)
    }

}