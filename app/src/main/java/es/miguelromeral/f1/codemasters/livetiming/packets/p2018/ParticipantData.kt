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
            0 -> Standard.AIMode.HUMAN
            1 -> Standard.AIMode.AI
            else -> Standard.UNKNOWN
        }

    fun getStandardTeamId() = when(teamId.toInt()){
        0 -> Standard.TEAMS.MERCEDES
        1 -> Standard.TEAMS.FERRARI
        2 -> Standard.TEAMS.REDBULL
        3 -> Standard.TEAMS.WILLIAMS
        4 -> Standard.TEAMS.FORCE_INDIA
        5 -> Standard.TEAMS.RENAULT
        6 -> Standard.TEAMS.TORO_ROSSO
        7 -> Standard.TEAMS.HAAS
        8 -> Standard.TEAMS.MCLAREN
        9 -> Standard.TEAMS.SAUBER
        10 -> Standard.TEAMS.MCLAREN_1988
        11 -> Standard.TEAMS.MCLAREN_1991
        12 -> Standard.TEAMS.WILLIAMS_1992
        13 -> Standard.TEAMS.FERRARI_1995
        14 -> Standard.TEAMS.WILLIAMS_1996
        15 -> Standard.TEAMS.MCLAREN_1998
        16 -> Standard.TEAMS.FERRARI_2002
        17 -> Standard.TEAMS.FERRARI_2004
        18 -> Standard.TEAMS.RENAULT_2006
        19 -> Standard.TEAMS.FERRARI_2007
        20 -> Standard.TEAMS.MCLAREN_2008
        21 -> Standard.TEAMS.REDBULL_2010
        22 -> Standard.TEAMS.FERRARI_1976
        34 -> Standard.TEAMS.MCLAREN_1976
        35 -> Standard.TEAMS.LOTUS_1972
        36 -> Standard.TEAMS.FERRARI_1979
        37 -> Standard.TEAMS.MCLAREN_1982
        38 -> Standard.TEAMS.WILLIAMS_2003
        39 -> Standard.TEAMS.BRAWN_2009
        40 -> Standard.TEAMS.LOTUS_1978
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

        fun getTeam(teamId: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (teamId.toInt()) {
                        0 -> R.string.team_mercedes
                        1 -> R.string.team_ferrari
                        2 -> R.string.team_redbull
                        3 -> R.string.team_williams
                        4 -> R.string.team_force_india
                        5 -> R.string.team_renault
                        6 -> R.string.team_toro_rosso
                        7 -> R.string.team_haas
                        8 -> R.string.team_mclaren
                        9 -> R.string.team_sauber
                        10 -> R.string.team_mclaren_1988
                        11 -> R.string.team_mclaren_1991
                        12 -> R.string.team_williams_1992
                        13 -> R.string.team_ferrari_1995
                        14 -> R.string.team_williams_1996
                        15 -> R.string.team_mclaren_1998
                        16 -> R.string.team_ferrari_2002
                        17 -> R.string.team_ferrari_2004
                        18 -> R.string.team_renault_2006
                        19 -> R.string.team_ferrari_2007
                        20 -> R.string.team_mclaren_2008
                        21 -> R.string.team_redbull_2010
                        22 -> R.string.team_ferrari_1976
                        34 -> R.string.team_mclaren_1976
                        35 -> R.string.team_lotus_1972
                        36 -> R.string.team_ferrari_1979
                        37 -> R.string.team_mclaren_1982
                        38 -> R.string.team_williams_2003
                        39 -> R.string.team_brawn_2009
                        40 -> R.string.team_lotus_1978
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

        fun getDriver(driverId: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (driverId.toInt()) {
                        0 -> R.string.driver_Carlos_Sainz
                        2 -> R.string.driver_Daniel_Ricciardo
                        3 -> R.string.driver_Fernando_Alonso
                        6 -> R.string.driver_Kimi_Raikkonen
                        7 -> R.string.driver_Lewis_Hamilton
                        8 -> R.string.driver_Marcus_Ericcson
                        9 -> R.string.driver_Max_Verstappen
                        10 -> R.string.driver_Nico_Hulkenberg
                        11 -> R.string.driver_Kevin_Magnussen
                        12 -> R.string.driver_Romain_Grosjean
                        13 -> R.string.driver_Sebastian_Vettel
                        14 -> R.string.driver_Sergio_Perez
                        15 -> R.string.driver_Valtteri_Bottas
                        17 -> R.string.driver_Esteban_Ocon
                        18 -> R.string.driver_Stoffel_Vandoorne
                        19 -> R.string.driver_Lance_Stroll
                        20 -> R.string.driver_Arron_Barnes
                        21 -> R.string.driver_Martin_Giles
                        22 -> R.string.driver_Alex_Murray
                        23 -> R.string.driver_Lucas_Roth
                        24 -> R.string.driver_Igor_Correia
                        25 -> R.string.driver_Sophie_Levasseur
                        26 -> R.string.driver_Jonas_Schiffer
                        27 -> R.string.driver_Alain_Forest
                        28 -> R.string.driver_Jay_Letourneau
                        29 -> R.string.driver_Esto_Saari
                        30 -> R.string.driver_Yasar_Atiyeh
                        31 -> R.string.driver_Callisto_Calabresi
                        32 -> R.string.driver_Naota_Izum
                        33 -> R.string.driver_Howard_Clarke
                        34 -> R.string.driver_Wilheim_Kaufmann
                        35 -> R.string.driver_Marie_Laursen
                        36 -> R.string.driver_Flavio_Nieves
                        37 -> R.string.driver_Peter_Belousov
                        38 -> R.string.driver_Klimek_Michalski
                        39 -> R.string.driver_Santiago_Moreno
                        40 -> R.string.driver_Benjamin_Coppens
                        41 -> R.string.driver_Noah_Visser
                        42 -> R.string.driver_Gert_Waldmuller
                        43 -> R.string.driver_Julian_Quesada
                        44 -> R.string.driver_Daniel_Jones
                        58 -> R.string.driver_Charles_Leclerc
                        59 -> R.string.driver_Pierre_Gasly
                        60 -> R.string.driver_Brendon_Hartley
                        61 -> R.string.driver_Sergey_Sirotkin
                        69 -> R.string.driver_Ruben_Meijer
                        70 -> R.string.driver_Rashid_Nair
                        71 -> R.string.driver_Jack_Tremblay
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }
    }
}