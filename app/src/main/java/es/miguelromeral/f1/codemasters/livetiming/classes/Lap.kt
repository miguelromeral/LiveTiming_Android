package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.classes.Game.Companion.updateSectorValues
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData
import es.miguelromeral.f1.codemasters.livetiming.standard.SECTORS
import timber.log.Timber
import java.text.Format

@ExperimentalUnsignedTypes
class Lap {

    var format = Standard.UNKNOWN

    var lastLapTime = MutableLiveData<Float>(0f)
    var currentLapTime = MutableLiveData<Float>(0f)
    var bestLapTime = MutableLiveData<Float>(Game.INIT_BEST_TIME)
    var sector1Time = MutableLiveData<Float>(0f)
    var sector2Time = MutableLiveData<Float>(0f)
    var lapDistance = MutableLiveData<Float>(0f)
    var totalDistance = MutableLiveData<Float>(0f)
    var safetyCarDelta = MutableLiveData<Float>(0f)
    var carPosition = MutableLiveData<UByte>(0u)
    var currentLapNum = MutableLiveData<UByte>(0u)
    var pitStatus = MutableLiveData<Byte>(0)
    var sector = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var currentLapInvalid = MutableLiveData<UByte>(0u)
    var penalties = MutableLiveData<UByte>(0u)
    var gridPosition = MutableLiveData<UByte>(0u)
    var driverStatus = MutableLiveData<UByte>(0u)
    var resultStatus = MutableLiveData<UByte>(0u)

    var lastSector: Byte? = null

    var _lastSector1Time: Float? = null
    var _lastSector2Time: Float? = null
    var _lastSector3Time: Float? = null

    var lastSector1Time: Float?
        get() = _lastSector1Time
        set(value){
            Timber.i("Checking Sector 1")
            _lastSector1Time = value
            bestSector1Time = Game.updateSectorValues(value, bestSector1Time)
        }
    var lastSector2Time: Float?
        get() = _lastSector2Time
        set(value){
            Timber.i("Checking Sector 2")
            _lastSector2Time = value
            bestSector2Time = Game.updateSectorValues(value, bestSector2Time)
        }
    var lastSector3Time: Float?
        get() = _lastSector3Time
        set(value){
            Timber.i("Checking Sector 3")
            _lastSector3Time = value
            bestSector3Time = Game.updateSectorValues(value, bestSector3Time)
        }

    var bestSector1Time: Float? = null
    var bestSector2Time: Float? = null
    var bestSector3Time: Float? = null



    fun updateFrom2018(info: LapData){
        synchronized(this) {
            format = Standard.FORMAT.F18
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
            pitStatus.postValue(info.getStandardPitStatus().toByte())
            sector.postValue(info.getStandardSector().toByte())
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
                SECTORS.SECTOR_1 -> {
                    if(lastSector?.toInt() == SECTORS.SECTOR_3) {
                        Timber.i("Updating Last Sector 3: ")
                        lastSector3Time =
                            if (lastLapTime.value == null || lastSector2Time == null || lastSector1Time == null) {
                                Timber.i("Last sector 3 is null")
                                null
                            } else {
                                Timber.i("Last sector 3 is a value")
                                (lastLapTime.value!! - lastSector2Time!! - lastSector1Time!!)
                            }
                    }
                }
                SECTORS.SECTOR_2 -> {
                    if(lastSector?.toInt() == SECTORS.SECTOR_1) {
                        Timber.i("Updating Last Sector 1")
                        lastSector1Time = sector1Time.value
                    }
                }
                SECTORS.SECTOR_3 -> {
                    if(lastSector?.toInt() == SECTORS.SECTOR_2) {
                        Timber.i("Updating Last Sector 2")
                        lastSector2Time = sector2Time.value
                    }
                }
            }
        }
        lastSector = sector.value
    }

    fun updateFrom2017(info: CarUDPData){
        synchronized(this){
            format = Standard.FORMAT.F17
            lastLapTime.postValue(info.lastLapTime)
            currentLapTime.postValue(info.currentLapTime)
            bestLapTime.postValue(info.bestLapTime)
            sector1Time.postValue(info.sector1Time)
            sector2Time.postValue(info.sector2Time)
            lapDistance.postValue(info.lapDistance)
            carPosition.postValue(info.carPosition.toUByte())
            currentLapNum.postValue(info.currentLapNum.toUByte())
            sector.postValue(info.sector)
            currentLapInvalid.postValue(info.currentLapInvalid.toUByte())
            penalties.postValue(info.penalties.toUByte())
            advancedLap()
        }
    }
}