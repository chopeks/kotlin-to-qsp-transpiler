package com.chopeks.qsp

/*
* Basically QSP has only one type, that can act as:
* String, Int, arrays of those, but index can be Int or String...
* So.. For kotlin to emulate it, there's Any + array operators for it
*
* But, there's some other variable like type, which is name = {} used with conjunction with dynamic(...), so there's alias for it
*/

typealias QspCodeTemplate = (Unit) -> Unit
typealias QspType = Any

operator fun QspType.get(type: QspType): QspType = 0
operator fun QspType.set(type: QspType, index: QspType) = 0

// there are also some predefined variables:
/** script arguments */
val `$args`: QspType = 0
/** script arguments */
val args: QspType = 0
/** path to background image */
var `$backImage`: QspType = 0
/** background color */
var bColor: Int = 0
/** location counter is called at equal intervals */
var `$counter`: QspType = 0
/** array of current actions */
var `$curActs`: QspType = 0
/** current location */
var `$curLoc`: QspType = 0
/**
 * if the value of the variable is not equal to 0, then the check of the game identifier is disabled when the state is loaded.
 * Set to 1 if you want to have saves compatible after game file change
 * NOTE: I see existing games set it to 1
 */
var debug: Int = 0
/** disables scroll, may not work with all players */
var disableScroll: Int = 0
/** disables subexpression if set, that means these <<...>> in qsp or \${...} in kotlin*/
var disableSubEx: Int = 0
/** font color */
var fColor: Int = 0
/** font size */
var fSize: Int = 0
/** font name */
var `$fName`: QspType = 0
/** link color */
var lColor: Int = 0
/** stores current text from main window */
val `$mainTxt`: String = ""
/** stores current text from main window */
val msecsCount: Int = 0
/** prevents saving game (1) or unlocks it (0) */
var noSave: Int = 0
/** backpack (?)*/
val objects: QspType = 0
/** on action selected */
var `$onActSel`: QspType = ""
/** on game load */
var `$onGLoad`: QspType = ""
/** on game save*/
var `$onGSave`: QspType = ""
/** on enter to new location via gt, called *before* entering location */
var `$onNewLoc`: QspType = ""
/** on item in backpack add, $args[0] - item name, $args[1] - item image*/
var `$onObjAdd`: QspType = ""
/** on item in backpack delete, $args[0] - item name */
var `$onObjDel`: QspType = ""
/** stores current content of additional window */
val `$statTxt`: QspType = ""
/** enable html in all windows */
var useHtml: Int = 0
/** reponse from input(...) call is here */
var `$userCom`: QspType = ""
/** text from input(...) call is here */
var `$userTxt`: QspType = ""
/** */
val qspVer: String = ""
/** */
