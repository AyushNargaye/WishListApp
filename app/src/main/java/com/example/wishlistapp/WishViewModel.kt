package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.Wishrepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishrepository: Wishrepository = Graph.wishRepository
):ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String){
        wishTitleState = newString
    }
    fun onWishDescriptionChanged(newString: String){
        wishDescriptionState = newString
    }


    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishrepository.getWishes()
        }
    }
    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishrepository.addAWish(wish = wish)
        }
    }

    fun getAWisById(id:Long):Flow<Wish> {
        return wishrepository.getAWishById(id)
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishrepository.updateAwish(wish = wish)
        }
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishrepository.deleteAWish(wish = wish)
        }
    }

}