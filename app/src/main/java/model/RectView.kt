package model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class RectView(context: Context,  val rect:Rect) : View(context) {

    private val left= rect.point.xPos.toFloat()
    private val right= (rect.point.xPos + rect.size.width).toFloat()
    private val top= rect.point.yPos.toFloat()
    private val bottom = (rect.point.yPos + rect.size.height).toFloat()
    private var redValue = rect.backGroundColor.value?.redValue
    private var greenValue = rect.backGroundColor.value?.greenValue
    private var blueValue = rect.backGroundColor.value?.blueValue
    private var opacity= ((rect.opacity.value?.times(25.5))?.toInt())
    private var selectedBorder = false


    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        val paint= Paint()
        paint.style = Paint.Style.FILL
        paint.setColor(rect.backGroundColor.value?.getBackGroundColor()!!)
        paint.setARGB(opacity!!, redValue!!, greenValue!!, blueValue!!)
        canvas.drawRect(left,top,right,bottom, paint)
        if(selectedBorder) {
            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK
            paint.strokeWidth= 2.0F
            canvas.drawRect(left, top, right, bottom, paint)
        }
    }

    fun drawBorder(){
        this.selectedBorder=true
        invalidate()
    }

    fun eraseBorder(){
        this.selectedBorder=false
        invalidate()
    }

    fun colorChange(color:BackGroundColor){
        this.redValue= color.redValue
        this.blueValue= color.blueValue
        this.greenValue= color.greenValue
        invalidate()
    }

    fun opacityChange(opacity:Int){
        this.opacity =  (opacity*25.5).toInt()
        invalidate()
    }


}
