package com.haile.stackoverflowuser.modules.userlist.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.haile.stackoverflowuser.R
import com.haile.stackoverflowuser.entities.UserData
import com.haile.stackoverflowuser.services.Config.VIEW_TYPE_ITEM
import com.haile.stackoverflowuser.services.Config.VIEW_TYPE_LOADING


class UserAdapter(private val context: Context, var users: MutableList<UserData?>? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener : UserClickListener? = null

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvUserName: TextView? = null
        var tvReputation: TextView? = null
        var tvLocation: TextView? = null
        var tvLastAccess: TextView? = null
        var imUserAvatar: ImageView? = null
        var btnBookmark: ToggleButton? = null
        var layoutUser: LinearLayout? = null

        init {
            tvUserName = view.findViewById(R.id.tv_user_name)
            tvReputation = view.findViewById(R.id.tv_reputation)
            tvLocation = view.findViewById(R.id.tv_location)
            tvLastAccess = view.findViewById(R.id.tv_last_access)
            imUserAvatar = view.findViewById(R.id.im_user_avatar)
            btnBookmark = view.findViewById(R.id.btn_bookmark)
            layoutUser = view.findViewById(R.id.layout_item_user)
        }
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return if (users?.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_ITEM){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_loading, parent, false)
            LoadingViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return users?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder){
            val user = users?.get(position)
            holder.tvUserName?.text = user?.displayName
            holder.tvReputation?.text = context.resources.getString(R.string.reputation, user?.reputation.toString())
            holder.tvLocation?.text = user?.location

            //Check time from last access to now
            val lastAccess = DateUtils.getRelativeTimeSpanString(user?.lastAccessDate!! * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
            holder.tvLastAccess?.text = lastAccess
            Glide.with(context)
                .load(user.profileImage)
                .placeholder(R.drawable.indicator_animation)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imUserAvatar!!)

            //Set UI for bookmark or de-bookmark mode
            if(user.isBookmark){
                holder.btnBookmark?.isChecked = true
                holder.btnBookmark?.textOn = context.getString(R.string.de_bookmark)
            } else {
                holder.btnBookmark?.textOff = context.getString(R.string.bookmark)
                holder.btnBookmark?.isChecked = false
            }

            holder.btnBookmark?.setOnClickListener {
                if(!user.isBookmark){
                    //Change item to book mark mode
                    listener?.onClickBookmark(true, user)
                    user.isBookmark = true
                    holder.btnBookmark?.textOn = context.getString(R.string.de_bookmark)
                } else {
                    // De-bookmark item
                    listener?.onClickBookmark(false, user)
                    user.isBookmark = false
                    holder.btnBookmark?.textOff = context.getString(R.string.bookmark)
                }
            }
            holder.layoutUser?.setOnClickListener{
                listener?.onClickUser(user.userId!!, user.displayName)
            }
        }
    }

    interface UserClickListener{
        fun onClickUser(userId: Int, userName: String?)
        fun onClickBookmark(isBookmark : Boolean, user: UserData)
    }
}