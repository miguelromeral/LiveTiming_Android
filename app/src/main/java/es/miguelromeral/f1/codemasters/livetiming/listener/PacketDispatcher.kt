package es.miguelromeral.f1.codemasters.livetiming.listener

import es.miguelromeral.f1.codemasters.livetiming.classes.Game
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketHeader
import es.miguelromeral.f1.codemasters.livetiming.packets.PacketLapData
import es.miguelromeral.f1.codemasters.livetiming.packets.SessionData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.PacketParticipantData
import es.miguelromeral.f1.codemasters.livetiming.packets.shortFromPacket
import timber.log.Timber

class PacketDispatcher(val content: ByteArray, var session: Game) : Runnable {

    override fun run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
        try {
        when(shortFromPacket(content.sliceArray(0..1)).toInt()){
                Controller.FORMAT_2018 -> {
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
                        //PacketCarTelemetryData.PACKET_ID -> session.newTelemetry2018(PacketCarTelemetryData.create(header, content))

                        //EventData.PACKET_ID -> EventData(content)

                        else ->
                            Timber.i("Packet not identified by its ID")
                    }
                }
                Controller.FORMAT_2019 -> {

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