package es.miguelromeral.f1.codemasters.livetiming.ui.models

import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.classes.*
import java.text.Format


class ItemLiveTiming private constructor()
{
    var position: UByte? = null
    var name: String? = null
    var fullname: String? = null
    var driverId: Byte? = null
    var team: Byte? = null
    var time: Float? = null
    var compound: Byte? = null
    var era: Byte? = null
    var sector1Time: Float? = null
    var sector2Time: Float? = null
    var sector3Time: Float? = null
    var bestSector1Time: Float? = null
    var bestSector2Time: Float? = null
    var bestSector3Time: Float? = null
    var bestSessionSector1Time: Float? = null
    var bestSessionSector2Time: Float? = null
    var bestSessionSector3Time: Float? = null
    var pitStatus: Byte? = null
    var format = Standard.UNKNOWN

    fun getTeamAsInt() = team?.toInt()
    fun getCompoundAsInt() = compound?.toInt()
    fun getEraAsInt() = era?.toInt()

    companion object{
        fun create(participant: Participant?, lap: Lap?, carStatus: CarStatus?, session: Session?, game: Game?): ItemLiveTiming{
            return ItemLiveTiming().apply {
                participant?.let{
                    name = it.shortName()
                    fullname = it.name.value
                    driverId = it.driverId.value
                    team = it.teamId.value
                    format = it.format
                }
                lap?.let{
                    position = it.carPosition.value
                    time = it.currentLapTime?.value
                    pitStatus = it.pitStatus.value
                    sector1Time = it.lastSector1Time
                    sector2Time = it.lastSector2Time
                    sector3Time = it.lastSector3Time
                    bestSector1Time = it.bestSector1Time
                    bestSector2Time = it.bestSector2Time
                    bestSector3Time = it.bestSector3Time
                }
                session?.let{
                    era = it.era.value
                }
                carStatus?.let{
                    compound = it.tyreCompound.value
                }
                game?.let{
                    bestSessionSector1Time = it.bestSector1Time
                    bestSessionSector2Time = it.bestSector2Time
                    bestSessionSector3Time = it.bestSector3Time
                }
            }
        }
    }
}