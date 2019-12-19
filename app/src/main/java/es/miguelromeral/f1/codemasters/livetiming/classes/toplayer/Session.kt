package es.miguelromeral.f1.codemasters.livetiming.classes.toplayer

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.MarshalZone
import es.miguelromeral.f1.codemasters.livetiming.packets.SessionData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017

@ExperimentalUnsignedTypes
class Session {

    var format: Format = Format.UNKNOWN

    // Weather Info
    var weather = MutableLiveData<UByte>(0u)
    var trackTemperature = MutableLiveData<Byte>(0)
    var airTemperature = MutableLiveData<Byte>(0)

    // Session Info
    var sessionType = MutableLiveData<UByte>(0u)
    var era = MutableLiveData<UByte>(0u)
    var sessionTimeLeft = MutableLiveData<Short>(0)
    var sessionDuration = MutableLiveData<Short>(0)
    var safetyCarStatus = MutableLiveData<UByte>(0u)
    var networkGame: UByte = 0u

    // Grand Prix Info
    var totalLaps = MutableLiveData<UByte>(0u)
    var trackLength = MutableLiveData<Short>(0)
    var trackId = MutableLiveData<Byte>(-1)
    var numMarshalZones: UByte = 0u
    var marshalZones : List<MarshalZone> = listOf()

    var pitSpeedLimit: UByte = 0u



    var gamePaused  = MutableLiveData<UByte>(0u)

    var isSpectating: UByte = 0u
    var spectatorCardIndex: UByte = 0u

    var sliProNativeSupport: UByte = 0u

    @Synchronized
    fun updateFrom2018(info: SessionData){
        format = Format.F1_2018
        weather.postValue(info.weather)
        trackTemperature.postValue(info.trackTemperature)
        airTemperature.postValue(info.airTemperature)
        totalLaps.postValue(info.totalLaps)
        trackLength.postValue(info.trackLength)
        sessionType.postValue(info.sessionType)
        trackId.postValue(info.trackId)
        era.postValue(info.era)
        sessionTimeLeft.postValue(info.sessionTimeLeft)
        sessionDuration.postValue(info.sessionDuration)
        pitSpeedLimit = info.pitSpeedLimit
        gamePaused.postValue(info.gamePaused)
        isSpectating = info.isSpectating
        spectatorCardIndex = info.spectatorCardIndex
        sliProNativeSupport = info.sliProNativeSupport
        numMarshalZones = info.numMarshalZones
        marshalZones = info.marshalZones
        safetyCarStatus.postValue(info.safetyCarStatus)
        networkGame = info.networkGame
    }

    @Synchronized
    fun updateFrom2017(info: Packet2017){
        format = Format.F1_2017
        trackId.postValue(info.track_number.toByte())
        //totalLaps = info.total_laps.toUByte()
    }

    fun weatherAsInt() = weather.value?.toInt()



    fun weather(): String = when(format){
            Format.F1_2018 -> SessionData.getWeather(weather.value!!)
            else -> "Unknown"
        }

    fun track(): String = when(format) {
        Format.F1_2017 -> Packet2017.getTrack(trackId.value!!)
        Format.F1_2018 -> SessionData.getTrack(trackId.value!!)
        else -> "Unknown"
    }

    fun sessionType(): String = when(format){
        Format.F1_2018 -> SessionData.getSessionType(sessionType.value!!)
        else -> "Unknown"
    }

    fun safetyCarStatus(): String = when(format){
        Format.F1_2018 -> SessionData.getSafetyCarStatus(safetyCarStatus.value)
        else -> "Unknown"
    }

    fun safetycarStatusAsInt() = safetyCarStatus.value?.toInt()

    fun networkGame(): String = when(format){
        Format.F1_2018 -> SessionData.getNateworkGame(networkGame)
        else -> "Unknown"
    }

    fun era(): String = when(format){
        Format.F1_2018 -> SessionData.getEra(era.value!!)
        else -> "Unknown"
    }
}