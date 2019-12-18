package es.miguelromeral.f1.codemasters.livetiming.ui.runnables

import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.DataItemLiveTiming
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
                    val tmp_pos = it.carPosition?.value
                    val tmp_lap = it.currentLapTime?.value
                    item.position = tmp_pos
                    item.time = tmp_lap
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

