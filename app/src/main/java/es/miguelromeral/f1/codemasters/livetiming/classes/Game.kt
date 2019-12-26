package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.packets.EventData
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketLapData
import es.miguelromeral.f1.codemasters.livetiming.packets.SessionData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarStatusData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import timber.log.Timber
import java.text.Format

class Game {

    var frameId = MutableLiveData(0)
    var format = Standard.UNKNOWN

    /*private*/ var _sessionData: MutableLiveData<Session> = MutableLiveData(
        Session()
    )
    val sessionData : LiveData<Session>
        get() = _sessionData


    /*private*/ var _players: MutableLiveData<MutableList<Player>>
            = MutableLiveData<MutableList<Player>>()
    val players : LiveData<MutableList<Player>>
        get() = _players

    var bestSector1Time: Float? = null
    var bestSector2Time: Float? = null
    var bestSector3Time: Float? = null


    @Synchronized
    fun newSectorTime(sector: Int, value: Float){
        when(sector){
            1 -> {
                bestSector1Time = updateSectorValues(value, bestSector1Time)
            }
            2 -> {
                bestSector2Time = updateSectorValues(value, bestSector2Time)
            }
            3 -> {
                bestSector3Time = updateSectorValues(value, bestSector3Time)
            }
        }
    }

    @Synchronized
    fun bestLapTime(): Float? {
        var list = mutableListOf<Float>()
        players.value?.map{
            it.currentLap.value?.bestLapTime?.value?.let{ best ->
                if(best != INIT_BEST_TIME){
                    list.add(best)
                }
            }
        }
        return list.min()
    }


    fun getPlayerByNameOrID(name: String?, id: Byte?): Player?{
        synchronized(players) {
            players.value?.let { list ->
                if (name == null) {

                    val tmp = list.filter {
                        it.participant.value?.driverId?.value == id
                    }.firstOrNull()
                    return tmp

                } else {
                    val tmp = list.filter{
                        it.participant.value?.name?.value.equals(name)
                    }.firstOrNull()
                    return tmp
                }
            }
        }
        return null
    }


    @Synchronized
    fun newSessionData2018(session: SessionData){
        format = Standard.FORMAT.F18
        frameId.postValue(session.header.frameIdentifier)
        sessionData.value?.let{
            _sessionData.postValue(it.apply {
                updateFrom2018(session)
            })
        }
    }

    @Synchronized
    fun newLapData2018(lapdata: PacketLapData){
        format = Standard.FORMAT.F18
        frameId.postValue(lapdata.header.frameIdentifier)
        players.value?.let {
            if (it.size != 0) {
                for ((count, p) in players.value!!.withIndex()) {
                    synchronized(p) {
                        p.newLap2018(lapdata.lapData[count], lapdata.header.frameIdentifier)
                    }
                }
            }
        }
    }


    @Synchronized
    fun newCarStatus2018(info: PacketCarStatusData){
        format = Standard.FORMAT.F18
        frameId.postValue(info.header.frameIdentifier)
        players.value?.let {
            if (it.size != 0) {
                for ((count, p) in players.value!!.withIndex()) {
                    synchronized(p) {
                        p.newCarStatus2018(info.carStatusData[count])
                    }
                }
            }
        }
    }


    @Synchronized
    fun newParticipants2018(info: PacketParticipantData){
        format = Standard.FORMAT.F18
        frameId.postValue(info.header.frameIdentifier)
        var list = getPlayersOrNew(info.numCars.toInt())

        var count = 0
        for(p in list){
            p.newParticipant2018(info.participants[count], sessionData.value?.era?.value?.toByte())
            count++

        }
        _players.postValue(list)
    }

    @Synchronized
    fun newTelemetry2018(info: PacketCarTelemetryData){
        format = Standard.FORMAT.F18
        frameId.postValue(info.header.frameIdentifier)
        players.value?.let{
            if(it.isNotEmpty()){
                for((count, p) in _players.value!!.withIndex()){
                    p.newTelemetry2018(info.carTelemetryData[count])
                }
            }
        }
    }

    @Synchronized
    fun newEventData2018(info: EventData){
        Timber.i("Reseting Session")
        format = Standard.FORMAT.F18
        frameId.postValue(info.header.frameIdentifier)
        if(info.getStandardEventCode() == Standard.EVENT.END){
            resetingGame()
        }
    }

    private fun resetingGame(){
        _sessionData.postValue(Session())
        _players.postValue(mutableListOf<Player>())
    }


    fun newData2017(info: Packet2017){
        format = Standard.FORMAT.F17
        synchronized(_sessionData) {
            _sessionData.value?.let {
                it.updateFrom2017(info)
            }
        }
        synchronized(_players){
            var list = getPlayersOrNew(info.num_cars.toInt())

            var count = 0
            for(p in list){
                val car = info.car_data[count]
                p.newParticipant2017(car, sessionData.value?.era?.value?.toByte())
                p.newLap2017(car)
                p.newCarStatus2017(car)
                count++

            }
            _players.postValue(list)
        }
    }



    @Synchronized
    private fun getPlayersOrNew(count: Int): MutableList<Player> {
        players.value?.let{
            return it
        }
        var c = 0
        val list: MutableList<Player> = mutableListOf<Player>()
        while (c < count) {
            list.add(Player(this))
            c++
        }
        return list
    }


    companion object {

        const val INIT_BEST_TIME = 0f

        fun updateSectorValues(lastSector: Float?, bestSector: Float?): Float?{
            lastSector?.let {
                if(bestSector == null || lastSector < it){
                    Timber.i("Lap Checking --> UPDATED")
                    return it
                }else{
                    Timber.i("Lap Checking --> Not updated")
                    return bestSector
                }
            }
            Timber.i("Lap Checking --> Not updated")
            return null
        }
    }
}