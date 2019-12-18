package es.miguelromeral.f1.codemasters.livetiming.packets

import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.Packet
import timber.log.Timber

@kotlin.ExperimentalUnsignedTypes
class PacketLapData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    var lapData: List<LapData>

    init{
        lapData = createLapsData(content, 0)
    }

    fun createLapsData(content: ByteArray, iteration: Int): List<LapData> {
        if(iteration >= MAX_LAP_DATA)
            return emptyList()

        val init = iteration * LapData.SIZE
        val end = init + LapData.SIZE

        val lap = LapData(content.sliceArray(init until end))

        //Timber.i("Lap It ($iteration) [$init until $end]: ${content.sliceArray(init until end).contentToString()}")

        return listOf(
            lap
        ) + createLapsData(content, iteration + 1)
    }

    companion object {
        const val PACKET_ID = 2
        const val MAX_LAP_DATA = 20

        fun create(header: PacketHeader, content: ByteArray): PacketLapData{
            return PacketLapData(header,
                content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}


@kotlin.ExperimentalUnsignedTypes
class LapData internal constructor(content: ByteArray)
{
    var lastLapTime: Float
    var currentLapTime: Float
    var bestLapTime: Float
    var sector1Time: Float
    var sector2Time: Float
    var lapDistance: Float
    var totalDistance: Float
    var safetyCarDelta: Float
    var carPosition: UByte
    var currentLapNum: UByte
    var pitStatus: UByte
    var sector: UByte
    var currentLapInvalid: UByte
    var penalties: UByte
    var gridPosition: UByte
    var driverStatus: UByte
    var resultStatus: UByte

    init{
        lastLapTime = floatFromPacket(content.sliceArray(0 until 4))
        currentLapTime = floatFromPacket(content.sliceArray(4 until 8))
        bestLapTime = floatFromPacket(content.sliceArray(8 until 12))
        sector1Time = floatFromPacket(content.sliceArray(12 until 16))
        sector2Time = floatFromPacket(content.sliceArray(16 until 20))
        lapDistance = floatFromPacket(content.sliceArray(20 until 24))
        totalDistance = floatFromPacket(content.sliceArray(24 until 28))
        safetyCarDelta = floatFromPacket(content.sliceArray(28 until 32))
        carPosition = content[32].toUByte()
        currentLapNum = content[33].toUByte()
        pitStatus = content[34].toUByte()
        sector = content[35].toUByte()
        currentLapInvalid = content[36].toUByte()
        penalties = content[37].toUByte()
        gridPosition = content[38].toUByte()
        driverStatus = content[39].toUByte()
        resultStatus = content[40].toUByte()
    }

    companion object{
        const val SIZE = 41
    }
}