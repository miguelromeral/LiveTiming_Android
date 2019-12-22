package es.miguelromeral.f1.codemasters.livetiming.packets.p2018

import es.miguelromeral.f1.codemasters.livetiming.packets.*

class PacketCarTelemetryData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    val carTelemetryData = createTelemetryData(content, 0)
    val buttonStatus = intFromPacket(content.sliceArray(1060 until 1064))

    fun createTelemetryData(content: ByteArray, begin: Int, iteration: Int = 0, endloop: Int = CarTelemetryData.MAX_CARS): List<CarTelemetryData>{
        if(iteration >= endloop)
            return emptyList()

        val start = begin + (iteration * CarTelemetryData.SIZE)
        val end = start + CarTelemetryData.SIZE

        return listOf(CarTelemetryData.create(content.sliceArray(start until end))) +
                createTelemetryData(content, begin, iteration + 1, endloop)
    }


    companion object {
        const val PACKET_ID = 6

        fun create(header: PacketHeader, content: ByteArray): PacketCarTelemetryData {
            return PacketCarTelemetryData(header,
                content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}

class CarTelemetryData private constructor(content: ByteArray){

    val speed = shortFromPacket(content.sliceArray(0 until 2))
    val throttle = content[2].toUByte()
    val steer = content[3]
    val brake = content[4].toUByte()
    val clutch = content[5].toUByte()
    val gear = content[6]
    val engineRPM = shortFromPacket(content.sliceArray(7 until 9))
    val drs = content[9].toUByte()
    val revLightsPercent = content[10].toUByte()
    val brakesTemperature = createShortArray(content,  11)
    val tyresSurfaceTemperature = createShortArray(content, 19)
    val tyresInnerTemperature = createShortArray(content, 27)
    val engineTemperature = shortFromPacket(content.sliceArray(35 until 37))
    val tyresPressure = createFloatArray(content, 37)

    companion object{
        const val MAX_CARS = 20
        const val SIZE = 53

        fun create(content: ByteArray): CarTelemetryData {
            return CarTelemetryData(content.sliceArray(0 until SIZE))
        }
    }
}