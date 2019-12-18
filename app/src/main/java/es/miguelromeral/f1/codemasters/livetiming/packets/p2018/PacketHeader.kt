package es.miguelromeral.f1.codemasters.livetiming.packets

import timber.log.Timber

class PacketHeader private constructor(content: ByteArray)
{
    var format: Short = 0
    var version: Byte = 0
    var id: Byte = ID_NOT_INITIALIZED
    var sessionUID: Long = 0L
    var sessionTime: Float = 0f
    var frameIdentifier: Int = 0
    var playerCarIndex: Byte = ID_NOT_INITIALIZED


    init{
        try{
            format = shortFromPacket(content.sliceArray(0..1))
            version = content[2]
            id = content[3]
            sessionUID = longFromPacket(content.sliceArray(4..11))
            sessionTime = floatFromPacket(content.sliceArray(12..15))
            frameIdentifier = intFromPacket(content.sliceArray(16..19))
            playerCarIndex = content[20]

            //Timber.i("Format: $format, Version: $version, id: $id, SUID: $sessionUID, Time: $sessionTime, FID: $frameIdentifier, PCI: $playerCarIndex")

        }catch(e: Exception){
            Timber.i("Testing - Exception when creating PacketHeader: "+e.message)
        }
    }

    fun is2018Package() = format == 2018.toShort()



    companion object {
        const val HEADER_SIZE = 21
        const val ID_NOT_INITIALIZED: Byte = -1

        fun create(content: ByteArray): PacketHeader{
            return PacketHeader(content.sliceArray(0 until HEADER_SIZE))
        }
    }
}