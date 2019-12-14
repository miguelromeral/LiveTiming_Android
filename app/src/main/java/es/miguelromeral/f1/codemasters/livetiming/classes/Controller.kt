package es.miguelromeral.f1.codemasters.livetiming.classes

import es.miguelromeral.f1.codemasters.livetiming.packets.*
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.ParticipantData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.DatagramPacket
import java.net.DatagramSocket

class Controller(val port: Int = 20777) {

    private var dSocket: DatagramSocket = DatagramSocket(port)
    private var buffer = ByteArray(MAX_BUFFER){ 0 }
    private var packet: DatagramPacket = DatagramPacket(buffer, buffer.size)

    private lateinit var session : Game

    fun addCurrentSession(session: Game){
        this.session = session
    }

    @ExperimentalUnsignedTypes
    suspend fun listen(): Unit{
        return withContext(Dispatchers.IO){
            try{
                while(true) {
                    dSocket.receive(packet)

                    newPacket(buffer)

                    packet.length = buffer.size
                }
            }catch (e: Exception){
                Timber.i("Testing - Exception while listening: "+e.message)
                e.printStackTrace()
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