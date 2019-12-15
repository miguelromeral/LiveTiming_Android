package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import es.miguelromeral.f1.codemasters.livetiming.classes.Game

class MyRunnable (private var myHandlerThread: MyHandlerThread,
                  private var item: ItemLiveTiming,
                  private var session: Game
) : Runnable {

    override fun run(){
        //var count = 0
        //while(count < 10){

        val player = session.players.value?.filter { it.participant.value?.name?.value == item?.name }?.firstOrNull()

            player?.let {player ->

                player.participant.value?.let{
                    item.name = it.name?.value?.toString()
                    item.team = it.teamId?.value
                }
                player.currentLap.value?.let{
                    item.position = it.carPosition?.value
                    item.time = it.currentLapTime?.value
                }

                myHandlerThread.sendOrder(item)

            }
           /* try{
                Thread.sleep(1000)
            }catch(e: InterruptedException){
                e.printStackTrace()
            }*/
        //}
    }

}