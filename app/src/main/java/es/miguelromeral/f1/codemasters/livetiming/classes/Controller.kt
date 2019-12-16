package es.miguelromeral.f1.codemasters.livetiming.classes

import es.miguelromeral.f1.codemasters.livetiming.packets.*
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.*

class Controller(val port: Int = 20777) {

    /*private var dSocket = ServerSocket().apply {
        reuseAddress = true
        bind(InetSocketAddress(port))
    }*/
    private var socket = DatagramSocket(port).apply {
        broadcast = true
    }
    private var buffer = ByteArray(MAX_BUFFER)
    private var packet: DatagramPacket = DatagramPacket(buffer, buffer.size)

    private lateinit var session : Game

    private val DEBUG_count = 3


    fun DEBUG_addItems(){
        var ml = mutableListOf<Player>()

        var i = 0
        while(i < DEBUG_count){
            var p = Player()
            p._participant.postValue(Participant().apply {
                name.postValue("F. LASTNAME $i")
                teamId.postValue(i.toUByte())
            })
            p._currentLap.postValue(Lap().apply {
                carPosition.postValue(i.plus(1).toUByte())
                currentLapTime.postValue(0f)
            })
            ml.add(p)
            i++
        }
        session._players.postValue(ml)
    }

    fun DEBUG_updateItems(){
        var i = 0
        while(i < DEBUG_count){
            session._players.value?.get(i)?.let{

                val tmp = it._currentLap.value?.currentLapTime?.value?.plus(0.1f)
                it._currentLap.value?.currentLapTime?.postValue(tmp)
            }

            i++
        }
    }

    fun addCurrentSession(session: Game){
        this.session = session
        DEBUG_addItems()
    }

    @ExperimentalUnsignedTypes
    suspend fun listen(): Unit{
        return withContext(Dispatchers.IO){


            try{


                // TESTING


                // END OF TESTING

                while(true) {

                    // TESTING


                    delay(100L)

                    DEBUG_updateItems()

                    // END OF TESTING


                    val packet = DatagramPacket(buffer, buffer.size)
                    //socket.receive(packet)

                    /*dSocket.receive(packet)

                    newPacket(buffer)

                    packet.length = buffer.size*/
                }
            }catch (e: Exception){
                Timber.i("Testing - Exception while listening in the Controller: "+e.message)
                e.printStackTrace()
            }finally {
                socket?.close()
            }
        }
    }

    @ExperimentalUnsignedTypes
    @Synchronized
    fun newPacket(content: ByteArray){
        PacketDispatcher(content, session).run()
    }

    companion object{
        const val MAX_BUFFER = 2048

        const val FORMAT_2018 = 2018
        const val FORMAT_2019 = 2019

    }
}