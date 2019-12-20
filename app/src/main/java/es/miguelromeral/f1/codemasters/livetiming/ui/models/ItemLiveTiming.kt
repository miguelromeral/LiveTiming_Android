package es.miguelromeral.f1.codemasters.livetiming.ui.models

import es.miguelromeral.f1.codemasters.livetiming.classes.CarStatus
import es.miguelromeral.f1.codemasters.livetiming.classes.Lap
import es.miguelromeral.f1.codemasters.livetiming.classes.Participant
import es.miguelromeral.f1.codemasters.livetiming.classes.Session
import es.miguelromeral.f1.codemasters.livetiming.standard.Format


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
    var format: Format = Format.UNKNOWN

    fun getTeamAsInt() = team?.toInt()
    fun getCompoundAsInt() = compound?.toInt()
    fun getEraAsInt() = era?.toInt()

    companion object{
        fun create(participant: Participant?, lap: Lap?, carStatus: CarStatus?, session: Session?): ItemLiveTiming{
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
                    sector1Time = it.lastSector1Time
                    sector2Time = it.lastSector2Time
                    sector3Time = it.lastSector3Time
                }
                session?.let{
                    era = it.era.value
                }
                carStatus?.let{
                    compound = it.tyreCompound.value
                }
            }
        }
    }
}