//
//  {{classname}}
//
//  Created by {{creater}} on {{time}}
//  Copyright (c) {{creater}} All rights reserved.


/**
   {{mark}}
*/

package {{package}};

import {{R}};

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

{{imports}}

class  {{classname}}(context: Context?) : BaseItem(context) {
    init {
        val flater = LayoutInflater.from(context)
        flater.inflate({{layout}}, this)
    }

    fun set(item: Any?) {
        item?.run{
        }
    }

}