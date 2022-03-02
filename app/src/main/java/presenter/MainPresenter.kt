package presenter

import Plane
import android.content.Context
import model.RectFactory
import model.RectView
import view.MainContract

class MainPresenter(
    private val view: MainContract.View,
    private val context: Context
) : MainContract.Presenter {

    private val plane = Plane(context)

    override fun selectRectangle(xPos: Float, yPos: Float) {
        val selectedRectangle = plane.getCustomRectangleByPosition(xPos, yPos)
        val selectedRectangleView= plane.getCustomRectangleViewByPosition(xPos,yPos)
        view.drawBorder(selectedRectangleView)
        selectedRectangle?.let {
            view.displayAttribute(selectedRectangle)
        }
    }

    override fun changeColor(rectView: RectView) {
        plane.changeColor(rectView)
    }

    override fun changeOpacity(rectView: RectView, opacity: Int) {
        plane.changeOpacity(rectView, opacity)
    }

    override fun createRectanglePaint() {
        val rectPaint = plane.createRectanglePaint(RectFactory.makeRect())
        view.drawRectangle(rectPaint)
    }

}

