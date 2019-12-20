package es.miguelromeral.f1.codemasters.livetiming.packets.p2018

import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketHeader
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


    fun getStandardAIControlled() = when (aiControlled.toInt()) {
            0 -> Standard.AI.HUMAN
            1 -> Standard.AI.AI
            else -> Standard.UNKNOWN
        }

    fun getStandardDriverId() = when (driverId.toInt()) {
        0 -> Standard.AI.HUMAN
        1 -> Standard.AI.AI
        else -> Standard.UNKNOWN
    }

    companion object{
        const val MAX_PARTICIPANT = 20
        const val SIZE = 53

        fun create(content: ByteArray): ParticipantData {
            return ParticipantData(content.sliceArray(0 until SIZE))
        }


        fun getNationality(nationality: UByte) :String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (nationality.toInt()) {
                        1 -> R.string.nat_american
                        2 -> R.string.nat_argentinean
                        3 -> R.string.nat_australian
                        4 -> R.string.nat_austrian
                        5 -> R.string.nat_azerbaijani
                        6 -> R.string.nat_bahraini
                        7 -> R.string.nat_belgian
                        8 -> R.string.nat_bolivian
                        9 -> R.string.nat_brazilian
                        10 -> R.string.nat_british
                        11 -> R.string.nat_bulgarian
                        12 -> R.string.nat_cameroonian
                        13 -> R.string.nat_canadian
                        14 -> R.string.nat_chilean
                        15 -> R.string.nat_chinese
                        16 -> R.string.nat_colombian
                        17 -> R.string.nat_costa_rican
                        18 -> R.string.nat_croatian
                        19 -> R.string.nat_cypriot
                        20 -> R.string.nat_czech
                        21 -> R.string.nat_danish
                        22 -> R.string.nat_dutch
                        23 -> R.string.nat_ecuadorian
                        24 -> R.string.nat_english
                        25 -> R.string.nat_emirian
                        26 -> R.string.nat_estonian
                        27 -> R.string.nat_finnish
                        28 -> R.string.nat_french
                        29 -> R.string.nat_german
                        30 -> R.string.nat_ghanaian
                        31 -> R.string.nat_greek
                        32 -> R.string.nat_guatemalan
                        33 -> R.string.nat_honduran
                        34 -> R.string.nat_hong_konger
                        35 -> R.string.nat_hungarian
                        36 -> R.string.nat_icelander
                        37 -> R.string.nat_indian
                        38 -> R.string.nat_indonesian
                        39 -> R.string.nat_irish
                        40 -> R.string.nat_israeli
                        41 -> R.string.nat_italian
                        42 -> R.string.nat_jamaican
                        43 -> R.string.nat_japanese
                        44 -> R.string.nat_jordanian
                        45 -> R.string.nat_kuwaiti
                        46 -> R.string.nat_latvian
                        47 -> R.string.nat_lebanese
                        48 -> R.string.nat_lithuanian
                        49 -> R.string.nat_luxembourger
                        50 -> R.string.nat_malaysian
                        51 -> R.string.nat_maltese
                        52 -> R.string.nat_mexican
                        53 -> R.string.nat_monegasque
                        54 -> R.string.nat_new_zealander
                        55 -> R.string.nat_nicaraguan
                        56 -> R.string.nat_north_korean
                        57 -> R.string.nat_northem_irish
                        58 -> R.string.nat_norwegian
                        59 -> R.string.nat_omani
                        60 -> R.string.nat_pakistani
                        61 -> R.string.nat_panamanian
                        62 -> R.string.nat_paraguayan
                        63 -> R.string.nat_peruvian
                        64 -> R.string.nat_polish
                        65 -> R.string.nat_portuguese
                        66 -> R.string.nat_qatari
                        67 -> R.string.nat_romanian
                        68 -> R.string.nat_russian
                        69 -> R.string.nat_salvadoran
                        70 -> R.string.nat_saudi
                        71 -> R.string.nat_scottish
                        72 -> R.string.nat_serbian
                        73 -> R.string.nat_singaporean
                        74 -> R.string.nat_slovakian
                        75 -> R.string.nat_slovenian
                        76 -> R.string.nat_south_korean
                        77 -> R.string.nat_south_african
                        78 -> R.string.nat_spanish
                        79 -> R.string.nat_swedish
                        80 -> R.string.nat_swiss
                        81 -> R.string.nat_taiwanese
                        82 -> R.string.nat_thai
                        83 -> R.string.nat_turkish
                        84 -> R.string.nat_uruguayan
                        85 -> R.string.nat_ukrainian
                        86 -> R.string.nat_venezuelan
                        87 -> R.string.nat_welsh
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

    }
}