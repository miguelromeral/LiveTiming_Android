package es.miguelromeral.f1.codemasters.livetiming.packets

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ByteOrder.LITTLE_ENDIAN
import java.nio.charset.StandardCharsets.UTF_8

// 2 Bytes Array
fun shortFromPacket(content: ByteArray) = ByteBuffer.wrap(content).order(LITTLE_ENDIAN).short

// 4 Bytes array
fun floatFromPacket(content: ByteArray) = ByteBuffer.wrap(content).order(LITTLE_ENDIAN).float

// 4 Bytes array
fun intFromPacket(content: ByteArray) = ByteBuffer.wrap(content).order(LITTLE_ENDIAN).int

// 8 Bytes array
fun longFromPacket(content: ByteArray) = ByteBuffer.wrap(content).order(LITTLE_ENDIAN).long


fun stringFromPacket(content: ByteArray) = content.toString(Charsets.UTF_8)



// 1 Byte Array
fun createByteArray(content: ByteArray, begining: Int, iteration: Int = 0, end: Int = 4): List<Byte>{
    if(iteration >= end)
        return emptyList()

    return listOf(content[begining + iteration]) + createByteArray(content, begining, iteration + 1, end)
}

// 1 Byte Array
fun createUByteArray(content: ByteArray, begining: Int, iteration: Int = 0, end: Int = 4): List<UByte>{
    if(iteration >= end)
        return emptyList()

    return listOf(content[begining + iteration].toUByte()) + createUByteArray(content, begining, iteration + 1, end)
}

// 2 Bytes array
fun createShortArray(content: ByteArray, begining: Int, iteration: Int = 0, endloop: Int = 4): List<Short>{
    if(iteration >= endloop)
        return emptyList()

    val start = begining + (iteration * 2)
    val end = start + 2

    return listOf(shortFromPacket(content.sliceArray(start until end))) + createShortArray(content, begining, iteration + 1, endloop)
}


// 4 Bytes array
fun createFloatArray(content: ByteArray, begining: Int, iteration: Int = 0, endloop: Int = 4): List<Float>{
    if(iteration >= endloop)
        return emptyList()

    val start = begining + (iteration * 4)
    val end = start + 4

    return listOf(floatFromPacket(content.sliceArray(start until end))) + createFloatArray(content, begining, iteration + 1, endloop)
}

