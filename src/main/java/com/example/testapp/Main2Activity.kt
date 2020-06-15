package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {
    /*val itemRepository = arrayListOf(
        StuffInfo(R.drawable.ball1,"농구공",1000,100,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.bicycle1,"자전거",5000,500,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.cosmetics1,"화장품",325000,511,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.cube1,"큐브",5000,650,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.cup1,"컵",1000,50,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.doll1,"인형",5000,1000,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.earphones1,"이어폰",325000,566,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.hat1,"모자",5000,514,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.pan1,"선풍기",1000,999,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.phone1,"핸드폰",5000,500,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.shirt1,"셔츠",325000,100,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.shoes1,"신발",5000,950,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.stove1,"오븐",1000,980,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.toy1,"장난감",5000,50,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.umbrella1,"우산",325000,300,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.wallet1,"지갑",5000,540,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요"),
        StuffInfo(R.drawable.watch1,"시계",5000,5000,Category.valueOf("Hobby").category,Period.valueOf("oneweek").period,"아 너무 좋아요")
    )*/
    lateinit var loginuser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        loginuser = intent.getParcelableExtra<FirebaseUser>("user")

        val navController = findNavController((R.id.nav_host_fragment))

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("NAVI","${destination.label}")
        }
        Toast.makeText(this,"${loginuser.displayName}님 환영합니다!",Toast.LENGTH_SHORT).show()
    }

}
