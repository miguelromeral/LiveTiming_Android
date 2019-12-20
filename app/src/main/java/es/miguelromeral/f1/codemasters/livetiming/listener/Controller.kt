package es.miguelromeral.f1.codemasters.livetiming.listener

import es.miguelromeral.f1.codemasters.livetiming.classes.*
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
import es.miguelromeral.f1.codemasters.livetiming.standard.Format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.*

class Controller(val port: Int = DEFAULT_PORT) {

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
        val mySession = Session().apply{
            format = Format.F1_2018
            weather.postValue(3u)
            era.postValue(CarStatusData.ERA_MODERN)
            airTemperature.postValue(31)
            trackTemperature.postValue(53)
            sessionType.postValue(3u)
            era.postValue(0u)
            sessionTimeLeft.postValue(599)
            sessionDuration.postValue(1200)
            safetyCarStatus.postValue(0u)
            trackId.postValue(4)
            trackLength.postValue(3900)
            totalLaps.postValue(70u)
        }
        session._sessionData.postValue(mySession)


        var ml = mutableListOf<Player>()

        var i = 0
        while(i < DEBUG_count){
            var p = Player()
            p._participant.postValue(Participant().apply {
                //name.postValue("F. LASTNAME $i")
                driverId.postValue(i.toByte())
                teamId.postValue(i.toByte())
                format = Format.F1_2018
            })
            p._currentLap.postValue(Lap().apply {
                carPosition.postValue(i.plus(1).toUByte())
                currentLapTime.postValue(0f)
            })
            p._carStatus.postValue(CarStatus().apply {
                tyreCompound.postValue(i.toUByte())
            })
            p._telemetry.postValue(Telemetry().apply {
                engineTemperature.postValue(i.toShort())
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
                it._currentLap.value?.sector1Time?.postValue(12.3f)
                it._currentLap.value?.sector2Time?.postValue(23.4f)
            }

            /*var ses = session._sessionData.value
            ses?.sessionTimeLeft?.postValue(ses?.sessionTimeLeft?.value?.dec())
            session._sessionData.postValue(ses)*/

            i++
        }
    }

    fun addCurrentSession(session: Game){
        this.session = session
        Timber.i("Listening on port $port")
        DEBUG_addItems()
    }

    @ExperimentalUnsignedTypes
    suspend fun listen(): Unit{
        return withContext(Dispatchers.IO){


            try{
                while(true) {

                    // TESTING
                    delay(100L)
                    DEBUG_updateItems()
                    // END OF TESTING


/*
                    buffer = ByteArray(MAX_BUFFER)
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    //Timber.i("Raw Packet: ${packet.data.contentToString()}")
                    newPacket(packet.data)
*/

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
        PacketDispatcher(
            content,
            session
        ).run()

    }

    companion object{
        const val MAX_BUFFER = 2048

        const val FORMAT_2018 = 2018
        const val FORMAT_2019 = 2019

        const val DEFAULT_PORT = 20777
    }
}