package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData

class Player {


    /*private*/ var _currentLap = MutableLiveData<Lap>()
    val currentLap : LiveData<Lap>
        get() = _currentLap

    /*private*/ var _participant = MutableLiveData<Participant>()
    val participant : LiveData<Participant>
        get() = _participant

    /*private*/ var _telemetry = MutableLiveData<Telemetry>()
    val telemetry : LiveData<Telemetry>
        get() = _telemetry

    /*private*/ var _carStatus = MutableLiveData<CarStatus>()
    val carStatus : LiveData<CarStatus>
        get() = _carStatus


    /////////////////////////////////////////
    //                                     //
    //               2018                  //
    //                                     //
    /////////////////////////////////////////

    @Synchronized
    fun newLap2018(info: LapData, id: Int){
        var lap = getCurrentLapOrNew()
        lap.updateFrom2018(info)
        _currentLap.postValue(lap)
    }

    @Synchronized
    fun newParticipant2018(info: ParticipantData, era: Byte? = null){
        var part = getParticipantOrNew()
        part.updateFrom2018(info, era)
        _participant.postValue(part)
    }

    @Synchronized
    fun newCarStatus2018(info: CarStatusData){
        var carstatus = getCarStatusOrNew()
        carstatus.updateFrom2018(info)
        _carStatus.postValue(carstatus)
    }

    @Synchronized
    fun newTelemetry2018(info: CarTelemetryData){
        _telemetry.value?.updateFrom2018(info)
    }


    /////////////////////////////////////////
    //                                     //
    //               2017                  //
    //                                     //
    /////////////////////////////////////////


    @Synchronized
    fun newLap2017(info: CarUDPData){
        var lap = getCurrentLapOrNew()

        lap.updateFrom2017(info)
        _currentLap.postValue(lap)
    }

    @Synchronized
    fun newParticipant2017(info: CarUDPData, era: Byte? = null){
        var part = getParticipantOrNew()
        part.updateFrom2017(info, era)
        _participant.postValue(part)
    }

    @Synchronized
    fun newCarStatus2017(info: CarUDPData){
        val car = getCarStatusOrNew()
        car.updateFrom2017(info)
        _carStatus.postValue(car)
    }



    /////////////////////////////////////////
    //                                     //
    //             Creators                //
    //                                     //
    /////////////////////////////////////////



    private fun getCurrentLapOrNew() =
        if(currentLap.value == null){
            Lap()
        }else{
            _currentLap.value!!
        }

    private fun getParticipantOrNew() =
        if(participant.value == null){
            Participant()
        }else{
            _participant.value!!
        }

    private fun getCarStatusOrNew() =
        if(carStatus.value == null){
            CarStatus()
        }else{
            carStatus.value!!
        }
}