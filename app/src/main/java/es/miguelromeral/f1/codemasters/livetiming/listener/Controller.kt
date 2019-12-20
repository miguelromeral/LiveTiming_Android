package es.miguelromeral.f1.codemasters.livetiming.listener

import classes.toplayer.Standard
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
        var mySession = Session().apply{
            format = Format.F1_2018
            weather.postValue(3u)
            era.postValue(Standard.ERA.MODERN_2018.toByte())
            airTemperature.postValue(31)
            trackTemperature.postValue(53)
            sessionType.postValue(3u)
            sessionTimeLeft.postValue(599)
            sessionDuration.postValue(1200)
            safetyCarStatus.postValue(0u)
            trackId.postValue(Standard.TRACKS.MONACO.toByte())
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
                tyreCompound.postValue(i.toByte())
                ersHarvestedThisLapMGUK.postValue(20000f + (25000f * i))
            })
            p._telemetry.postValue(Telemetry().apply {
                engineTemperature.postValue(i.toShort())
                gear.postValue((i % 9).toByte())
                throttle.postValue((10 + i * 4).toByte())
                speed.postValue((100 + i * 12).toShort())
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



                    /*buffer = ByteArray(MAX_BUFFER)
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)
                    Timber.i("Raw Packet: ${packet.data.contentToString()}")
                    newPacket(packet.data)
*/

                    //val b = byteArrayOf(-30, 7, 1, 7, 101, -128, 91, -91, 73, 37, -122, 126, 43, -57, -101, 67, -90, 31, 0, 0, 19, 0, 1, 3, 60, 0, 60, -58, 99, 64, 0, 0, -46, 66, -68, 52, -52, 16, 9, 0, 4, 4, 3, 2, 1, 4, 4, 3, 2, 0, 0, 0, 0, 0, 0, 0, -114, 51, 121, 73, 5, 5, 37, 55, 73, -49, -120, -109, 73, 8, 29, 49, 74, 0, 1, 3, 60, 0, -61, -7, 124, 64, 0, 0, -46, 66, -68, 52, -85, 13, 9, 0, 5, 5, 3, 3, 0, 5, 5, 3, 3, 0, 0, 0, 0, 0, 0, 0, 92, -88, -76, 73, 5, 110, -49, 27, 73, -69, 119, 125, 73, 48, 126, 3, 74, 0, 1, 3, 60, 0, 120, 72, -46, 64, 0, 0, -46, 66, -68, 52, -41, 14, 9, 0, 2, 2, 1, 1, 0, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 105, -87, 91, 74, 5, 25, 43, 41, 72, -65, 59, 119, 72, 32, -67, 13, 72, 0, 1, 0, 60, 1, 89, -43, 2, 64, 0, 0, -46, 66, -68, 52, -41, 14, 9, 0, 6, 6, 4, 3, 0, 6, 6, 4, 3, 0, 0, 0, 0, 0, 0, 0, -114, -88, 78, 74, 0, 111, 18, 85, 73, 80, -72, -66, 73, -82, -50, 96, 73, 0, 1, 0, 60, 0, 126, -66, 34, 64, 0, 0, -46, 66, -68, 52, -85, 13, 9, 0, 8, 8, 6, 5, 0, 8, 8, 6, 5, 0, 0, 0, 0, 0, 0, 0, 27, -36, 77, 73, 0, 108, -48, 0, 73, -79, -15, 77, 73, 55, -82, 52, 73, 0, 1, 0, 60, 0, 47, -100, -9, 64, 0, 0, -46, 66, -68, 52, -41, 14, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 60, 0, -52, 51, 47, 64, 0, 0, -46, 66, -68, 52, -52, 16, 9, 0, 5, 5, 3, 3, 0, 5, 5, 3, 3, 0, 0, 0, 0, 0, 0, 0, -3, 104, -12, 73, 5, -49, 94, -56, 72, -8, 115, 21, 73, -72, 116, 91, 73, 0, 1, 0, 60, 1, 30, -31, -4, 64, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 60, 0, -116, -43, 16, 65, 0, 0, -46, 66, -68, 52, -52, 16, 9, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 60, 0, 100, 78, -119, 64, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 5, 5, 3, 3, 1, 5, 5, 3, 3, 0, 0, 0, 0, 0, 0, 0, -44, 98, -4, 72, 5, 30, -74, -63, 72, 58, -96, 84, 73, 101, 25, -123, 73, 0, 1, 0, 60, 0, -124, 65, 20, 65, 0, 0, -46, 66, -68, 52, -52, 16, 9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 60, 0, -2, 50, 33, 64, 0, 0, -46, 66, -68, 52, -85, 13, 9, 0, 7, 7, 5, 4, 0, 7, 7, 5, 4, 0, 0, 0, 0, 0, 0, 0, 20, 25, -76, 73, 0, 35, 101, 28, 73, 5, -4, -100, 73, 23, -33, 88, 73, 0, 1, 3, 60, 0, -53, -76, -110, 64, 0, 0, -46, 66, -68, 52, -52, 16, 9, 0, 7, 7, 5, 5, 0, 7, 7, 5, 5, 0, 0, 0, 0, 0, 0, 0, 95, -73, -31, 72, 5, -91, -19, -121, 72, -57, -70, -19, 72, -14, 44, 36, 73, 0, 1, 3, 60, 0, -60, -69, -124, 64, 0, 0, -46, 66, -68, 52, -52, 16, 9, 0, 5, 5, 3, 3, 0, 5, 5, 3, 3, 0, 0, 0, 0, 0, 0, 0, 108, 80, -7, 73, 5, 86, 22, 11, 73, -17, 67, 115, 73, -115, -102, -60, 73, 0, 1, 3, 60, 0, -27, -16, -54, 64, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 2, 2, 1, 1, 0, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, -73, 76, 102, 74, 5, -19, 125, -125, 72, 71, -21, 0, 73, -32, 76, -53, 72, 0, 1, 3, 60, 0, -52, 46, -24, 64, 0, 0, -46, 66, -68, 52, -41, 14, 9, 0, 3, 3, 2, 2, 0, 3, 3, 2, 2, 0, 0, 0, 0, 0, 0, 0, 82, 45, 47, 74, 5, 95, 114, 49, 73, 110, 13, -114, 73, 94, 52, 15, 74, 2, 1, 0, 60, 0, 106, 54, -12, 63, 0, 0, -46, 66, -68, 52, -85, 13, 9, 0, 6, 6, 4, 3, 0, 6, 6, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 60, 0, 111, 106, -35, 64, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -8, -38, 103, 74, 5, 0, 0, 0, 0, 115, 99, 45, 69, -58, 69, 71, 72, 2, 1, 0, 60, 0, 12, 56, -15, 63, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 6, 6, 4, 3, 0, 6, 6, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 60, 0, 0, 0, -64, 64, 0, 0, -46, 66, 32, 53, -52, 16, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 116, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                    //newPacket(b)

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