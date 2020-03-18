package com.fossil.stackoverflowuser.modules.reputation.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fossil.stackoverflowuser.R
import com.fossil.stackoverflowuser.entities.Reputation
import com.fossil.stackoverflowuser.services.Config.VIEW_TYPE_ITEM
import com.fossil.stackoverflowuser.services.Config.VIEW_TYPE_LOADING

class ReputationAdapter(
    private val context: Context,
    var reputations: MutableList<Reputation?>? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvType: TextView? = null
        var tvChange: TextView? = null
        var tvPostId: TextView? = null
        var tvCreateAt: TextView? = null

        init {
            tvType = view.findViewById(R.id.tv_type)
            tvChange = view.findViewById(R.id.tv_change)
            tvPostId = view.findViewById(R.id.tv_post_id)
            tvCreateAt = view.findViewById(R.id.tv_create_at)
        }
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return if (reputations?.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_reputation, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return reputations?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val reputation = reputations?.get(position)

            holder.tvType?.text =
                context.getString(R.string.reputation_type, reputation?.reputationHistoryType)
            holder.tvChange?.text =
                context.getString(R.string.change, reputation?.reputationChange.toString())
            holder.tvPostId?.text =
                context.getString(R.string.post_id, reputation?.postId.toString())

            //Calculate Time from create reputation to now
            val lastAccess = DateUtils.getRelativeTimeSpanString(
                reputation?.creationDate!! * 1000,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            )
            holder.tvCreateAt?.text = lastAccess
        }
    }


}