package es.miguelromeral.f1.codemasters.livetiming.classes

import es.miguelromeral.f1.codemasters.livetiming.packets.*
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
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

    private val DEBUG_count = 20


    fun DEBUG_addItems(){
        session._sessionData.era.postValue(CarStatusData.ERA_MODERN)

        var ml = mutableListOf<Player>()

        var i = 0
        while(i < DEBUG_count){
            var p = Player()
            p._participant.postValue(Participant().apply {
                name.postValue("F. LASTNAME $i")
                teamId.postValue(i.toUByte())
                format = Format.F1_2018
            })
            p._currentLap.postValue(Lap().apply {
                carPosition.postValue(i.plus(1).toUByte())
                currentLapTime.postValue(0f)
            })
            p._carStatus.postValue(CarStatus().apply {
                tyreCompound.postValue(i.toUByte())
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
        //DEBUG_addItems()
    }

    @ExperimentalUnsignedTypes
    suspend fun listen(): Unit{
        return withContext(Dispatchers.IO){


            try{
                while(true) {

                    // TESTING
                    //delay(100L)
                    //DEBUG_updateItems()
                    // END OF TESTING



                    buffer = ByteArray(MAX_BUFFER)
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    //Timber.i("Raw Packet: ${packet.data.contentToString()}")
                    newPacket(packet.data)


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
        /*try {
            when(shortFromPacket(content.sliceArray(0..1)).toInt()){
                Controller.FORMAT_2018 -> {
                    val header = PacketHeader.create(content)
                    //Timber.i("Testing - New packet received | ID: "+ header.id)
                    when (header.id.toInt()) {
                        SessionData.PACKET_ID -> session.newSessionData2018(
                            SessionData.create(
                                header,
                                content
                            )
                        )
                        PacketLapData.PACKET_ID -> {

                            Timber.i("Lap Pa: ${content.contentToString()}")

                            session.newLapData2018(
                                PacketLapData.create(
                                    header,
                                    content
                                ))
                        }
                        PacketParticipantData.PACKET_ID -> session.newParticipants2018(
                            PacketParticipantData.create(header, content)
                        )
                        //PacketCarTelemetryData.PACKET_ID -> session.newTelemetry2018(PacketCarTelemetryData.create(header, content))

                        //EventData.PACKET_ID -> EventData(content)

                        else ->
                            Timber.i("Packet not identified by its ID")
                    }
                }
                Controller.FORMAT_2019 -> {

                }

                else -> {
                    // 2017 Format doesn't have the format in the first two bytes
                    Timber.i("Testing - Unknown packet format received.")
                    session.newData2017(Packet2017.create(content))
                }

            }

        }catch(e: Exception){
            Timber.e("Error in PacketDispatcher: "+e.message)
        }*/
    }

    companion object{
        const val MAX_BUFFER = 2048

        const val FORMAT_2018 = 2018
        const val FORMAT_2019 = 2019

    }
}