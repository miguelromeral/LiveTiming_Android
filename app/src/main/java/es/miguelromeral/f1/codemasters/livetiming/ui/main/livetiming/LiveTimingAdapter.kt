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
            binding.ivColor.setColorFilter(ContextCompat.getColor(binding.ivColor.context, R.color.colorPrimary))
            binding.item = item
            binding.tvName.text = item.name
            item.position?.let{
                binding.tvPos.text = it.toString()
            }
            item.time?.let{
                binding.tvCurrentTime.text = floatToTimeFormatted(it)
            }
            item.team?.let{
                //binding.ivColor.setBackgroundColor(R.color.colorAccent)
            }
        }

        /*fun getColorByTeamId(teamId: UByte){
            when(teamId.toInt()){
                0 ->
            }
        }*/



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