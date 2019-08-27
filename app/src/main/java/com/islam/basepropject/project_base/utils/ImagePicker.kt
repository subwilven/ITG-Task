package com.islam.basepropject.project_base.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.handleActivityResult
import java.io.File


object ImagePicker {


    var onPicked: (imageFile: File?) -> Unit = {}
     fun pickImage(fragment: Fragment, onImagePicked: (imageFile: File?) -> Unit) {
        onPicked = onImagePicked
        fragment.childFragmentManager.beginTransaction()
                .add(ImagePickerFragment(), "ImagePickerFragment")
                .commitNow()

    }

    private class ImagePickerFragment : Fragment() {


        override fun onAttach(activity: Activity) {
            super.onAttach(activity)
            EasyImage.openChooserWithGallery(this, "asdfasdf", EasyImage.REQ_PICK_PICTURE_FROM_GALLERY)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
                override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
//                    val myBitmap = BitmapFactory.decodeFile(imageFile?.getAbsolutePath())
//                    Bitmap.createScaledBitmap(myBitmap, 200, 200, false)
                    onPicked.invoke(imageFile)
                }

                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                    super.onImagePickerError(e, source, type)
                    ActivityManager.showToast("Cannot Load the Image", Toast.LENGTH_SHORT)
                }

                override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                    if (source == EasyImage.ImageSource.CAMERA) {
                        val photoFile = EasyImage.lastlyTakenButCanceledPhoto(context)
                        photoFile?.delete()
                    }
                }
            })
        }
    }
}