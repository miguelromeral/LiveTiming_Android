package es.miguelromeral.f1.codemasters.livetiming.packets.p2017

import es.miguelromeral.f1.codemasters.livetiming.packets.createFloatArray
import es.miguelromeral.f1.codemasters.livetiming.packets.floatFromPacket

class CarUDPData internal constructor(content: ByteArray) {

    val worldPosition = createFloatArray(content, 0, 0, 3)
    val lastLapTime = floatFromPacket(content.sliceArray(12 until 16))
    val currentLapTime = floatFromPacket(content.sliceArray(16 until 20))
    val bestLapTime = floatFromPacket(content.sliceArray(20 until 24))
    val sector1Time= floatFromPacket(content.sliceArray(24 until 28))
    val sector2Time = floatFromPacket(content.sliceArray(28 until 32))
    val lapDistance = floatFromPacket(content.sliceArray(32 until 36))
    val driverId = content[36]
    val teamId = content[37]
    val carPosition = content[38]
    val currentLapNum = content[39]
    val tyreCompound = content[40]
    val inPits = content[41]
    val sector = content[42]
    val currentLapInvalid = content[43]
    val penalties = content[44]


    companion object{
        const val SIZE = 45

    }
}