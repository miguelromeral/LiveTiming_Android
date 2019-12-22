package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData
import java.text.Format

class Participant {

    var format = Standard.UNKNOWN

    var aiControlled = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var driverId = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var teamId = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var raceNumber = MutableLiveData<UByte>(0u)
    var nationality = MutableLiveData<UByte>(0u)
    var name = MutableLiveData<String>()
    var era = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())



    @Synchronized
    fun updateFrom2018(info: ParticipantData, era: Byte? = null){
        format = Standard.FORMAT.F18
        aiControlled.postValue(info.getStandardAIControlled().toByte())
        driverId.postValue(info.getStandardDriverId().toByte())
        teamId.postValue(Standard.TEAMS.getStandardName2018(info.teamId).toByte())
        raceNumber.postValue(info.raceNumber)
        nationality.postValue(info.nationality)
        name.postValue(info.name)
        era?.let{
            this.era.postValue(it)
        }
    }

    @Synchronized
    fun updateFrom2017(info: CarUDPData, era: Byte? = null){
        format = Standard.FORMAT.F17
        driverId.postValue(info.getStandardDriverId(era).toByte())
        teamId.postValue(Standard.TEAMS.getStandardTeamName2017(info.teamId, era).toByte())
        name.postValue(null)
        era?.let{
            this.era.postValue(it)
        }
    }

    fun aiControlled(): String {
        MyApplication.getContext()?.resources?.let {
            return it.getString(
                when (aiControlled.value?.toInt()) {
                    Standard.AI.HUMAN -> R.string.ai_human
                    Standard.AI.AI -> R.string.ai_npc
                    else -> R.string.unknown
                })
        }
        return Standard.UNKNOWN_TEXT
    }

    fun driver(): String = name.value ?: driverByID()


    fun driverByID() = Standard.DRIVERS.name(driverId.value?.toInt())


    fun shortName(): String {
        var name = name.value ?: driver()

        val values = name.split(" ")
        val string
                = if(values.size > 1)
            values[1]
        else
            values[0]
        return string.substring(0, 3).toUpperCase()
    }

    fun team() = Standard.TEAMS.getTeamName(teamId.value?.toInt())
}