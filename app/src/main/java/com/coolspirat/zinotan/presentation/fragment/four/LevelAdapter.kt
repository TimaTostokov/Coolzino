package com.coolspirat.zinotan.presentation.fragment.four


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.model.Level

class LevelsAdapter(
    private val levels: List<Level>,
    private val onLevelClick: (Level) -> Unit
) : RecyclerView.Adapter<LevelsAdapter.LevelViewHolder>() {

    inner class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgLevel: ImageView = itemView.findViewById(R.id.imgLevel)
        private val imgLock: ImageView = itemView.findViewById(R.id.imgLock)

        fun bind(level: Level) {
            imgLevel.setImageResource(level.imageResId)

            if (level.isUnlocked) {
                imgLock.visibility = View.GONE
            } else {
                imgLock.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                onLevelClick(level)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_level, parent, false)
        return LevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        holder.bind(levels[position])
    }

    override fun getItemCount(): Int = levels.size
}
