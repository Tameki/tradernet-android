package com.tameki.tradernet.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

abstract class GlideBitmapTarget : CustomTarget<Bitmap>() {
    override fun onLoadCleared(placeholder: Drawable?) = Unit

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) = Unit
}