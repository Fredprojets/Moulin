package leJeu

import kotlin.random.Random.Default.nextInt

fun main() {

    val joueur1 = Joueur("X", AI = false)
    val joueur2 = Joueur("AI", AI = true)

    // Phase 1
    Plateau.display()
    repeat(9){
        print("Placez un jeton: ")
        if(!joueur1.jouer()){
            if(!joueur1.jouer()){
                println("...Le hasard va choisir")
            }
        }
        joueur2.jouer()
        Plateau.display()
    }
}

class Jeton(var proprietaire:String = "o", var proteger:Boolean = false){
    override fun toString(): String {
        return proprietaire.first().toString()
    }
}

class Joueur(val nom:String, val AI:Boolean){
    var nbJetons = 9

    fun jouer():Boolean{
        return if (AI){
            val anneau = listOf("b","m","c")
            val choix = anneau[nextInt(3)] + (nextInt(8)+1).toString()
            nbJetons--
            Plateau.placerJeton(nom,choix)
        } else{
            val valide = Plateau.placerJeton(nom,readLine()!!)
            nbJetons--
            if (!valide){
                println("choix non-valide")
                nbJetons++
            }
            valide
        }
    }
}

object Plateau {
    var b: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())
    var m: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())
    var c: MutableList<Jeton> = mutableListOf(Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton(), Jeton())

    fun placerJeton(nom: String,endroit:String):Boolean{
        val position = endroit.last().toInt() - 49
        when (endroit) {
            in "b1".."b8"-> if(b[position].toString() == "o") b[position].proprietaire = nom else return false
            in "m1".."m8" -> if(m[position].toString() == "o") m[position].proprietaire = nom else return false
            in "c1".."c8" -> if(c[position].toString() == "o") c[position].proprietaire = nom else return false
            else -> return false
        }

        return true
    }



    fun moulin(){

    }

    fun display() {
        val tableau:List<String> = listOf(
                " "+b[0]+"-----"+b[1]+"-----"+b[2]+" ",
                " |     |     | ",
                " | "+m[0]+"---"+m[1]+"---"+m[2]+" | ",
                " | | "+c[0]+"-"+c[1]+"-"+c[2]+" | | ",
                " | | |   | | | ",
                " "+b[3]+"-"+m[3]+"-"+c[3]+"   "+c[4]+"-"+m[4]+"-"+b[4]+" ",
                " | | |   | | | ",
                " | | "+c[5]+"-"+c[6]+"-"+c[7]+" | | ",
                " | "+m[5]+"---"+m[6]+"---"+m[7]+" | ",
                " |     |     | ",
                " "+b[5]+"-----"+b[6]+"-----"+b[7]+" "
                )
        for (element in tableau){
            println(element)
        }
    }
}