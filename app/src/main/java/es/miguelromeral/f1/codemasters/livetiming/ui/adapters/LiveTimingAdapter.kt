package es.miguelromeral.f1.codemasters.livetiming.ui.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.ItemLiveTimingBinding
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.ui.floatToTimeFormatted
import es.miguelromeral.f1.codemasters.livetiming.ui.getTyreIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


class LiveTimingAdapter :
        ListAdapter<DataItem, RecyclerView.ViewHolder>
            (LiveTimingDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ItemLiveTiming -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val item = getItem(position) as DataItem.ItemLiveTiming
                holder.bind(item, position)
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

    fun addHeaderAndSubmitList(list: List<DataItem.ItemLiveTiming>?){
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list
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

        fun bind(item: DataItem.ItemLiveTiming, position: Int){
            binding.rootItemLiveTiming.let {
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        it.context,
                        if (position % 2 == 0)
                            /*if(position == 0)
                                R.color.colorPrimary
                            else*/
                                R.color.liveTimingListOdd
                        else
                            R.color.liveTimingListEven
                    )
                )
            }
            binding.item = item
            binding.tvName.text = item.name
            item.position?.let{
                binding.tvPos.text = it.toString()
            }
            item.time?.let{
                binding.tvCurrentTime.text =
                    floatToTimeFormatted(it)
            }
            item.team?.let{
                binding.ivColor.setColorFilter(ContextCompat.getColor(binding.ivColor.context, getColorByTeamId(it)))
                //binding.ivColor.setBackgroundColor(R.color.colorAccent)
            }
            item.compound?.let{
                binding.ivCompound.setImageResource(getTyreIcon(item.format, item.era, it))
            }
        }

        fun getColorByTeamId(teamId: UByte): Int =
            when(teamId.toInt()){
                0 -> R.color.teamMercedes
                1 -> R.color.teamFerrari
                2 -> R.color.teamRedBull
                3 -> R.color.teamWilliams
                4 -> R.color.teamRacingPoint
                5 -> R.color.teamRenault
                6 -> R.color.teamToroRosso
                7 -> R.color.teamHaas
                8 -> R.color.teamMcLaren
                9 -> R.color.teamAlfaRomeo
                /*
                10 -> "McLaren 1988"
                11 -> "McLaren 1991"
                12 -> "Williams 1992"
                13 -> "Ferrari 1995"
                14 -> "Williams 1996"
                15 -> "McLaren 1998"
                16 -> "Ferrari 2002"
                17 -> "Ferrari 2004"
                18 -> "Renault 2006"
                19 -> "Ferrari 2007"
                20 -> "McLaren 2008"
                21 -> "RedBull 2010"
                22 -> "Ferrari 1976"
                34 -> "McLaren 1976"
                35 -> "Lotus 1972"
                36 -> "Ferrari 1979"
                37 -> "McLaren 1982"
                38 -> "Williams 2003"
                39 -> "Brawn 2009"
                40 -> "Lotus 1978"*/
                else -> R.color.teamUnknown
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLiveTimingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )

            }
        }

    }


}

class LiveTimingDiffCallback : DiffUtil.ItemCallback<DataItem>(){

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        val oh = oldItem.id == DataItem.HEADER_NAME
        val nh = newItem.id == DataItem.HEADER_NAME

        if(oh && nh)
            return true

        if(oh || nh)
            return false

        val oi = oldItem as DataItem.ItemLiveTiming
        val ni = newItem as DataItem.ItemLiveTiming

        return oi.name == ni.name && oi.time == ni.time
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean{
        return oldItem.equals(newItem)
    }

}


sealed class DataItem {

    abstract val id: String

    data class ItemLiveTiming (
        var position: UByte?,
        var name: String?,
        var team: UByte?,
        var time: Float?,
        var compound: UByte?,
        var era: UByte?,
        var format: Format = Format.UNKNOWN) : DataItem(){

        override val id = name ?: "DataItem"
    }

    object Header : DataItem(){

        override val id = HEADER_NAME
    }

    companion object{
        const val HEADER_NAME = "HEADER_NAME_F1_LIVE_TIMING"
    }
}