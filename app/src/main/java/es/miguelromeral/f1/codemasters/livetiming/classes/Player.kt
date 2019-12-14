package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData

class Player {

    var frameId = MutableLiveData(0)

    private var _currentLap = MutableLiveData<Lap>()
    val currentLap : LiveData<Lap>
        get() = _currentLap

    private var _participant = MutableLiveData<Participant>()
    val participant : LiveData<Participant>
        get() = _participant

    private var _telemetry = MutableLiveData<Telemetry>()
    val telemetry : LiveData<Telemetry>
        get() = _telemetry

    @Synchronized
    fun newLap2018(info: LapData, id: Int){
        var lap: Lap
        if(currentLap.value == null){
            lap = Lap()
        }else{
            lap = currentLap.value!!
        }
        lap.updateFrom2018(info)
        _currentLap.postValue(lap)
        frameId.postValue(id)
    }

    @Synchronized
    fun newParticipant2018(info: ParticipantData, id: Int){
        if(participant.value == null) {
            var part = Participant()
            part.updateFrom2018(info)
            _participant.postValue(part)
        }else{
            var part = _participant.value!!
            part.updateFrom2018(info)
            _participant.postValue(part)
        }
        frameId.postValue(id)
    }

    @Synchronized
    fun newTelemetry2018(info: CarTelemetryData){
        _telemetry.value?.updateFrom2018(info)
    }

}