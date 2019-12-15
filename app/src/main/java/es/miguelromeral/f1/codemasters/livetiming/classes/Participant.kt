package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData
import es.miguelromeral.f1.codemasters.livetiming.packets.stringFromPacket

class Participant {

    var format = Format.UNKNOWN

    var aiControlled = MutableLiveData<UByte>(0u)
    var driverId = MutableLiveData<UByte>(0u)
    var teamId = MutableLiveData<UByte>(0u)
    var raceNumber = MutableLiveData<UByte>(0u)
    var nationality = MutableLiveData<UByte>(0u)
    var name = MutableLiveData<String>()



    @Synchronized
    fun updateFrom2018(info: ParticipantData){
        format = Format.F1_2018
        aiControlled.postValue(info.aiControlled)
        driverId.postValue(info.driverId)
        teamId.postValue(info.teamId)
        raceNumber.postValue(info.raceNumber)
        nationality.postValue(info.nationality)
        name.postValue(info.name)
    }

    fun aiControlled() = when(format){
        Format.F1_2018 -> ParticipantData.getAiControlled(aiControlled.value!!)
        else -> "Unknown"
    }

    fun driver() = when(format){
        Format.F1_2018 -> ParticipantData.getDriver(driverId.value!!)
        else -> "Unknown"
    }

    fun team() = when(format){
        Format.F1_2018 -> ParticipantData.getTeam(teamId.value!!)
        else -> "Unknown"
    }
}