//
//  {{classname}}
//
//  Created by {{creater}} on {{time}}
//  Copyright (c) {{creater}} All rights reserved.


/**
   {{mark}}
*/

package {{package}};

import android.view.View;

import com.mdx.framework.activity.MFragment;

abstract class BaseFrg : MFragment(), View.OnClickListener, HttpResultSubscriberListener  {


 final override fun initV(view: View) {
        initView()
        loaddata()
    }

 abstract fun initView()
 abstract fun loaddata()
 override fun onClick(v: View) {
 }
 override fun onSuccess(data: String?, method: String) {
 }
 override fun onError(code: String?, msg: String?, data: String?, method: String) {
 }
}
