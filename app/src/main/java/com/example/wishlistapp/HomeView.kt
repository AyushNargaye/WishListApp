package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController, viewModel: WishViewModel
) {
   // val navController = rememberNavController()
 //   val viewModel: WishViewModel = viewModel()
    val context = LocalContext.current
    Scaffold(
        topBar = {AppBarView(
            title = "WishList", {

            Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
        },
          //  modifier = Modifier.padding(top = 8.dp, start = 5.dp, end = 5.dp)
            )},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp), contentColor = Color.Black,
                onClick = {
                    Toast.makeText(context, "Floating Action Button Clicked", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.AddScreen.route + "/0L")
                    // TODO Add Navigation to add screen

                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ){
//        LazyColumn(modifier = Modifier
//            .fillMaxSize()
//            .padding(it))
//        {
//            items(DummyWish.wishList){
//                    wish -> WishItem(wish = wish) { }
//            }
//        }
//    }
//
//}
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
            items(wishlist.value, key = {wish -> wish.id}) {
                wish ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it== DismissValue.DismissedToStart){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == DismissDirection.EndToStart)
                            Color.Red else Color.Transparent,label = ""
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd // Or any alignment you want
                        ){
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                        }

                    },
                    directions = setOf( DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(2f)},
                    dismissContent = {
                        WishItem(wish = wish) {
                            // Handle item click if needed
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                    )


            }
        }
    }
}




@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp,end = 8.dp)
        .clickable {
        onClick()
    },
//        elevation = 10.dp,
//        backgroundColor = Color.White
        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)

        }
    }
}