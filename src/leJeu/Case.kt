package leJeu

class Case(val case:String, var jeton:Char = 'o'){
    var protect:Boolean = false
    var adj:Array<Case> = arrayOf()
    var moulinable:Array<Array<Case>> = arrayOf()

    override fun toString():String {
        return jeton.toString()
    }
}