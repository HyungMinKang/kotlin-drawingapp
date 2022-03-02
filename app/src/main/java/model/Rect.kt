package model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Rect(rectId:String, val point: Point, val size: Size, backGroundColor: BackGroundColor, opacity:Int) :ViewModel(){
    private val rectId=rectId
    var opacity = MutableLiveData<Int>(opacity)
    var backGroundColor = MutableLiveData<BackGroundColor>(backGroundColor)

    override fun toString(): String {
        return "${this.rectId}, $point $size $backGroundColor Alpha: $opacity"
    }
}