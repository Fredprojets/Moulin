package leJeu

import kotlin.random.Random.Default.nextInt

fun main() {

    val joueur1 = Joueur("X", AI = false)
    val joueur2 = Joueur("AI", AI = true)

    // Phase 1
    Plateau.display()
    repeat(9){
        print("Placez un jeton: ")
        joueur1.jouer()
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
            var valide = false
            while (!valide) {
                val anneau = listOf("b", "m", "c")
                val choix = anneau[nextInt(3)] + (nextInt(8) + 1).toString()

                valide =Plateau.placerJeton(nom, choix)
                if (valide) nbJetons--
            }
            valide

        } else{ //coups du joeur humain
            var valide = false
            while (!valide) {
                valide =Plateau.placerJeton(nom,readLine()!!)
                if (!valide){
                    println("Coup non valide, entrez un coup valide")
                } else nbJetons--
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
            in "b1".."b8"-> if(b[position].toString() == "o") {
                b[position].proprietaire = nom
                checkMoulin(endroit,nom)
            } else return false
            in "m1".."m8" -> if(m[position].toString() == "o") {
                m[position].proprietaire = nom
                checkMoulin(endroit,nom)
            } else return false
            in "c1".."c8" -> if(c[position].toString() == "o") {
                c[position].proprietaire = nom
                checkMoulin(endroit,nom)
            } else return false
            else -> return false
        }

        return true
    }

    fun checkMoulin(position:String, joueur: String){
        when(position.first()){
            'b' -> when(position.last().toInt()-49){
                1 -> if ((b[1].proprietaire == joueur && b[2].proprietaire == joueur) ||
                        (b[3].proprietaire == joueur && b[5].proprietaire == joueur)) { moulin() }

                2 -> if ((b[0].proprietaire == joueur && b[2].proprietaire == joueur) ||
                        (m[1].proprietaire == joueur && c[2].proprietaire == joueur)) { moulin() }

                3 -> if ((b[0].proprietaire == joueur && b[1].proprietaire == joueur) ||
                        (b[4].proprietaire == joueur && b[7].proprietaire == joueur)) { moulin() }

                4 -> if ((b[0].proprietaire == joueur && b[5].proprietaire == joueur) ||
                        (m[3].proprietaire == joueur && c[3].proprietaire == joueur)) { moulin() }

                5 -> if ((b[2].proprietaire == joueur && b[7].proprietaire == joueur) ||
                        (m[4].proprietaire == joueur && c[4].proprietaire == joueur)) { moulin() }

                6 -> if ((b[0].proprietaire == joueur && b[3].proprietaire == joueur) ||
                        (b[6].proprietaire == joueur && b[7].proprietaire == joueur)) { moulin() }

                7 -> if ((b[5].proprietaire == joueur && b[7].proprietaire == joueur) ||
                        (m[6].proprietaire == joueur && c[6].proprietaire == joueur)) { moulin() }

                8 -> if ((b[5].proprietaire == joueur && b[6].proprietaire == joueur) ||
                        (b[2].proprietaire == joueur && b[4].proprietaire == joueur)) { moulin() }
            }
            'm' -> when(position.last().toInt()-49){
                1 -> if ((m[1].proprietaire == joueur && m[2].proprietaire == joueur) ||
                        (m[3].proprietaire == joueur && m[5].proprietaire == joueur)) { moulin() }

                2 -> if ((m[0].proprietaire == joueur && m[2].proprietaire == joueur) ||
                        (b[1].proprietaire == joueur && c[2].proprietaire == joueur)) { moulin() }

                3 -> if ((m[0].proprietaire == joueur && m[1].proprietaire == joueur) ||
                        (m[4].proprietaire == joueur && m[7].proprietaire == joueur)) { moulin() }

                4 -> if ((m[0].proprietaire == joueur && m[5].proprietaire == joueur) ||
                        (b[3].proprietaire == joueur && c[3].proprietaire == joueur)) { moulin() }

                5 -> if ((m[2].proprietaire == joueur && m[7].proprietaire == joueur) ||
                        (b[4].proprietaire == joueur && c[4].proprietaire == joueur)) { moulin() }

                6 -> if ((m[0].proprietaire == joueur && m[3].proprietaire == joueur) ||
                        (m[6].proprietaire == joueur && m[7].proprietaire == joueur)) { moulin() }

                7 -> if ((m[5].proprietaire == joueur && m[7].proprietaire == joueur) ||
                        (b[6].proprietaire == joueur && c[6].proprietaire == joueur)) { moulin() }

                8 -> if ((m[5].proprietaire == joueur && m[6].proprietaire == joueur) ||
                        (m[2].proprietaire == joueur && m[4].proprietaire == joueur)) { moulin() }
            }
            'c' -> when(position.last().toInt()-49){
                1 -> if ((c[1].proprietaire == joueur && c[2].proprietaire == joueur) ||
                        (c[3].proprietaire == joueur && c[5].proprietaire == joueur)) { moulin() }

                2 -> if ((c[0].proprietaire == joueur && c[2].proprietaire == joueur) ||
                        (m[1].proprietaire == joueur && b[2].proprietaire == joueur)) { moulin() }

                3 -> if ((c[0].proprietaire == joueur && c[1].proprietaire == joueur) ||
                        (c[4].proprietaire == joueur && c[7].proprietaire == joueur)) { moulin() }

                4 -> if ((c[0].proprietaire == joueur && c[5].proprietaire == joueur) ||
                        (m[3].proprietaire == joueur && b[3].proprietaire == joueur)) { moulin() }

                5 -> if ((c[2].proprietaire == joueur && c[7].proprietaire == joueur) ||
                        (m[4].proprietaire == joueur && b[4].proprietaire == joueur)) { moulin() }

                6 -> if ((c[0].proprietaire == joueur && c[3].proprietaire == joueur) ||
                        (c[6].proprietaire == joueur && c[7].proprietaire == joueur)) { moulin() }

                7 -> if ((c[5].proprietaire == joueur && c[7].proprietaire == joueur) ||
                        (m[6].proprietaire == joueur && b[6].proprietaire == joueur)) { moulin() }

                8 -> if ((c[5].proprietaire == joueur && c[6].proprietaire == joueur) ||
                        (c[2].proprietaire == joueur && c[4].proprietaire == joueur)) { moulin() }
            }
        }
    }

    private fun moulin(){
        println("Moulin!")
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