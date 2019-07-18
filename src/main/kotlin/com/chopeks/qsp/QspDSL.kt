@file:Suppress("UNUSED_PARAMETER")

/**
 * There's a list of possible commands from original russian page:
 * ACT ADDLIB ADDOBJ ADDQST AND ARRCOMP ARRPOS ARRSIZE $BACKIMAGE BCOLOR
 * CLA CLEAR *CLEAR CLOSE CLR *CLR CLS CMDCLEAR CMDCLR COPYARR $COUNTER
 * COUNTOBJ $CURACTS CURLOC DEBUG DELACT DELLIB DELOBJ DESC DISABLESCROLL
 * DISABLESUBEX DYNAMIC DYNEVAL ELSE ELSEIF EXIT FCOLOR $FNAME FREELIB
 * FSIZE FUNC GETOBJ GOSUB GOTO GS GT IF IIF INCLIB INPUT INSTR ISNUM ISPLAY
 * JUMP KILLALL KILLOBJ KILLQST KILLVAR LCASE LCOLOR LEN LET LOC $MAINTXT
 * MAX MENU MID MIN MOD MSECSCOUNT MSG NL *NL NO NOSAVE OBJ $ONACTSEL
 * $ONGLOAD $ONGSAVE $ONNEWLOC $ONOBJADD $ONOBJDEL $ONOBJSEL OPENGAME
 * OPENQST OR P *P PL *PL PLAY QSPVER RAND REFINT REPLACE RGB RND SAVEGAME
 * SELACT SELOBJ SET SETTIMER SHOWACTS SHOWINPUT SHOWOBJS SHOWSTAT $STATTXT
 * STR STRCOMP STRFIND STRPOS TRIM UCASE UNSEL UNSELECT USEHTML $USERCOM
 * USER_TEXT USRTXT VAL VIEW WAIT XGOTO XGT
 */
package com.chopeks.qsp

/** Ignore this one, it's not API, just helper */
private fun noop() {}

//ACT
/** ACT, defines action with given text */
fun act(name: String, image: String = "", block: (Unit) -> Unit) = noop()

//ADDLIB
/** Adds locations from file to the game */
@Deprecated("old library", replaceWith = ReplaceWith("incLib()", "com.chopeks.qsp"))
fun addLib(path: String) = noop()

//ADDOBJ
/** Adds object to inventory */
fun addObj(name: String) = noop()

/** Adds object to inventory */
fun addObj(name: String, image: String) = noop()

/** Adds object to inventory */
fun addObj(name: String, image: String, index: Int) = noop()

/** Adds object to inventory */
fun addObj(name: String, index: Int) = noop()

//ADDQST
@Deprecated("old library", replaceWith = ReplaceWith("incLib()", "com.chopeks.qsp"))
fun addQst(path: String) = noop()
//AND - no api, that's an && operator
//ARRCOMP
/**  returns the index of the match of the array */
fun arrComp(start: Int, arrayName: String, pattern: String): Int = 0

/**  returns the index of the match of the array */
fun arrComp(arrayName: String, regex: String): Int = 0
//ARRPOS
/**  returns the index of the element of the array */
fun arrPos(start: Int, arrayName: String, pattern: String): Int = 0

/**  returns the index of the element of the array */
fun arrPos(arrayName: String, regex: String): Int = 0
//ARRSIZE
/** size of an array*/
fun arrsize(arrayName: String): Int = 0
//$BACKIMAGE that's a variable, check QspTypes
//BCOLOR that's a variable, check QspTypes
//CLA
/** Clears list of actions */
fun cla() = noop()
//CLEAR - meh, see aClr()
//*CLEAR - meh, see clr()
//CLOSE
/** stops playing given sound */
fun close(path: String) = noop()

/** stops playing all sounds*/
fun closeAll() = noop()
//CLR
/** clears the additional description window*/
fun aClr() = noop()
//*CLR
/** clears the main description window*/
fun clr() = noop()
//CLS
/** clears all, equivalent of calling (CLEAR & * CLEAR & CLA & CMDCLEAR) or kotlin way (clr();aClr();cla(); cmdClear())*/
fun cls() = noop()
//CMDCLEAR
//CMDCLR
/** clears user input*/
fun cmdClr() = noop()
//COPYARR
/** copies array */
fun copyArr(dst: String, src: String, start: Int = 0, count: Int = 0) = noop()
//$COUNTER that's a variable, check QspTypes
//COUNTOBJ
/** returns the number of items in the backpack. */
fun countObj(): Int = 0
//$CURACTS that's a variable, check QspTypes
//CURLOC that's a variable, check QspTypes
//DEBUG that's a variable, check QspTypes
//DELACT
/** removes action */
fun delAct(action: String) = noop()
//DELLIB
/** removes all locations added using the operator "addLib" . */
@Deprecated("old library", replaceWith = ReplaceWith("freeLib(...)", "com.chopeks.qsp"))
fun delLib(path: String) = noop()
//DELOBJ
/** remove the item from the backpack, if there is one */
fun delObj(path: String) = noop()
//DESC
/** returns the text of the base location description */
fun desc(location: String): String = ""
//DISABLESCROLL that's a variable, check QspTypes
//DISABLESUBEX that's a variable, check QspTypes
//DYNAMIC
/** executes the code specified as a string of text, but it has to be QSP code, not kotlin! */
fun dynamic(code: String, vararg parameters: Any) = noop()
//DYNEVAL
/** same as dynamic, but sets $result variable */
fun dyneval(code: String, vararg parameters: Any) = noop()
//ELSE - that's compiller stuff, ignore
//ELSEIF - that's compiller stuff, ignore
//EXIT
/** stops current subroutine, so with gs, it will stop and return, with gt it will ... ? exit game? */
fun exit() = noop()
//FCOLOR that's a variable, check QspTypes
//$FNAME that's a variable, check QspTypes
//FREELIB
/** removes locations added by addLib() */
fun freeLib(string: String) = noop()
//FSIZE  that's a variable, check QspTypes
//FUNC
/** process subroutine same way as gs(...), but also sets $result variable */
fun func(location: String, vararg parameters: Any) = noop()
//GETOBJ
/** return name of object from backpack */
fun getObj(int: Any): String = ""
//GOSUB
//GS
/** Go to sublocation, then go back */
fun gs(location: String, vararg parameters: Any) = noop()
//GOTO
//GT
/** Go to location*/
fun gt(string: String, vararg target: Any) = noop()
//IF - that's compiller stuff, ignore
//IIF
/** inline if, returns value*/
fun iif(predicate: Boolean, ifTrue: Any, ifNot: Any): Any = 0
//INCLIB
/** Adds locations from file to the game */
fun incLib(path: String) = noop()
//INPUT
/** displays pop up window with message, and returns input from user into `$userCom` variable, but also returned by function */
fun input(message: String): String = ""
//INSTR
/** checks if search is in string */
fun inStr(start: Int, string: String, search: String): Int = 0

/** checks if search is in string */
fun inStr(string: String, search: String): Int = 0
//ISNUM
/** checks if string is number */
fun isNum(string: Any): Int = 0
//ISPLAY
/** returns 1 if path sound plays now */
fun isPlay(path: String): Int = 0
//JUMP
/** jumps to label */
fun jump(label: String) = noop()
//KILLALL
/** removes all variables and backpack */
fun killall() = noop()
//KILLOBJ - // TODO this is not documented at all...
//KILLQST
/** removes all locations added using the operator "addQst" . */
@Deprecated("old library", replaceWith = ReplaceWith("freeLib(...)", "com.chopeks.qsp"))
fun killQst(path: String) = noop()
//KILLVAR
/** removes variable, or with index, specific element from array */
fun killVar(variable: String, index: Any = 0) = noop()
//LCASE
/** to lower case*/
fun lcase(string: Any): String = ""
//LCOLOR that's a variable, check QspTypes
//LEN
/** string lenght */
fun len(any: Any): Int = 0
//LET - keyword, ignore
//LOC
/** checks if location */
fun loc(location: String): Int = 0
//$MAINTXT that's a variable, check QspTypes
//MAX
/** max */
fun max(any: Any, any2: Any, vararg anys: Any): Int = 0
//MENU
/** shows pop up with options to be chosen, usage is somewhat problematic, refer to original docs fot that,
 *  But to sum it up:
 *   $stone [0] = 'Take a Stone: takestone'
 *   $stone [1] = 'Throw Stone: throwstone'
 *  menu("stone") will show 2 options, click on any will gs to location after ':' with args[0] == location in the menu
 */
fun menu(arrayName: String) = noop()
//MID
/** cuts string from start with length */
fun mid(string: Any, start: Int, length: Int): String = ""
//MIN
/** min */
fun min(any: Any, any2: Any, vararg anys: Any): Int = 0
//MOD operator, ignore it
//MSECSCOUNT that's a variable, check QspTypes
//MSG
/** shows pop up with message */
fun msg(string: String) = noop()
//NL
/** Additional window: Print new line, then text */
fun anl(string: Any = 0) = noop()
//*NL
/** Main window: Print new line, then text */
fun nl(string: Any = 0) = noop()
//NO bitwise negation, use ! from kotlin
//NOSAVE that's a variable, check QspTypes
//OBJ //TODO ????
/** checks if item is in backpack */
fun obj(any: Any): Int = 0
//$ONACTSEL  that's a variable, check QspTypes
//$ONGLOAD  that's a variable, check QspTypes
//$ONGSAVE that's a variable, check QspTypes
//$ONNEWLOC that's a variable, check QspTypes
//$ONOBJADD that's a variable, check QspTypes
//$ONOBJDEL that's a variable, check QspTypes
//$ONOBJSEL that's a variable, check QspTypes
//OPENGAME
/** loads state of the game from save file, if no param loading screen is called */
fun openGame(path: String = "") = noop()
//OPENQST
/** opens new qsp file */
fun openQst(path: String) = noop()
//OR -- operator, ignore
//P
/** Additional window: Print text*/
fun ap(string: Any) = noop()
//*P
/** Main window: Print text*/
fun p(string: Any) = noop()
//PL
/** Additional window: Print text, then new line, works same as just putting string without any command */
fun apl(string: Any) = noop()
//*PL
/** Main window: Print text, then new line, works same as just putting string without any command */
fun pl(string: Any = 0) = noop()
//PLAY
/** plays sound */
fun play(string: String, volume: Int) = noop()
//QSPVER that's a variable, check QspTypes
//RAND
/** random int in range 'from' to 'to' */
fun rand(from: Int, to: Int) = 0

/** random int in range '1' to 'to' */
fun rand(to: Int) = 0
//REFINT
/** forces interface update */
fun refint() = noop()
//REPLACE
/** replaces strings*/
fun replace(string: Any, search: String, replacement: Any) = noop()
//RGB
/** color to int */
fun rgb(r: Int, g: Int, n: Int) = 0
//RND
/** returns random from 1 to 1000 */
fun rnd(): Int = 0
//SAVEGAME
/** saves game, if no param provided - selection window will pop */
fun saveGame(saveFile: String = "") = noop()
//SELACT
/** returns selected action */
fun selAct(): String = ""
//SELOBJ that's a variable, check QspTypes
/** returns selected object from backpack */
fun selObj(): String = ""
//SET - keyword, ignore...
//SETTIMER
/** sets game timer, value is in `$counter` variable */
fun setTimer(millis: Int) = noop()
//SHOWACTS
/** if content is != 0 shows actions window*/
fun showActs(any: Any) = noop()
//SHOWINPUT
/** if content is != 0 shows input window*/
fun showInput(any: Any) = noop()
//SHOWOBJS
/** if content is != 0 shows list of backpack content*/
fun showObjs(any: Any) = noop()
//SHOWSTAT
/** if content is != 0 shows additional description window*/
fun showStat(any: Any) = noop()
//$STATTXT that's a variable, check QspTypes
//STR
/** int to string */
fun str(int: Any): String = ""
//STRCOMP
/** compares 2 strings */
fun strComp(string: Any, string2: Any): Int = 0
//STRFIND
/** runs regex on string and returns match defined in index */
fun strFind(string: Any, regex: String, index: Int): String = ""
//STRPOS
/** retuns position of str in string */
fun strpos(string: String, str: String, index: Int = 0): Int = 0
//TRIM
/** trims whitespaces */
fun trim(any: Any): String = ""
//UCASE
/** to uppercase */
fun ucase(string: Any): String = ""
//UNSEL
//UNSELECT
/** unselect backpack item */
fun unSelect() = noop()
//USEHTML that's a variable, check QspTypes
//$USERCOM that's a variable, check QspTypes
//USER_TEXT same as  USRTXT
//USRTXT that's a variable, check QspTypes
//VAL operator, ignore
/** converts string to int */
fun value(string: Any): Int = 0
//VIEW
/** opens separate window with given image */
fun view(image: String) = noop()

/** closes separate window with image*/
fun view() = noop()
//WAIT
/** Wait for millis */
fun wait(millis: Int) = noop()
//XGOTO
//XGT
/** same as gt(..) but previous location is not cleared */
fun xgt(string: String, vararg target: Any) = noop()


// There are some non api calls, because kotlin has it own limitations:

/** defines label used with jump(...) function */
fun label(string: String): String = ""


/** wrapper on curly brackets in qsp, kotlin has no way to do so without identifier */
fun newObj(block: (Unit) -> Unit) = ""
