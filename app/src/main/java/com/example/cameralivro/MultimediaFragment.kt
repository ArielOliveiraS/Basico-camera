package com.example.cameralivro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class MultimediaFragment : Fragment(), CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected fun hasPermission(): Boolean {
        val permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissions.all {
            ActivityCompat.checkSelfPermission(requireActivity(), it) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }

    //veridica se a aplicacao tem permissao de escrever no cartao de memoria
    protected fun requestPermissions(requestCode: Int = REQUEST_CODE_PERMISSION) {
        requestPermissions(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode
        ) //solicita permissao no requestPermissions
    }

    companion object {
        const val REQUEST_CODE_PERMISSION = 1
    }
}