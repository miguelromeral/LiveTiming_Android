package es.miguelromeral.f1.codemasters.livetiming.packets.p2017

import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.createFloatArray
import es.miguelromeral.f1.codemasters.livetiming.packets.floatFromPacket

class CarUDPData internal constructor(content: ByteArray) {

    val worldPosition = createFloatArray(content, 0, 0, 3)
    val lastLapTime = floatFromPacket(content.sliceArray(12 until 16))
    val currentLapTime = floatFromPacket(content.sliceArray(16 until 20))
    val bestLapTime = floatFromPacket(content.sliceArray(20 until 24))
    val sector1Time = floatFromPacket(content.sliceArray(24 until 28))
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

        fun getTeam(teamId: UByte, era: UByte):String {
            MyApplication.getContext()?.resources?.let {
                when(era.toInt()){
                    Packet2017.ERA_MODERN -> {
                        return it.getString(
                            when (teamId.toInt()) {
                                0 -> R.string.team_redbull
                                1 -> R.string.team_ferrari
                                2 -> R.string.team_mclaren
                                3 -> R.string.team_renault
                                4 -> R.string.team_mercedes
                                5 -> R.string.team_sauber
                                6 -> R.string.team_force_india
                                7 -> R.string.team_williams
                                8 -> R.string.team_toro_rosso
                                11 -> R.string.team_haas
                                else -> R.string.unknown
                            })
                    }
                    Packet2017.ERA_CLASSIC -> {
                        return it.getString(
                            when (teamId.toInt()) {
                                0 -> R.string.team_williams_1992
                                1 -> R.string.team_mclaren_1988
                                2 -> R.string.team_mclaren_2008
                                3 -> R.string.team_ferrari_2004
                                4 -> R.string.team_ferrari_1995
                                5 -> R.string.team_ferrari_2007
                                6 -> R.string.team_mclaren_1998
                                7 -> R.string.team_williams_1996
                                8 -> R.string.team_renault_2006
                                10 -> R.string.team_ferrari_2002
                                11 -> R.string.team_redbull_2010
                                12 -> R.string.team_mclaren_1991
                                else -> R.string.unknown
                            })
                    }
                    else ->
                        return it.getString(R.string.unknown)
                }
            }
            return "Unknown"
        }


        fun getDriver(driverId: UByte, era: UByte):String {
            MyApplication.getContext()?.resources?.let {
                when(era.toInt()){
                    Packet2017.ERA_MODERN -> {
                        return it.getString(
                            when (driverId.toInt()) {
                                9 -> R.string.driver_Lewis_Hamilton
                                15 -> R.string.driver_Valtteri_Bottas
                                16 -> R.string.driver_Daniel_Ricciardo
                                22 -> R.string.driver_Max_Verstappen
                                0 -> R.string.driver_Sebastian_Vettel
                                6 -> R.string.driver_Kimi_Raikkonen
                                5 -> R.string.driver_Sergio_Perez
                                33 -> R.string.driver_Esteban_Ocon
                                3 -> R.string.driver_Felipe_Massa
                                35 -> R.string.driver_Lance_Stroll
                                2 -> R.string.driver_Fernando_Alonso
                                34 -> R.string.driver_Stoffel_Vandoorne
                                23 -> R.string.driver_Carlos_Sainz
                                1 -> R.string.driver_Daniil_Kvyat
                                7 -> R.string.driver_Romain_Grosjean
                                14 -> R.string.driver_Kevin_Magnussen
                                10 -> R.string.driver_Nico_Hulkenberg
                                20 -> R.string.driver_Jolyon_Palmer
                                18 -> R.string.driver_Marcus_Ericcson
                                31 -> R.string.driver_Pascal_Wehrlein
                                else -> R.string.unknown
                            })
                    }
                    Packet2017.ERA_CLASSIC -> {
                        return it.getString(
                            when (driverId.toInt()) {
                                23 -> R.string.driver_Arron_Barnes
                                1 -> R.string.driver_Martin_Giles
                                16 -> R.string.driver_Alex_Murray
                                68 -> R.string.driver_Lucas_Roth
                                2 -> R.string.driver_Igor_Correia
                                3 -> R.string.driver_Sophie_Levasseur
                                24 -> R.string.driver_Jonas_Schiffer
                                4 -> R.string.driver_Alain_Forest
                                20 -> R.string.driver_Jay_Letourneau
                                6 -> R.string.driver_Esto_Saari
                                9 -> R.string.driver_Yasar_Atiyeh
                                18 -> R.string.driver_Callisto_Calabresi
                                22 -> R.string.driver_Naota_Izum
                                10 -> R.string.driver_Howard_Clarke
                                8 -> R.string.driver_Wilheim_Kaufmann
                                14 -> R.string.driver_Marie_Laursen
                                31 -> R.string.driver_Flavio_Nieves
                                7 -> R.string.driver_Peter_Belousov
                                0 -> R.string.driver_Klimek_Michalski
                                5 -> R.string.driver_Santiago_Moreno
                                15 -> R.string.driver_Benjamin_Coppens
                                32 -> R.string.driver_Noah_Visser
                                33 -> R.string.driver_Gert_Waldmuller
                                34 -> R.string.driver_Julian_Quesada
                                else -> R.string.unknown
                            })
                    }
                }
                return it.getString(R.string.unknown)
            }
            return "Unknown"
        }
    }
}