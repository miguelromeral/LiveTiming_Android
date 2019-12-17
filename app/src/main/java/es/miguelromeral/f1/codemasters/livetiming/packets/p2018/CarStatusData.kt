package es.miguelromeral.f1.codemasters.livetiming.packets.p2018

import es.miguelromeral.f1.codemasters.livetiming.packets.*


class PacketCarStatusData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    val carStatusData = createCarStatusData(content, 0)

    fun createCarStatusData(content: ByteArray, begin: Int, iteration: Int = 0, end: Int = CarStatusData.MAX_CARS): List<CarStatusData>{
        if(iteration >= end)
            return emptyList()

        val start = begin + (iteration * CarStatusData.SIZE)
        val end = start + CarStatusData.SIZE

        return listOf(CarStatusData.create(content.sliceArray(start until end))) +
                createCarStatusData(content, begin, iteration + 1, end)
    }


    companion object {
        const val PACKET_ID = 7

        fun create(header: PacketHeader, content: ByteArray): PacketCarStatusData {
            return PacketCarStatusData(header,
                content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}

class CarStatusData private constructor(content: ByteArray){

    val tractionControl = content[0].toUByte()
    val antiLockBrakes = content[1].toUByte()
    val fuelMix = content[2].toUByte()
    val frontBrakeBias = content[3].toUByte()
    val pitLimiterStatus = content[4].toUByte()
    val fuelInTank = floatFromPacket(content.sliceArray(5 until 9))
    val fuelCapacity = floatFromPacket(content.sliceArray(9 until 13))
    val maxRPM = intFromPacket(content.sliceArray(13 until 15))
    val idleRPM = intFromPacket(content.sliceArray(15 until 17))
    val maxGears = content[17].toUByte()
    val drsAllowed = content[18].toUByte()
    val tyresWear = createUByteArray(content, 19)
    val tyreCompound = content[23].toUByte()
    val tyresDamage = createUByteArray(content, 24)
    val frontLeftWingDamage = content[28].toUByte()
    val frontRightWingDamage = content[29].toUByte()
    val rearWingDamage = content[30].toUByte()
    val engineDamage = content[31].toUByte()
    val gearBoxDamage = content[32].toUByte()
    val exhaustDamage = content[33].toUByte()
    val vehicleFiaFlags = content[34]
    val ersStoreEnergy = floatFromPacket(content.sliceArray(35 until 39))
    val ersDeployMode = content[39].toUByte()
    val ersHarvestedThisLapMGUK = floatFromPacket(content.sliceArray(40 until 44))
    val ersHarvestedThisLapMGUH = floatFromPacket(content.sliceArray(44 until 48))
    val ersDeployedThisLap = floatFromPacket(content.sliceArray(48 until 52))


    companion object{
        const val MAX_CARS = 20
        const val SIZE = 52

        const val ERA_MODERN: UByte = 0u
        const val ERA_CLASSIC: UByte = 1u

        fun create(content: ByteArray): CarStatusData {
            return CarStatusData(content.sliceArray(PacketHeader.HEADER_SIZE until SIZE))
        }
    }
}