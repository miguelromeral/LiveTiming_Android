package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.classes.Player
import es.miguelromeral.f1.codemasters.livetiming.databinding.ItemLiveTimingBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.main.floatToTimeFormatted
import es.miguelromeral.f1.codemasters.livetiming.ui.main.shared.GameViewModel
import kotlinx.coroutines.newFixedThreadPoolContext


class LiveTimingAdapter :
        ListAdapter<ItemLiveTiming, LiveTimingAdapter.ViewHolder>
            (LiveTimingDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }




    class ViewHolder private constructor(val binding: ItemLiveTimingBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: ItemLiveTiming, position: Int){
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
                binding.tvCurrentTime.text = floatToTimeFormatted(it)
            }
            item.team?.let{
                binding.ivColor.setColorFilter(ContextCompat.getColor(binding.ivColor.context, getColorByTeamId(it)))
                //binding.ivColor.setBackgroundColor(R.color.colorAccent)
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
                return ViewHolder(binding)

            }
        }

    }


}

class LiveTimingDiffCallback : DiffUtil.ItemCallback<ItemLiveTiming>(){

    override fun areItemsTheSame(oldItem: ItemLiveTiming, newItem: ItemLiveTiming): Boolean {
        return oldItem.name == newItem.name && oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: ItemLiveTiming, newItem: ItemLiveTiming): Boolean{
        return oldItem.equals(newItem)
    }

}