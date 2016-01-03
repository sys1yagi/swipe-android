package com.sys1yagi.swipe.core.entity.swipe

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Element properties
 * id (String): the element identifier to identify an element in the associated Scene
 * element (String): the name of the named Element to inherit properties from
 * x (Float or Percent): x-position of the left-top corner of element, default is 0
 * y (Float or Percent): y-position of the left-top corner of the element, default is 0
 * pos ([Float/Percent, Float/Percent]): alternative way to specificy the position by the location of the anchor point
 * anchor ([Float/Percent, Float/Percent]): anchor point, default is ["50%", "50%"]
 * w (Float, Percent or "fill"): width of the element, default is "100%".
 * h (Float, Percent or "fill"): height of the element, default is "100%"
 * bc (Color): background color, default is clear, animatable
 * clip (Boolean): Specifies clipping behavior, default is false
 * borderWidth (Float): Width of the border, default is 0, animatable
 * borderColor (Color): Color of the border, animatable
 * cornerRadius (Float): Size of the corner radius, animatable
 * opacity (Float): Opacity of the element, between 0 to 1, animatable
 * rotate (Float or Float[3]): Rotation in degree around the anchor point, clockwise, defalut is 0, animatable.
 * scale (Float or [Float, Float]): Scaling factor around the anchor point, default is [1, 1], animatable
 * translate ([Float, Float]): Translation, default is [0, 0], animatable
 * text (String): Text to display
 * textAlign (String): Text alignment, center (default), left or right
 * fontSize (Float or Percent): Font size
 * textColor (Color): Color of the text, animatable
 * img (URL): Image to display, animatable
 * mask (URL): Image mask (PNG with the alpha channel)
 * sprite (URL): Sprite to display
 * slice ([Int, Int]): Dimension of the sprite
 * slot ([Int, Int]): Slot to diplay, animatable
 * path (Path): Path to display (SVG style), animatable
 * lineWidth (Float): Width of stroke, default is 0
 * strokeColor (Color): Color of the stroke, default is black, animatable
 * fillColor (Color): Fill color, default is clear, animatable
 * strokeStart (Float): Starting offset, default is 0, animatable
 * strokeEnd (Float): Ending offset, default is 1, animatable
 * video or radio (URL): Video/Radio to play
 * videoStart (Float): Starting point of the video in seconds, default is 0
 * videoDuration (Float): Ending point of the video in seconds
 * stream (Bool): Specifies if the resource specified with the video tag is stream or not, default is false
 * to (Transition Animation): Specifies the Transitional Animation
 * loop (Loop Animation): Specifies the Loop Animation
 * tiling (Bool): Specifies the tiling (to be used with shift loop animation)
 * action (String): Specifies the Action
 * repeat (Bool): Repeat rule for the element. The default is false.
 */
class SwipeElement : Cloneable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("element")
    var element: String = ""

    @SerializedName("x")
    var x: String = "0"

    @SerializedName("y")
    var y: String = "0"

    @SerializedName("pos")
    var pos: Array<String>? = null

    @SerializedName("anchor")
    var anchor: Array<String>? = null

    @SerializedName("w")
    var w: String = "0"

    @SerializedName("h")
    var h: String = "0"

    @SerializedName("bc")
    var bc: String? = null

    @SerializedName("clip")
    var isClip: Boolean = false

    @SerializedName("borderWidth")
    var borderWidth: Float = 0.toFloat()

    @SerializedName("borderColor")
    var borderColor: String? = null

    @SerializedName("cornerRadius")
    var cornerRadius: Float = 0.toFloat()

    @SerializedName("opacity")
    var opacity: Float = 0.toFloat()
        internal set

    @SerializedName("rotate")
    var rotate: String = "0"

    @SerializedName("scale")
    var scale: String = "[1, 1]"

    @SerializedName("translate")
    var translate: FloatArray = FloatArray(2, { 1f })

    @SerializedName("text")
    var text: String = ""

    @SerializedName("markdown")
    var markdown: List<String> = listOf()

    @SerializedName("textAlign")
    var textAlign: String = "center"

    @SerializedName("fontSize")
    var fontSize: String = ""

    @SerializedName("textColor")
    var textColor: String = "#000000"

    @SerializedName("img")
    var img: String = ""

    @SerializedName("mask")
    var mask: String = ""

    @SerializedName("sprite")
    var sprite: String = ""

    @SerializedName("slice")
    var slice: IntArray? = null

    @SerializedName("slot")
    var slot: IntArray? = null

    // TODO
    @SerializedName("path")
    var path: String? = null

    @SerializedName("fillColor")
    var fillColor: String? = null

    @SerializedName("video")
    var video: String? = null

    @SerializedName("radio")
    var radio: String? = null

    @SerializedName("stream")
    var isStream: Boolean = false
        internal set

    // TODO
    @SerializedName("to")
    var to: JsonObject? = null

    @SerializedName("loop")
    var loop: JsonObject? = null

    @SerializedName("tiling")
    var isTiling: Boolean = false

    @SerializedName("action")
    var action: String? = null

    @SerializedName("repeat")
    var isRepeat: Boolean = false

    @SerializedName("shadow")
    var shadow: Shadow? = null

    @SerializedName("elements")
    var elements: List<SwipeElement>? = null

    override public fun clone(): SwipeElement {
        val clone = SwipeElement()

        clone.id = id
        clone.element = element
        clone.x = x
        clone.y = y
        clone.pos = pos?.copyOf()
        clone.anchor = anchor?.copyOf()
        clone.w = w
        clone.h = h
        clone.bc = bc
        clone.isClip = isClip
        clone.borderWidth = borderWidth
        clone.borderColor = borderColor
        clone.cornerRadius = cornerRadius
        clone.opacity = opacity
        clone.rotate = rotate
        clone.scale = scale
        clone.translate = translate.copyOf()
        clone.text = text
        clone.markdown = ArrayList<String>(markdown)
        clone.textAlign = textAlign
        clone.fontSize = fontSize
        clone.textColor = textColor
        clone.img = img
        clone.mask = mask
        clone.sprite = sprite
        clone.slice = slice?.copyOf()
        clone.slot = slot?.copyOf()
        clone.path = path
        clone.fillColor = fillColor
        clone.video = video
        clone.radio = radio
        clone.isStream = isStream

        //TODO
        //        clone.to = JsonParser().parse(to?.toString()).asJsonObject
        //        clone.loop = JsonParser().parse(loop?.toString()).asJsonObject
        clone.isTiling = isTiling
        clone.action = action
        clone.isRepeat = isRepeat
        clone.shadow = shadow?.clone()

        clone.elements = elements?.let {
            val cloneElements = ArrayList<SwipeElement>()
            it.forEach {
                cloneElements.add(it.clone() as SwipeElement)
            }
            cloneElements
        } ?: null
        return clone
    }

    fun inheritElement(parent: SwipeElement): SwipeElement {
        val inherited = parent.clone()

        //
        if (!"0".equals(w)) {
            inherited.w = w
        }
        if (!"0".equals(h)) {
            inherited.h = h
        }
        if (!"0".equals(x)) {
            inherited.x = x
        }
        if (!"0".equals(y)) {
            inherited.y = y
        }
        shadow?.let {
            inherited.shadow = it.clone()
        }

        inherited.markdown = ArrayList<String>(markdown)

        inherited.cornerRadius = cornerRadius

        val elements = elements?.let { ArrayList<SwipeElement>(elements) } ?: ArrayList<SwipeElement>()
        parent.elements?.forEach {
            var parentElement: SwipeElement? = it

            for (i in 0..elements.size - 1) {
                if (elements[i].id?.let { it.equals(parentElement?.id) } ?: false) {
                    val e = elements[i].inheritElement(parentElement!!)
                    elements[i] = e
                    parentElement = null
                    break
                }
            }
            parentElement?.let {
                elements.add(it)
            }
        }
        inherited.elements = elements

        return inherited
    }
}
