package view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.codesquad.kotlin_drawingapp.R
import model.BackGroundColor
import model.Rect
import model.RectView
import presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mainLayout: FrameLayout
    private lateinit var presenter: MainPresenter
    private var selectedRectangle :Rect? = null
    private var selectedCustomRectangleView: RectView? = null
    private val backgroundObserver = Observer<BackGroundColor>{ colorValue->
        selectedCustomRectangleView?.colorChange(colorValue)
        findViewById<TextView>(R.id.tv_rgb_value).text= colorValue.getRGBHexValue()
    }
    private val opacityObserver = Observer<Int> { opacity->
        this.selectedCustomRectangleView?.opacityChange(opacity)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, this)
        mainLayout = findViewById(R.id.image_container)
        val btnMakeRectangle = findViewById<Button>(R.id.btn_addRectangle)
        val tvRGBValue = findViewById<TextView>(R.id.tv_rgb_value)
        val opacitySeekBar = findViewById<SeekBar>(R.id.seekbar_opacity)

        btnMakeRectangle.setOnClickListener {
            presenter.createRectanglePaint()
        }

        mainLayout.setOnTouchListener { _, motionEvent ->
            if(selectedCustomRectangleView==null){
                presenter.selectRectangle(motionEvent.x, motionEvent.y)
            }
            else{
                selectedCustomRectangleView?.let{
                    it.eraseBorder()
                    this.selectedRectangle?.backGroundColor?.removeObserver(backgroundObserver)
                    presenter.selectRectangle(motionEvent.x, motionEvent.y)
                }
            }
            true
        }

        tvRGBValue.setOnClickListener {
            selectedCustomRectangleView?.let { selectedView -> presenter.changeColor(selectedView,) }
        }
        opacitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(opacitySeekBar: SeekBar?, opacity: Int, fromUser: Boolean) {
                selectedCustomRectangleView?.let { presenter.changeOpacity(it, opacity) }
            }
            override fun onStartTrackingTouch(opacitySeekBar: SeekBar?) {}
            override fun onStopTrackingTouch(opacitySeekBar: SeekBar?) {}
        })
    }

    override fun drawRectangle(rectView: RectView) {
        mainLayout.addView(rectView)
    }

    override fun drawBorder(rectView: RectView?) {
        this.selectedCustomRectangleView = rectView
        rectView?.drawBorder()
    }

    override fun displayAttribute(rect: Rect) {
        val rgbValueTextView = findViewById<TextView>(R.id.tv_rgb_value)
        val opacitySeekBar = findViewById<SeekBar>(R.id.seekbar_opacity)
        rgbValueTextView.text = rect.backGroundColor.value?.getRGBHexValue()
        this.selectedRectangle= rect
        opacitySeekBar.progress = rect.opacity?.value!!
        rect.backGroundColor.observe(this, backgroundObserver)
        rect.opacity.observe(this, opacityObserver)
    }


}