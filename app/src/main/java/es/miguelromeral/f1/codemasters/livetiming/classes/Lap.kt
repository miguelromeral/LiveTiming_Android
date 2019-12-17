package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.SessionData

@ExperimentalUnsignedTypes
class Lap {

    var format: Format = Format.UNKNOWN

    var lastLapTime = MutableLiveData<Float>(0f)
    var currentLapTime = MutableLiveData<Float>(0f)
    var bestLapTime = MutableLiveData<Float>(0f)
    var sector1Time = MutableLiveData<Float>(0f)
    var sector2Time = MutableLiveData<Float>(0f)
    var lapDistance = MutableLiveData<Float>(0f)
    var totalDistance = MutableLiveData<Float>(0f)
    var safetyCarDelta = MutableLiveData<Float>(0f)
    var carPosition = MutableLiveData<UByte>(0u)
    var currentLapNum = MutableLiveData<UByte>(0u)
    var pitStatus = MutableLiveData<UByte>(0u)
    var sector = MutableLiveData<UByte>(0u)
    var currentLapInvalid = MutableLiveData<UByte>(0u)
    var penalties = MutableLiveData<UByte>(0u)
    var gridPosition = MutableLiveData<UByte>(0u)
    var driverStatus = MutableLiveData<UByte>(0u)
    var resultStatus = MutableLiveData<UByte>(0u)



    fun updateFrom2018(info: LapData){
        synchronized(this) {
            val tmp_pos = info.carPosition
            val tmp_lap = info.currentLapTime
            format = Format.F1_2018
            lastLapTime.postValue(info.lastLapTime)
            currentLapTime.postValue(info.currentLapTime)
            bestLapTime.postValue(info.bestLapTime)
            sector1Time.postValue(info.sector1Time)
            sector2Time.postValue(info.sector2Time)
            lapDistance.postValue(info.lapDistance)
            totalDistance.postValue(info.totalDistance)
            safetyCarDelta.postValue(info.safetyCarDelta)
            carPosition.postValue(info.carPosition)
            currentLapNum.postValue(info.currentLapNum)
            pitStatus.postValue(info.pitStatus)
            sector.postValue(info.sector)
            currentLapInvalid.postValue(info.currentLapInvalid)
            penalties.postValue(info.penalties)
            gridPosition.postValue(info.gridPosition)
            driverStatus.postValue(info.driverStatus)
            resultStatus.postValue(info.resultStatus)
        }
    }
}