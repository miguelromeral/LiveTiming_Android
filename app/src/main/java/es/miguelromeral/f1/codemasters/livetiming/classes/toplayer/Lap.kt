package es.miguelromeral.f1.codemasters.livetiming.classes.toplayer

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData

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

    var lastSector: UByte? = null

    var lastSector1Time: Float? = null
    var lastSector2Time: Float? = null
    var lastSector3Time: Float? = null


    fun updateFrom2018(info: LapData){
        synchronized(this) {
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
            advancedLap()
        }
    }

    fun advancedLap(){
        sector.value?.let {
            when (it.toInt()) {
                1 -> {
                    lastSector3Time =
                        if (lastLapTime.value == null || lastSector2Time == null || lastSector1Time == null)
                            null
                        else
                            (lastLapTime.value!! - lastSector2Time!! - lastSector1Time!!)
                }
                2 -> {
                    lastSector1Time = sector1Time.value
                }
                3 -> {
                    lastSector2Time = sector2Time.value
                }
            }
        }
        lastSector = sector.value
    }

    fun updateFrom2017(info: CarUDPData){
        synchronized(this){
            format = Format.F1_2017
            lastLapTime.postValue(info.lastLapTime)
            currentLapTime.postValue(info.currentLapTime)
            bestLapTime.postValue(info.bestLapTime)
            sector1Time.postValue(info.sector1Time)
            sector2Time.postValue(info.sector2Time)
            lapDistance.postValue(info.lapDistance)
            carPosition.postValue(info.carPosition.toUByte())
            currentLapNum.postValue(info.currentLapNum.toUByte())
            sector.postValue(info.sector.toUByte())
            currentLapInvalid.postValue(info.currentLapInvalid.toUByte())
            penalties.postValue(info.penalties.toUByte())
            advancedLap()
        }
    }
}