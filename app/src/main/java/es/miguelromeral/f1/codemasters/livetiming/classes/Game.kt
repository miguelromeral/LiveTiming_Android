package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.LapData
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketLapData
import es.miguelromeral.f1.codemasters.livetiming.packets.SessionData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import timber.log.Timber

class Game {

    var frameId = MutableLiveData(0)

    /*private*/ var _sessionData = Session()
    val sessionData : Session?
        get() = _sessionData


    /*private*/ var _players: MutableLiveData<MutableList<Player>>
            = MutableLiveData<MutableList<Player>>()
    val players : LiveData<MutableList<Player>>
        get() = _players

    /*fun newPacket(){
        _packetsCount.value = packetsCount.value?.plus(1)
        _sessionData = MutableLiveData<SessionData?>()
        Timber.i("Testing - Added new packet")
    }*/


    /*fun newEventData(event: EventData){
        newPacket()
        _eventData.value = event
    }*/


    fun getPlayerByPosition(position: Int?): Player?{
        position?.let {
            synchronized(players) {
                players.value?.let { list ->




                    return list.filter {
                        it.currentLap.value?.carPosition?.value == position.toUByte()
                    }.firstOrNull()
                }
            }
        }
        return null
    }


    @Synchronized
    fun newSessionData2018(session: SessionData){
        frameId.postValue(session.header.frameIdentifier)
        if(sessionData != null) {
            _sessionData.updateFrom2018(session)
        }
    }

    @Synchronized
    fun newLapData2018(lapdata: PacketLapData){
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
    fun newParticipants2018(info: PacketParticipantData){

        frameId.postValue(info.header.frameIdentifier)
        if(players.value?.size != info.numCars.toInt()){

            val list: MutableList<Player>
                = mutableListOf<Player>()

            for(p in info.participants){
                var inst = Player().apply {
                    newParticipant2018(p, info.header.frameIdentifier)
                }
                list.add(inst)
            }

            _players.postValue(list)
        }
    }

    @Synchronized
    fun newTelemetry2018(info: PacketCarTelemetryData){
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
    fun newData2017(info: Packet2017){
        sessionData?.let{
            _sessionData.updateFrom2017(info)
        }
    }

}