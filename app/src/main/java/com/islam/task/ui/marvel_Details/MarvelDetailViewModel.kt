package com.islam.task.ui.marvel_Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.islam.task.data.Repository
import com.islam.task.pojo.Item
import com.islam.task.project_base.base.other.BaseViewModel
import com.islam.task.project_base.base.other.SingleLiveEvent
import kotlinx.coroutines.launch

class MarvelDetailViewModel :BaseViewModel(){

    val repository = Repository()

    val comics :MutableLiveData<List<Item>> = MutableLiveData()
    val events :MutableLiveData<List<Item>> = MutableLiveData()
    val stories :MutableLiveData<List<Item>> = MutableLiveData()
    val series :MutableLiveData<List<Item>> = MutableLiveData()

    val showImageFlipper : SingleLiveEvent<Pair<Int,List<Item>>> = SingleLiveEvent()

    fun loadComics(viewId:Int,marvelId:String){
        viewModelScope.launch {
            comics.value =networkCall (viewId){ repository.getComics(marvelId) }.value?.data?.results
        }
    }
    fun loadEvents(viewId:Int,marvelId:String){
        viewModelScope.launch {
            events.value =networkCall (viewId){ repository.getEvents(marvelId) }.value?.data?.results
        }
    }
    fun loadStories(viewId:Int,marvelId:String){
        viewModelScope.launch {
            stories.value =networkCall (viewId){ repository.getStories(marvelId) }.value?.data?.results
        }
    }
    fun loadSeries(viewId:Int,marvelId:String){
        viewModelScope.launch {
            series.value =networkCall (viewId){ repository.getSeries(marvelId) }.value?.data?.results
        }
    }

}