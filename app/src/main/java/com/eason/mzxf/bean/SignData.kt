package com.eason.mzxf.bean

/**
 * Created by Eason on 2018/1/17.
 */
data class SignData(val createTime: String,
                    val id: Int,
                    val isSign: Boolean,
                    val num: Int,
                    val other: String,
                    val regularList: MutableList<String>,
                    val userId: Int)



//"createTime": "2018-01-17 14:55:49",
//"id": 7991,
//"isSign": false,
//"num": 3,
//"other": null,
//"regularList": null,
//"userId": 100998