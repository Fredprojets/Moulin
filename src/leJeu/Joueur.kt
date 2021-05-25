package leJeu

import kotlin.random.Random

class Joueur(val nom:String,val adversaire:String, val AI:Boolean){
    var nbJetons = 0

    fun placer():Boolean{
        return if (AI){ // pour le AI faire des coups au hasard
            var valide = false
            while (!valide) {
                val anneau = listOf("b", "m", "c")
                val choix = anneau[Random.nextInt(3)] + (Random.nextInt(8) + 1).toString()

                valide =Plateau.placerJeton(this, choix)
                if (valide) nbJetons++
            }
            valide

        } else{ //coups du joeur humain
            var valide = false
            while (!valide) {
                valide =Plateau.placerJeton(this,readLine()!!)
                if (!valide){
                    println("Coup non valide, entrez un coup valide")
                } else nbJetons++
            }
            valide
        }
    }

    fun bouger() {
        do {
            var choix = readLine()!!
            var jeton = choix
        } while (!Plateau.choixJeton(choix,this))
    }
}