package es.miguelromeral.f1.codemasters.livetiming.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.ItemLiveTimingBinding
import es.miguelromeral.f1.codemasters.livetiming.standard.PIT_STATUS
import es.miguelromeral.f1.codemasters.livetiming.ui.floatToTimeFormatted
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.setBackgroundByPosition
import es.miguelromeral.f1.codemasters.livetiming.ui.setColorBySector
import es.miguelromeral.f1.codemasters.livetiming.ui.setColorByTeam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


class LiveTimingAdapter :
        ListAdapter<DataItemLiveTiming, RecyclerView.ViewHolder>
            (LiveTimingDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItemLiveTiming.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItemLiveTiming.Content -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val item = getItem(position) as DataItemLiveTiming.Content
                holder.bind(item.data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<ItemLiveTiming>?){
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItemLiveTiming.Header)
                else -> listOf(DataItemLiveTiming.Header) + list.map {
                    DataItemLiveTiming.Content(it)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }



    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {

        companion object{
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_live_timing, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemLiveTimingBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(itemLiveTiming: ItemLiveTiming){
            setBackgroundByPosition(binding.rootItemLiveTiming, itemLiveTiming.position)
            binding.item = itemLiveTiming
            binding.tvCurrentTime.text = if(itemLiveTiming.pitStatus?.toInt() == PIT_STATUS.NONE) floatToTimeFormatted(itemLiveTiming.time) else ""

            itemLiveTiming?.let{item ->



                binding.tvSector1Time?.let{
                    it.setTextColor(it.context.getColor(setColorBySector(item.sector1Time, item.bestSector1Time, item.bestSessionSector1Time)))
                }
                binding.tvSector2Time?.let{
                    it.setTextColor(it.context.getColor(setColorBySector(item.sector2Time, item.bestSector2Time, item.bestSessionSector2Time)))
                }
                binding.tvSector3Time?.let{
                    it.setTextColor(it.context.getColor(setColorBySector(item.sector3Time, item.bestSector3Time, item.bestSessionSector3Time)))
                }
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLiveTimingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)

            }
        }

    }


}

class LiveTimingDiffCallback : DiffUtil.ItemCallback<DataItemLiveTiming>(){

    override fun areItemsTheSame(oldItemLiveTiming: DataItemLiveTiming, newItemLiveTiming: DataItemLiveTiming): Boolean {
        val oh = oldItemLiveTiming.id == DataItemLiveTiming.HEADER_NAME
        val nh = newItemLiveTiming.id == DataItemLiveTiming.HEADER_NAME

        if(oh && nh)
            return true

        if(oh || nh)
            return false

        val oi = oldItemLiveTiming as DataItemLiveTiming.Content
        val ni = newItemLiveTiming as DataItemLiveTiming.Content

        return oi.data.name == ni.data.name && oi.data.time == ni.data.time
    }

    override fun areContentsTheSame(oldItemLiveTiming: DataItemLiveTiming, newItemLiveTiming: DataItemLiveTiming): Boolean{
        return oldItemLiveTiming.equals(newItemLiveTiming)
    }

}



sealed class DataItemLiveTiming {

    abstract val id: String

    data class Content (
        var data: ItemLiveTiming) : DataItemLiveTiming(){

        override val id = data.name ?: "DataItemLiveTiming"
    }

    object Header : DataItemLiveTiming(){

        override val id = HEADER_NAME
    }

    companion object{
        const val HEADER_NAME = "HEADER_NAME_F1_LIVE_TIMING"
    }
}