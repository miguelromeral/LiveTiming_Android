package es.miguelromeral.f1.codemasters.livetiming.packets

import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.Packet

class EventData
    private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    var eventStringCode: String =
        stringFromPacket(content.sliceArray(0..4))

    fun getStandardEventCode(): Int = when(eventStringCode.toUpperCase()){
        "SSTA" -> Standard.EVENT.START
        "SEND" -> Standard.EVENT.END
        else -> Standard.UNKNOWN
    }

    companion object {
        const val PACKET_ID = 3

        fun create(header: PacketHeader, content: ByteArray): EventData{
            return EventData(header, content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}