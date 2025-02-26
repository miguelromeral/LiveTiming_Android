package es.miguelromeral.f1.codemasters.livetiming.listener

import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.packets.*
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarStatusData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketCarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import timber.log.Timber

class PacketDispatcher(val content: ByteArray, var session: Game) : Runnable {

    override fun run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
        try {
        when(Controller.getFormat(content)){
                Standard.FORMAT.F18 -> {
                    val header = PacketHeader.create(content)
                    //Timber.i("Testing - New packet received | ID: "+ header.id)
                    when (header.id.toInt()) {
                        SessionData.PACKET_ID -> session.newSessionData2018(
                            SessionData.create(
                                header,
                                content
                            )
                        )
                        PacketLapData.PACKET_ID -> {
                            //Timber.i("Lap Pa: ${content.contentToString()}")
                            session.newLapData2018(
                            PacketLapData.create(
                                header,
                                content
                            ))
                        }
                        PacketParticipantData.PACKET_ID -> session.newParticipants2018(
                            PacketParticipantData.create(header, content)
                        )
                        PacketCarStatusData.PACKET_ID -> session.newCarStatus2018(
                            PacketCarStatusData.create(header, content)
                        )
                        PacketCarTelemetryData.PACKET_ID ->
                            session.newTelemetry2018(
                                PacketCarTelemetryData.create(header, content)
                            )
                        EventData.PACKET_ID ->
                            session.newEventData2018(EventData.create(header, content))

                        else ->
                            Timber.i("Packet not identified by its ID")
                    }
                }
                Standard.FORMAT.F19 -> {

                }

                else -> {
                    // 2017 Format doesn't have the format in the first two bytes
                    //Timber.i("Testing - Unknown packet format received: proccessed as 2017")
                    session.newData2017(Packet2017.create(content))
                }

        }

        }catch(e: Exception){
            Timber.e("Error in PacketDispatcher: "+e.message)
        }


    }

}