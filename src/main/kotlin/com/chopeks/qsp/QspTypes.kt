package com.chopeks.qsp

/*
* Basically QSP has only one type, that can act as:
* String, Int, arrays of those, but index can be Int or String...
* So.. For kotlin to emulate it there's Any + array operators for it
*/
operator fun Any.get(int: Int): Any = 0

operator fun Any.get(int: String): Any = 0
operator fun Any.set(int: Int, i: Int) = 0
operator fun Any.set(int: String, i: Int) = 0
operator fun Any.set(int: Int, i: String) = 0
operator fun Any.set(int: String, i: String) = 0


// there are also some predefined variables:
/** script arguments */
val `$args`: Any = 0
/** script arguments */
val args: Any = 0
/** path to background image */
var `$backImage`: Any = 0
/** background color */
var bColor: Int = 0
/** location counter is called at equal intervals */
var `$counter`: Any = 0
/** array of current actions */
var `$curActs`: Any = 0
/** current location */
var `$curLoc`: Any = 0
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
var `$fName`: Any = 0
/** link color */
var lColor: Int = 0
/** stores current text from main window */
val `$mainTxt`: String = ""
/** stores current text from main window */
val msecsCount: Int = 0
/** prevents saving game (1) or unlocks it (0) */
var noSave: Int = 0
/** backpack (?)*/
val objects: Any = 0
/** on action selected */
var `$onActSel`: Any = ""
/** on game load */
var `$onGLoad`: Any = ""
/** on game save*/
var `$onGSave`: Any = ""
/** on enter to new location via gt, called *before* entering location */
var `$onNewLoc`: Any = ""
/** on item in backpack add, $args[0] - item name, $args[1] - item image*/
var `$onObjAdd`: Any = ""
/** on item in backpack delete, $args[0] - item name */
var `$onObjDel`: Any = ""
/** stores current content of additional window */
val `$statTxt`: Any = ""
/** enable html in all windows */
var useHtml: Int = 0
/** reponse from input(...) call is here */
var `$userCom`: Any = ""
/** text from input(...) call is here */
var `$userTxt`: Any = ""
/** */
val qspVer: String = ""
/** */
