package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.DataItem
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.LiveTimingFragment

class MyHandlerThread(private var uiHandler: LiveTimingFragment.UiHandler) : HandlerThread("MyHandlerThread") {

    private var handler: Handler? = null


    private fun getHandler(looper: Looper): Handler {
        return object:Handler(looper){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)

                var item = msg?.obj as DataItem.ItemLiveTiming

                val processedMessage = Message()
                processedMessage.obj = item
                uiHandler.sendMessage(processedMessage)
            }
        }
    }

    fun sendOrder(item: DataItem.ItemLiveTiming){
        val message = Message()
        message.obj = item
        handler?.sendMessage(message)
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = getHandler(looper)
    }
}
