package es.miguelromeral.f1.codemasters.livetiming.packets.p2018

import es.miguelromeral.f1.codemasters.livetiming.packets.Packet
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketHeader
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketLapData
import es.miguelromeral.f1.codemasters.livetiming.packets.stringFromPacket


class PacketParticipantData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    val numCars = content[0].toUByte()
    val participants = createParticipants(content, 1, 0, numCars.toInt())


    fun createParticipants(content: ByteArray, begin: Int, iteration: Int = 0, end: Int = ParticipantData.MAX_PARTICIPANT): List<ParticipantData>{
        if(iteration >= end)
            return emptyList()

        val start = begin + (iteration * ParticipantData.SIZE)
        val final = start + ParticipantData.SIZE

        return listOf(ParticipantData.create(content.sliceArray(start until final))) +
                createParticipants(content, begin, iteration + 1, end)
    }

    companion object{
        const val PACKET_ID = 4

        fun create(header: PacketHeader, content: ByteArray): PacketParticipantData {
            return PacketParticipantData(header,
                content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}


class ParticipantData private constructor(content: ByteArray){

    val aiControlled = content[0].toUByte()
    val driverId = content[1].toUByte()
    val teamId = content[2].toUByte()
    val raceNumber = content[3].toUByte()
    val nationality = content[4].toUByte()
    val name = stringFromPacket(content.sliceArray(5 until SIZE))


    companion object{
        const val MAX_PARTICIPANT = 20
        const val SIZE = 53

        fun create(content: ByteArray): ParticipantData {
            return ParticipantData(content.sliceArray(0 until SIZE))
        }

        fun getAiControlled(aiControlled: UByte) = when(aiControlled.toInt()){
            0 -> "Human"
            1 -> "AI"
            else -> "Unknown"
        }

        fun getDriver(driverId: UByte) = when(driverId.toInt()){
            0 -> "Carlos Sainz"
            2 -> "Daniel Ricciardo"
            3 -> "Fernando Alonso"
            6 -> "Kimi Raikkonen"
            7 -> "Lewis Hamilton"
            8 -> "Marcus Ericcson"
            9 -> "Max Verstappen"
            10 -> "Nico Hulkenberg"
            11 -> "Kevin Magnussen"
            12 -> "Romain Grosjean"
            13 -> "Sebastian Vettel"
            14 -> "Sergio Perez"
            15 -> "Valtteri Bottas"
            17 -> "Esteban Ocon"
            18 -> "Stoffel Vandoorne"
            19 -> "Lance Stroll"
            20 -> "Arron Barnes"
            21 -> "Martin Giles"
            22 -> "Alex Murray"
            23 -> "Lucas Roth"
            24 -> "Igor Correia"
            25 -> "Sophie Levasseur"
            26 -> "Jonas Schiffer"
            27 -> "Alain Forest"
            28 -> "Jay Letourneau"
            29 -> "Esto Saari"
            30 -> "Yasar Atiyeh"
            31 -> "Callisto Calabresi"
            32 -> "Naota Izum"
            33 -> "Howard Clarke"
            34 -> "Wilheim Kaufmann"
            35 -> "Marie Laursen"
            36 -> "Flavio Nieves"
            37 -> "Peter Belousov"
            38 -> "Klimek Michalski"
            39 -> "Santiago Moreno"
            40 -> "Benjamin Coppens"
            41 -> "Noah Visser"
            42 -> "Gert Waldmuller"
            43 -> "Julian Quesada"
            44 -> "Daniel Jones"
            58 -> "Charles Leclerc"
            59 -> "Pierre Gasly"
            60 -> "Brendon Hartley"
            61 -> "Sergey Sirotkin"
            69 -> "Ruben Meijer"
            70 -> "Rashid Nair"
            71 -> "Jack Tremblay"
            else -> "Unknown"
        }
    }
}