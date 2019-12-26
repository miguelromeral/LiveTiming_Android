package es.miguelromeral.f1.codemasters.livetiming.ui.runnables

import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.MyHandlerThread

class SectorsUpdater (private var myHandlerThread: MyHandlerThread,
                      private var items: List<ItemLiveTiming>,
                      private var session: Game,
                      private var position: Int
) : Runnable {

    override fun run(){


        var item = items?.get(position)
        val player = session.getPlayerByNameOrID(item.fullname, id = item.driverId)

        if(player != null && item != null){

            player.participant.value?.let{
                item.name = it.shortName()
                item.team = it.teamId?.value
                item.format = it.format
                item.driverId = it.driverId?.value
                item.fullname = it.name?.value
            }
            player.currentLap.value?.let{
                synchronized(it) {
                    item.position = it.carPosition?.value
                    item.time = it.currentLapTime?.value
                    item.pitStatus = it.pitStatus?.value
                    item.sector1Time = it.lastSector1Time
                    item.sector2Time = it.lastSector2Time
                    item.sector3Time = it.lastSector3Time
                    item.bestSector1Time = it.bestSector1Time
                    item.bestSector2Time = it.bestSector2Time
                    item.bestSector3Time = it.bestSector3Time
                }
            }
            player.carStatus.value?.let{
                synchronized(it){
                    item.compound = it.tyreCompound?.value
                }
            }

            myHandlerThread.sendOrder(item)

        }
    }

}

