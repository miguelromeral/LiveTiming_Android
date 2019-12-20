package es.miguelromeral.f1.codemasters.livetiming.packets.p2017

import classes.toplayer.Standard
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


    fun getStandardTyreCompound() = when (tyreCompound.toInt()) {
        0 -> Standard.TYRES.ULTRASOFT
        1 -> Standard.TYRES.SUPERSOFT
        2 -> Standard.TYRES.SOFT
        3 -> Standard.TYRES.MEDIUM
        4 -> Standard.TYRES.HARD
        5 -> Standard.TYRES.INTER
        6 -> Standard.TYRES.WET
        else -> Standard.UNKNOWN
    }

    fun getStandardDriverId(era: Byte?): Int {
        era?.let {
            return when (era.toInt()) {
                Standard.ERA.MODERN_2017 -> {
                    return when (driverId.toInt()) {
                        9 -> Standard.DRIVERS.HAMILTON
                        15 -> Standard.DRIVERS.BOTTAS
                        16 -> Standard.DRIVERS.RICCIARDO
                        22 -> Standard.DRIVERS.VERSTAPPEN
                        0 -> Standard.DRIVERS.VETTEL
                        6 -> Standard.DRIVERS.RAIKKONEN
                        5 -> Standard.DRIVERS.PEREZ
                        33 -> Standard.DRIVERS.OCON
                        3 -> Standard.DRIVERS.MASSA
                        35 -> Standard.DRIVERS.STROLL
                        2 -> Standard.DRIVERS.ALONSO
                        34 -> Standard.DRIVERS.VANDOORNE
                        23 -> Standard.DRIVERS.SAINZ
                        1 -> Standard.DRIVERS.KVYAT
                        7 -> Standard.DRIVERS.GROSJEAN
                        14 -> Standard.DRIVERS.MAGNUSSEN
                        10 -> Standard.DRIVERS.HULKENBERG
                        20 -> Standard.DRIVERS.PALMER
                        18 -> Standard.DRIVERS.ERICCSON
                        31 -> Standard.DRIVERS.WEHRLEIN
                        else -> Standard.UNKNOWN
                    }
                }
                Standard.ERA.CLASSIC_2017 -> {
                    return when (driverId.toInt()) {
                        23 -> Standard.DRIVERS.BARNES
                        1 -> Standard.DRIVERS.GILES
                        16 -> Standard.DRIVERS.MURRAY
                        68 -> Standard.DRIVERS.ROTH
                        2 -> Standard.DRIVERS.CORREIA
                        3 -> Standard.DRIVERS.LEVASSEUR
                        24 -> Standard.DRIVERS.SCHIFFER
                        4 -> Standard.DRIVERS.FOREST
                        20 -> Standard.DRIVERS.LETOURNEAUU
                        6 -> Standard.DRIVERS.SAARI
                        9 -> Standard.DRIVERS.ATIYEH
                        18 -> Standard.DRIVERS.CALABRESI
                        22 -> Standard.DRIVERS.IZUM
                        10 -> Standard.DRIVERS.CLARKE
                        8 -> Standard.DRIVERS.KAUFMANN
                        14 -> Standard.DRIVERS.LAURSEN
                        31 -> Standard.DRIVERS.NIEVES
                        7 -> Standard.DRIVERS.BELOUSOV
                        0 -> Standard.DRIVERS.MICHALSKI
                        5 -> Standard.DRIVERS.MORENO
                        15 -> Standard.DRIVERS.COPPENS
                        32 -> Standard.DRIVERS.VISSER
                        33 -> Standard.DRIVERS.WALDMULLER
                        34 -> Standard.DRIVERS.QUESADA
                        else -> Standard.UNKNOWN
                    }
                }
                else -> Standard.UNKNOWN
            }

        }
        return Standard.UNKNOWN
    }
/*
     fun getStandardAI() = when (.toInt()) {
            0 -> Standard.AI.HUMAN
            1 -> Standard.AI.AI
            else -> Standard.UNKNOWN
        }*/


    companion object{
        const val SIZE = 45
    }
}