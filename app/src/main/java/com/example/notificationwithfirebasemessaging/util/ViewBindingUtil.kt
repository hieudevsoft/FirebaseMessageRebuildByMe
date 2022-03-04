package com.example.notificationwithfirebasemessaging.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

internal fun Class<*>.checkInflateMethod():Boolean{
    return try {
        getMethod("inflate",LayoutInflater::class.java)
        true
    }catch (e:Exception){
        false
    }
}

internal fun Any.findGenericClass():Class<*>{
    var classResult:Class<*>? = null
    var originalClass = this.javaClass
    while (classResult==null||!classResult.checkInflateMethod()){
        classResult = (originalClass.genericSuperclass as ParameterizedType).actualTypeArguments.firstOrNull{
            if(it is Class<*>){
                it.checkInflateMethod()
            } else false
        } as Class<*>?
        originalClass = originalClass.superclass
    }
    return classResult
}

internal fun <V:ViewBinding> Class<*>.getBinding(layoutInflater: LayoutInflater):V{
    return try {
        getMethod("inflate",LayoutInflater::class.java)
            .invoke(null,layoutInflater) as V
    }catch (e:Exception){
        throw IllegalAccessException("View has been changed...")
    }
}

internal fun <V : ViewBinding> Class<*>.getBinding(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
): V {
    return try {
        getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, false) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun <V:ViewBinding> BaseActivity<V>.getBinding():V{
    return findGenericClass().getBinding(layoutInflater)
}
