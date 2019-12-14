package es.miguelromeral.f1.codemasters.livetiming.packets

import android.os.Build.VERSION_CODES.P
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ByteOrder.BIG_ENDIAN
import java.nio.ByteOrder.LITTLE_ENDIAN


@kotlin.ExperimentalUnsignedTypes
class SessionData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    var weather: UByte
    var trackTemperature: Byte
    var airTemperature: Byte
    var totalLaps: UByte
    var trackLength: Short
    var sessionType: UByte
    var trackId: Byte
    var era: UByte
    var sessionTimeLeft: Short
    var sessionDuration: Short
    var pitSpeedLimit: UByte
    var gamePaused: UByte
    var isSpectating: UByte
    var spectatorCardIndex: UByte
    var sliProNativeSupport: UByte
    var numMarshalZones: UByte
    var marshalZones : List<MarshalZone>
    var safetyCarStatus: UByte
    var networkGame: UByte

    init{
        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        weather = content.get(0).toUByte()
        trackTemperature = content.get(1)
        airTemperature = content.get(2)
        totalLaps = content.get(3).toUByte()
        trackLength = shortFromPacket(content.sliceArray(4..5))
        sessionType = content.get(6).toUByte()
        trackId = content[7]
        era = content[8].toUByte()
        sessionTimeLeft = shortFromPacket(content.sliceArray(9..10))
        sessionDuration = shortFromPacket(content.sliceArray(11..12))
        pitSpeedLimit = content[13].toUByte()
        gamePaused = content[14].toUByte()
        isSpectating = content[15].toUByte()
        spectatorCardIndex = content[16].toUByte()
        sliProNativeSupport = content[17].toUByte()
        numMarshalZones = content[18].toUByte()
        val finaloffset = MAX_MARSHAL_ZONES * MarshalZone.SIZE + 19
        marshalZones = createMarshalZones(
            content.sliceArray(
                19 until finaloffset)
            , 0)
        safetyCarStatus = content[finaloffset].toUByte()
        networkGame = content[finaloffset + 1].toUByte()
    }

    private fun createMarshalZones(content: ByteArray, iteration: Int): List<MarshalZone> {
        if(iteration >= MAX_MARSHAL_ZONES)
            return emptyList()

        val init = iteration * MarshalZone.SIZE
        val end = init + MarshalZone.SIZE

        return listOf(
            MarshalZone(content.sliceArray(init until end))
        ) + createMarshalZones(content, iteration + 1)

    }


    companion object {
        const val PACKET_ID = 1
        const val MAX_MARSHAL_ZONES = 21

        fun getTrack(trackId: Byte) = when (trackId.toInt()) {
            0 -> "Melbourne"
            1 -> "Paul Ricard"
            2 -> "Shanghai"
            3 -> "Sakhir (Bahrain)"
            4 -> "Catalunya"
            5 -> "Monaco"
            6 -> "Montreal"
            7 -> "Silverstone"
            8 -> "Hockenheim"
            9 -> "Hungaroring"
            10 -> "Spa"
            11 -> "Monza"
            12 -> "Singapore"
            13 -> "Suzuka"
            14 -> "Abu Dhabi"
            15 -> "Texas"
            16 -> "Brazil"
            17 -> "Austria"
            18 -> "Sochi"
            19 -> "Mexico"
            20 -> "Baku (Azerbaijan)"
            21 -> "Sakhir Short"
            22 -> "Silverstone Short"
            23 -> "Texas Short"
            24 -> "Suzuka Short"
            else -> "Unknown"
        }

        fun getNateworkGame(networkGame: UByte) = when(networkGame){
            0.toUByte() -> "Offline"
            1.toUByte() -> "Online"
            else -> "Unknown"
        }

        fun getSafetyCarStatus(safetyCarStatus: UByte) = when(safetyCarStatus){
            0.toUByte() -> "No Safety Car"
            1.toUByte() -> "SC"
            2.toUByte() -> "VSC"
            else -> "Unkown"
        }

        fun getEra(era: UByte) = when(era){
            0.toUByte() -> "Modern"
            1.toUByte() -> "Classic"
            else -> "Unknown"
        }

        fun getWeather(weather: UByte) = when(weather){
            0.toUByte() -> "Clear"
            1.toUByte() -> "Light Cloud"
            2.toUByte() -> "Overcast"
            3.toUByte() -> "Light Rain"
            4.toUByte() -> "Heavy Rain"
            5.toUByte() -> "Storm"
            else -> "Unknown"
        }

        fun getSessionType(session: UByte)  = when(session){
            1.toUByte() -> "P1"
            2.toUByte() -> "P2"
            3.toUByte() -> "P3"
            4.toUByte() -> "Sh. P" // Short Practice
            5.toUByte() -> "Q1"
            6.toUByte() -> "Q2"
            7.toUByte() -> "Q3"
            8.toUByte() -> "Sh. Q" // Short Qualy
            9.toUByte() -> "OSQ"
            10.toUByte() -> "R"
            11.toUByte() -> "R2"
            12.toUByte() -> "Time Trial"
            else -> "Unknown"
        }

        fun create(header: PacketHeader, content: ByteArray): SessionData{
            return SessionData(header, content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}

class MarshalZone internal constructor(content: ByteArray)
{
    var zoneStart: Float
    var zoneFlag: Byte

    init{
        zoneStart = floatFromPacket(content.sliceArray(0..3))
        zoneFlag = content[4]
    }

    companion object{
        const val SIZE = 5
    }
}