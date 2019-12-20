package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.classes.Player
import timber.log.Timber

class SetupViewModel(var session: Game) : ViewModel() {

    private var _index = 0
    private var _list: List<Player>? = null

    private var _names = MutableLiveData<MutableList<String>>(mutableListOf())
    val names : LiveData<MutableList<String>>
        get() = _names

    private var _selectedName = MutableLiveData<String?>(null)
    val selectedName : LiveData<String?>
        get() = _selectedName

    private var _monitoring: MutableLiveData<Player> = MutableLiveData()
    val monitoring : LiveData<Player>
        get() = _monitoring
/*
    private var _updateRequired = MutableLiveData<Boolean>(false)
    val updateRequired : LiveData<Boolean>
        get() = _updateRequired
*/
    fun updateItems(list: List<Player>?): MutableList<String> {
        try {
            _list = list
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
                p.participant.value?.driver().let{
                    if(it == null)
                        throw Exception("no name provided for player")

                    _names.value?.add(it)
                }
            }
            return true
        }catch (e: Exception){
            _names.postValue(mutableListOf())
            return false
        }
    }

    fun setSelectedItem(index: Int, name: String, player: Player){
        _index = index
        _selectedName.postValue(name)
        _monitoring.postValue(player)
    }
}