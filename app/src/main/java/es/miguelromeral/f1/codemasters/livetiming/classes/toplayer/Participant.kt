package es.miguelromeral.f1.codemasters.livetiming.classes.toplayer

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData

class Participant {

    var format = Format.UNKNOWN

    var aiControlled = MutableLiveData<Byte>(TopLayer.UNKNOWN.toByte())
    var driverId = MutableLiveData<UByte>(0u)
    var teamId = MutableLiveData<UByte>(0u)
    var raceNumber = MutableLiveData<UByte>(0u)
    var nationality = MutableLiveData<UByte>(0u)
    var name = MutableLiveData<String>()
    var era = MutableLiveData<UByte>(0u)



    @Synchronized
    fun updateFrom2018(info: ParticipantData, era: UByte? = null){
        format = Format.F1_2018
        aiControlled.postValue(info.getAiControlledTopLayer().toByte())
        driverId.postValue(info.driverId)
        teamId.postValue(info.teamId)
        raceNumber.postValue(info.raceNumber)
        nationality.postValue(info.nationality)
        name.postValue(info.name)
        era?.let{
            this.era.postValue(it)
        }
    }

    @Synchronized
    fun updateFrom2017(info: CarUDPData, era: UByte? = null){
        format = Format.F1_2017
        driverId.postValue(info.driverId.toUByte())
        teamId.postValue(info.teamId.toUByte())
        name.postValue(null)
        era?.let{
            this.era.postValue(it)
        }
    }

    fun aiControlled(): String {
        MyApplication.getContext()?.resources?.let {
            return it.getString(
                when (aiControlled.value?.toInt()) {
                    TopLayer.AIMode.HUMAN -> R.string.ai_human
                    TopLayer.AIMode.AI -> R.string.ai_npc
                    else -> R.string.unknown
                })
        }
        return TopLayer.UNKNOWN_TEXT
    }

    fun driver(era: UByte? = Packet2017.ERA_MODERN.toUByte()) = when(format){
        Format.F1_2017 -> CarUDPData.getDriver(driverId.value!!, era!!)
        Format.F1_2018 -> ParticipantData.getDriver(driverId.value!!)
        else -> "Unknown"
    }

    fun shortName(): String {
        var name = name.value ?: driver(era.value)

        val values = name.split(" ")
        val string
                = if(values.size > 1)
            values[1]
        else
            values[0]
        return string.substring(0, 3).toUpperCase()
    }

    fun team(era: UByte) = when(format){
        Format.F1_2017 -> CarUDPData.getTeam(teamId.value!!, era)
        Format.F1_2018 -> ParticipantData.getTeam(teamId.value!!)
        else -> "Unknown"
    }

    fun nationality() = when(format){
        Format.F1_2018 -> ParticipantData.getNationality(nationality.value!!)
        else -> "Unknown"
    }
}