package es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.DataItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.LiveTimingFragment
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import timber.log.Timber

class MyHandlerThread(private var uiHandler: LiveTimingFragment.UiHandler) : HandlerThread("MyHandlerThread") {

    private var handler: Handler? = null


    private fun getHandler(looper: Looper): Handler {
        return object:Handler(looper){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)

                var item = msg?.obj as ItemLiveTiming

                val processedMessage = Message()
                processedMessage.obj = item
                uiHandler.sendMessage(processedMessage)
            }
        }
    }

    fun sendOrder(itemLiveTiming: ItemLiveTiming){
        try {
            val message = Message()
            message.obj = itemLiveTiming
            handler?.sendMessage(message)
        }catch (e: Exception){
            Timber.i("Exception: "+e.message)
            Timber.i("Exception: "+e.printStackTrace())
        }
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = getHandler(looper)
    }
}
