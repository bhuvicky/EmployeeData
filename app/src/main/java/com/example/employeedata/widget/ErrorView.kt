package com.example.employeedata.widget

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.employeedata.R
import com.example.employeedata.extensions.show

class ErrorView: LinearLayout {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var subtitleView: TextView
    private lateinit var retryView: Button

    private var retryListener: (() -> Unit)? = null

    constructor(context: Context): super(context) {
        println("log constructor1")
        init(context, null, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        // TODO; New Learn; custom view constructor called
        println("log constructor2")
        init(context, attrs, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {
        println("log constructor3")
        init(context, attrs, defStyle, null)
    }

    // call requires API Level 21, current min 19
    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int):
            super(context, attrs, defStyle, defStyleRes) {
        println("log constructor4")
        init(context, attrs, defStyle, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int?, defStyleRes: Int?) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView,
                defStyle ?: 0, defStyleRes ?: 0)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_error_view, this, true)
        orientation = VERTICAL

        imageView = findViewById(R.id.errorViewImage)
        titleView = findViewById(R.id.errorviewTitle)
        subtitleView = findViewById(R.id.errorviewSubtitle)
        retryView = findViewById(R.id.errorviewRetry)

        setDefaultValues(typedArray)
    }

    private fun setDefaultValues(a: TypedArray) {
        val imageRes: Int
        val imageTint: Int
        val imageVisible: Boolean
        val imageSize: Int

        val title: String?
        val titleColor: Int

        val subtitle: String?
        val subtitleColor: Int

        val titleVisible: Boolean
        val subtitleVisible: Boolean
        val retryVisible: Boolean

        val retryText: String?
        val retryBackground: Int
        val retryColor: Int

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_ev_image, R.drawable.error_view_cloud)
            imageTint = a.getColor(R.styleable.ErrorView_ev_imageTint, 0)
            imageVisible = a.getBoolean(R.styleable.ErrorView_ev_imageVisible, true)
            imageSize = a.getDimensionPixelSize(R.styleable.ErrorView_ev_imageSize, 0)

            title = a.getString(R.styleable.ErrorView_ev_title)
            titleColor = a.getColor(R.styleable.ErrorView_ev_titleColor,
                resources.getColor(R.color.error_view_title))
            titleVisible = a.getBoolean(R.styleable.ErrorView_ev_titleVisible, true)

            subtitle = a.getString(R.styleable.ErrorView_ev_subtitle)
            subtitleColor = a.getColor(R.styleable.ErrorView_ev_subtitleColor,
                resources.getColor(R.color.error_view_subtitle))
            subtitleVisible = a.getBoolean(R.styleable.ErrorView_ev_subtitleVisible, true)

            retryVisible = a.getBoolean(R.styleable.ErrorView_ev_retryVisible, true)
            retryText = a.getString(R.styleable.ErrorView_ev_retryText)
            retryBackground = a.getResourceId(R.styleable.ErrorView_ev_retryBackground,
                R.drawable.error_view_retry_button_background)
//            retryColor = a.getColor(R.styleable.ErrorView_ev_retryColor,
//                resources.getColor(R.color.error_view_retry))

            if (imageRes != 0) {
                setImage(imageRes)
            }
            if (imageTint != 0) {
                setImageTint(imageTint)
            }
            setImageVisible(imageVisible)
            if (imageSize != 0) {
                setImageSize(imageSize)
            }

            if (title != null) {
                setTitle(title)
            }

            if (subtitle != null) {
                setSubtitle(subtitle)
            }

            if (retryText != null) {
                retryView.text = retryText
            }

            if (!titleVisible) {
                titleView.visibility = View.GONE
            }

            if (!subtitleVisible) {
                subtitleView.visibility = View.GONE
            }

            if (!retryVisible) {
                retryView.visibility = View.GONE
            }

            titleView.setTextColor(titleColor)
            subtitleView.setTextColor(subtitleColor)

//            retryView.setTextColor(retryColor)
//            retryView.setBackgroundResource(retryBackground)
        } finally {
            a.recycle()
        }

        retryView.setOnClickListener {
            retryListener?.invoke()
        }
    }

    /**
     * Indicates whether the title is currently visible.
     */
    var isImageVisible: Boolean
        get() = imageView.visibility == View.VISIBLE
        set(value) = setImageVisible(value).unitify()

    /**
     * Returns the current title string.
     */
    var title: CharSequence
        get() = titleView.text
        set(value) = setTitle(value.toString()).unitify()

    /**
     * Indicates whether the title is currently visible.
     */
    var isTitleVisible: Boolean
        get() = titleView.visibility == View.VISIBLE
        set(value) = setTitleVisible(value).unitify()

    /**
     * Returns the current subtitle.
     */
    var subtitle: CharSequence
        get() = subtitleView.text
        set(value) = setSubtitle(value.toString()).unitify()

    /**
     * Indicates whether the subtitle is currently visible.
     */
    var isSubtitleVisible: Boolean
        get() = subtitleView.visibility == View.VISIBLE
        set(value) = setSubtitleVisible(value).unitify()

    /**
     * Returns the retry button text.
     */
    var retryText: CharSequence
        get() = retryView.text
        set(value) = setRetryText(value.toString()).unitify()

    /**
     * Indicates whether the retry button is visible.
     */
    var isRetryVisible: Boolean
        get() = retryView.visibility == View.VISIBLE
        set(value) = setRetryVisible(value).unitify()

    /**
     * Sets error image to a given drawable resource.
     */
    private fun setImage(res: Int): ErrorView {
        imageView.setImageResource(res)
        return this
    }

    /**
     * Sets the error image to a given [android.graphics.drawable.Drawable].
     */
    private fun setImage(drawable: Drawable): ErrorView {
        imageView.setImageDrawable(drawable)
        return this
    }

    /**
     * Sets the error image to a given [android.graphics.Bitmap].
     */
    private fun setImage(bitmap: Bitmap): ErrorView {
        imageView.setImageBitmap(bitmap)
        return this
    }

    /**
     * Tints the error image with given color.
     */
    private fun setImageTint(color: Int): ErrorView {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        return this
    }

    /**
     * Shows or hides the error image.
     */
    private fun setImageVisible(visible: Boolean): ErrorView {
        imageView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets error image width to given pixel value. The ImageView adjusts its bounds to preserve the
     * aspect ratio of its drawable.
     */
    private fun setImageSize(width: Int): ErrorView {
        imageView.layoutParams.width = width
        return this
    }

    /**
     * Sets the error title to a given [java.lang.String].
     */
    private fun setTitle(text: String?): ErrorView {
        setTitleVisible(text != null)
        titleView.text = text
        return this
    }

    /**
     * Sets the error title to a given string resource.
     */
    private fun setTitle(res: Int): ErrorView {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setTitleVisible(true)
        titleView.setText(res)
        return this
    }

    /**
     * Sets the error title text to a given color.
     */
    private fun setTitleColor(color: Int): ErrorView {
        titleView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides the error title
     */
    private fun setTitleVisible(visible: Boolean): ErrorView {
        titleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the error subtitle to a given [java.lang.String].
     */
    private fun setSubtitle(subtitle: String?): ErrorView {
        setSubtitleVisible(subtitle != null)
        subtitleView.text = subtitle
        return this
    }

    /**
     * Sets the error subtitle to a given string resource.
     */
    private fun setSubtitle(res: Int): ErrorView {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setSubtitleVisible(true)
        subtitleView.setText(res)
        return this
    }

    /**
     * Sets the error subtitle text to a given color
     */
    private fun setSubtitleColor(color: Int): ErrorView {
        subtitleView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides error subtitle.
     */
    private fun setSubtitleVisible(visible: Boolean): ErrorView {
        subtitleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the retry button text to a given string.
     */
    private fun setRetryText(text: String): ErrorView {
        retryView.text = text
        return this
    }

    /**
     * Sets the retry button text to a given string resource.
     */
    private fun setRetryText(res: Int): ErrorView {
        retryView.setText(res)
        return this
    }

    /**
     * Sets the retry button's text color to a given color.
     */
    private fun setRetryColor(color: Int): ErrorView {
        retryView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides the retry button.
     */
    private fun setRetryVisible(visible: Boolean): ErrorView {
        retryView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Attaches a listener to the view which will be notified when retry events occur.
     */
    private fun setRetryListener(listener: () -> Unit): ErrorView {
        retryListener = listener
        return this
    }

    private fun Any.unitify(): Unit {}

    fun showOnlyTitle() {
        if (!isVisible)
            show()
        isRetryVisible = false
        isSubtitleVisible = false
        isImageVisible = false
    }
}