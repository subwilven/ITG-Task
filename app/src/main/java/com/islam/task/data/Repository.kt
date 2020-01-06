package com.islam.task.data

import com.islam.task.BuildConfig
import com.islam.task.pojo.Item
import com.islam.task.project_base.POJO.ApiResponse

import com.islam.task.project_base.base.other.network.NetworkModel
import com.islam.task.util.Utils.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository{

    suspend fun getComics(marvelId:String):ApiResponse<List<Item>>{
        return withContext(Dispatchers.IO){
            val timestamp =(System.currentTimeMillis()/1000).toString()
            NetworkModel.clientApi!!.getComics(marvelId,
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (timestamp+BuildConfig.PRIVATE_KEY+BuildConfig.PUBLC_KEY).toMD5())
        }
    }

    suspend fun getEvents(marvelId:String):ApiResponse<List<Item>>{
        return withContext(Dispatchers.IO){
            val timestamp =(System.currentTimeMillis()/1000).toString()
            NetworkModel.clientApi!!.getEvents(marvelId,
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (timestamp+BuildConfig.PRIVATE_KEY+BuildConfig.PUBLC_KEY).toMD5())
        }
    }


    suspend fun getSeries(marvelId:String):ApiResponse<List<Item>>{
        return withContext(Dispatchers.IO){
            val timestamp =(System.currentTimeMillis()/1000).toString()
            NetworkModel.clientApi!!.getSeries(marvelId,
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (timestamp+BuildConfig.PRIVATE_KEY+BuildConfig.PUBLC_KEY).toMD5())
        }
    }


    suspend fun getStories(marvelId:String):ApiResponse<List<Item>>{
        return withContext(Dispatchers.IO){
            val timestamp =(System.currentTimeMillis()/1000).toString()
            NetworkModel.clientApi!!.getStories(marvelId,
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (timestamp+BuildConfig.PRIVATE_KEY+BuildConfig.PUBLC_KEY).toMD5())
        }
    }
}

