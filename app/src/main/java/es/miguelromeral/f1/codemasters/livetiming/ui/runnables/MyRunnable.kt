package es.miguelromeral.f1.codemasters.livetiming.ui.runnables

import es.miguelromeral.f1.codemasters.livetiming.classes.toplayer.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.MyHandlerThread

class MyRunnable (private var myHandlerThread: MyHandlerThread,
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
                    item.sector1Time = it.sector1Time?.value
                    item.sector2Time = it.sector2Time?.value
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

